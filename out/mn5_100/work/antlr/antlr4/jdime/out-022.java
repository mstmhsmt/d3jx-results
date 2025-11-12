package org.antlr.v4.runtime;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;

/** This is the default error handling mechanism for ANTLR parsers
 *  and tree parsers.
 */
public class DefaultErrorStrategy implements ANTLRErrorStrategy {
  /** How to create token objects */
  protected TokenFactory<?> _factory = CommonTokenFactory.DEFAULT;

  /** This is true after we see an error and before having successfully
	 *  matched a token. Prevents generation of more than one error message
	 *  per error.
	 */
  protected boolean errorRecoveryMode = false;

  /** The index into the input stream where the last error occurred.
	 * 	This is used to prevent infinite loops where an error is found
	 *  but no token is consumed during recovery...another error is found,
	 *  ad naseum.  This is a failsafe mechanism to guarantee that at least
	 *  one token/tree node is consumed for two errors.
	 */
  protected int lastErrorIndex = -1;

  protected IntervalSet lastErrorStates;

  @Override public void setTokenFactory(TokenFactory<?> factory) {
    this._factory = factory;
  }

  @Override public void beginErrorCondition(Parser recognizer) {
    errorRecoveryMode = true;
  }

  @Override public boolean inErrorRecoveryMode(Parser recognizer) {
    return errorRecoveryMode;
  }

  @Override public void endErrorCondition(Parser recognizer) {
    errorRecoveryMode = false;
    lastErrorStates = null;
    lastErrorIndex = -1;
  }

  @Override public void reportError(Parser recognizer, RecognitionException e) throws RecognitionException {
    if (errorRecoveryMode) {
      return;
    }
    recognizer._syntaxErrors++;
    beginErrorCondition(recognizer);
    if (e instanceof NoViableAltException) {
      reportNoViableAlternative(recognizer, (NoViableAltException) e);
    } else {
      if (e instanceof InputMismatchException) {
        reportInputMismatch(recognizer, (InputMismatchException) e);
      } else {
        if (e instanceof FailedPredicateException) {
          reportFailedPredicate(recognizer, (FailedPredicateException) e);
        } else {
          System.err.println("unknown recognition error type: " + e.getClass().getName());
          if (recognizer != null) {
            recognizer.notifyErrorListeners((Token) e.offendingToken, e.getMessage(), e);
          }
        }
      }
    }
  }

  /** Recover from NoViableAlt errors. Also there could be a mismatched
	 *  token that the match() routine could not recover from.
	 */
  @Override public void recover(Parser recognizer, RecognitionException e) {
    if (lastErrorIndex == recognizer.getInputStream().index() && lastErrorStates.contains(recognizer._ctx.s)) {
      recognizer.consume();
    }
    lastErrorIndex = recognizer.getInputStream().index();
    if (lastErrorStates == null) {
      lastErrorStates = new IntervalSet();
    }
    lastErrorStates.add(recognizer._ctx.s);
    IntervalSet followSet = getErrorRecoverySet(recognizer);
    consumeUntil(recognizer, followSet);
  }

  /** Make sure that the current lookahead symbol is consistent with
	 *  what were expecting at this point in the ATN.
	 *
	 *  At the start of a sub rule upon error, sync() performs single
	 *  token deletion, if possible. If it can't do that, it bails
	 *  on the current rule and uses the default error recovery,
	 *  which consumes until the resynchronization set of the current rule.
	 *
	 *  If the sub rule is optional, ()? or ()* or optional alternative,
	 *  then the expected set includes what follows the subrule.
	 *
	 *  During loop iteration, it consumes until it sees a token that can
	 *  start a sub rule or what follows loop. Yes, that is pretty aggressive.
	 *  We opt to stay in the loop as long as possible.
 	 */
  @Override public void sync(Parser recognizer) {
    ATNState s = recognizer.getInterpreter().atn.states.get(recognizer._ctx.s);
    if (errorRecoveryMode) {
      return;
    }
    SymbolStream<Token> tokens = recognizer.getInputStream();
    int la = tokens.LA(1);
    if (recognizer.getATN().nextTokens(s).contains(la) || la == Token.EOF) {
      return;
    }
    if (recognizer.isExpectedToken(la)) {
      return;
    }
    if (s instanceof PlusBlockStartState || s instanceof StarLoopEntryState || s instanceof BlockStartState) {
      if (singleTokenDeletion(recognizer) != null) {
        return;
      }
      throw new InputMismatchException(recognizer);
    }
    if (s instanceof PlusLoopbackState || s instanceof StarLoopbackState) {
      reportUnwantedToken(recognizer);
      IntervalSet expecting = recognizer.getExpectedTokens();
      IntervalSet whatFollowsLoopIterationOrRule = expecting.or(getErrorRecoverySet(recognizer));
      consumeUntil(recognizer, whatFollowsLoopIterationOrRule);
    }
  }

  public void reportNoViableAlternative(Parser recognizer, NoViableAltException e) throws RecognitionException {
    SymbolStream<Token> tokens = recognizer.getInputStream();
    String input;
    if (tokens instanceof TokenStream) {
      if (e.startToken.getType() == Token.EOF) {
        input = "<EOF>";
      } else {
        input = ((TokenStream) tokens).toString(e.startToken, e.offendingToken);
      }
    } else {
      input = "<unknown input>";
    }
    String msg = "no viable alternative at input " + escapeWSAndQuote(input);
    recognizer.notifyErrorListeners((Token) e.offendingToken, msg, e);
  }

  public void reportInputMismatch(Parser recognizer, InputMismatchException e) throws RecognitionException {
    String msg = "mismatched input " + getTokenErrorDisplay((Token) e.offendingToken) + " expecting " + e.getExpectedTokens().toString(recognizer.getTokenNames());
    recognizer.notifyErrorListeners((Token) e.offendingToken, msg, e);
  }

  public void reportFailedPredicate(Parser recognizer, FailedPredicateException e) throws RecognitionException {
    String ruleName = recognizer.getRuleNames()[recognizer._ctx.getRuleIndex()];
    String msg = "rule " + ruleName + " " + e.msg;
    recognizer.notifyErrorListeners((Token) e.offendingToken, msg, e);
  }

  public void reportUnwantedToken(Parser recognizer) {
    if (errorRecoveryMode) {
      return;
    }
    recognizer._syntaxErrors++;
    beginErrorCondition(recognizer);
    Token t = recognizer.getCurrentToken();
    String tokenName = getTokenErrorDisplay(t);
    IntervalSet expecting = getExpectedTokens(recognizer);
    String msg = "extraneous input " + tokenName + " expecting " + expecting.toString(recognizer.getTokenNames());
    recognizer.notifyErrorListeners(t, msg, null);
  }

  public void reportMissingToken(Parser recognizer) {
    if (errorRecoveryMode) {
      return;
    }
    recognizer._syntaxErrors++;
    beginErrorCondition(recognizer);
    Token t = recognizer.getCurrentToken();
    IntervalSet expecting = getExpectedTokens(recognizer);
    String msg = "missing " + expecting.toString(recognizer.getTokenNames()) + " at " + getTokenErrorDisplay(t);
    recognizer.notifyErrorListeners(t, msg, null);
  }

  /** Attempt to recover from a single missing or extra token.
	 *
	 *  EXTRA TOKEN
	 *
	 *  LA(1) is not what we are looking for.  If LA(2) has the right token,
	 *  however, then assume LA(1) is some extra spurious token.  Delete it
	 *  and LA(2) as if we were doing a normal match(), which advances the
	 *  input.
	 *
	 *  MISSING TOKEN
	 *
	 *  If current token is consistent with what could come after
	 *  ttype then it is ok to "insert" the missing token, else throw
	 *  exception For example, Input "i=(3;" is clearly missing the
	 *  ')'.  When the parser returns from the nested call to expr, it
	 *  will have call chain:
	 *
	 *    stat -> expr -> atom
	 *
	 *  and it will be trying to match the ')' at this point in the
	 *  derivation:
	 *
	 *       => ID '=' '(' INT ')' ('+' atom)* ';'
	 *                          ^
	 *  match() will see that ';' doesn't match ')' and report a
	 *  mismatched token error.  To recover, it sees that LA(1)==';'
	 *  is in the set of tokens that can follow the ')' token
	 *  reference in rule atom.  It can assume that you forgot the ')'.
	 */
  @Override public Token recoverInline(Parser recognizer) throws RecognitionException {
    Token matchedSymbol = singleTokenDeletion(recognizer);
    if (matchedSymbol != null) {
      recognizer.consume();
      return matchedSymbol;
    }
    if (singleTokenInsertion(recognizer)) {
      return getMissingSymbol(recognizer);
    }
    throw new InputMismatchException(recognizer);
  }

  public boolean singleTokenInsertion(Parser recognizer) {
    int currentSymbolType = recognizer.getInputStream().LA(1);
    ATNState currentState = recognizer.getInterpreter().atn.states.get(recognizer._ctx.s);
    ATNState next = currentState.transition(0).target;
    IntervalSet expectingAtLL2 = recognizer.getInterpreter().atn.nextTokens(next, recognizer._ctx);
    if (expectingAtLL2.contains(currentSymbolType)) {
      reportMissingToken(recognizer);
      return true;
    }
    return false;
  }

  public Token singleTokenDeletion(Parser recognizer) {
    int nextTokenType = recognizer.getInputStream().LA(2);
    IntervalSet expecting = getExpectedTokens(recognizer);
    if (expecting.contains(nextTokenType)) {
      reportUnwantedToken(recognizer);
      recognizer.consume();
      Token matchedSymbol = recognizer.getCurrentToken();
      endErrorCondition(recognizer);
      return matchedSymbol;
    }
    return null;
  }

  /** Conjure up a missing token during error recovery.
	 *
	 *  The recognizer attempts to recover from single missing
	 *  symbols. But, actions might refer to that missing symbol.
	 *  For example, x=ID {f($x);}. The action clearly assumes
	 *  that there has been an identifier matched previously and that
	 *  $x points at that token. If that token is missing, but
	 *  the next token in the stream is what we want we assume that
	 *  this token is missing and we keep going. Because we
	 *  have to return some token to replace the missing token,
	 *  we have to conjure one up. This method gives the user control
	 *  over the tokens returned for missing tokens. Mostly,
	 *  you will want to create something special for identifier
	 *  tokens. For literals such as '{' and ',', the default
	 *  action in the parser or tree parser works. It simply creates
	 *  a CommonToken of the appropriate type. The text will be the token.
	 *  If you change what tokens must be created by the lexer,
	 *  override this method to create the appropriate tokens.
	 */
  protected Token getMissingSymbol(Parser recognizer) {
    Token currentSymbol = recognizer.getCurrentToken();
    IntervalSet expecting = getExpectedTokens(recognizer);
    int expectedTokenType = expecting.getMinElement();
    String tokenText;
    if (expectedTokenType == Token.EOF) {
      tokenText = "<missing EOF>";
    } else {
      tokenText = "<missing " + recognizer.getTokenNames()[expectedTokenType] + ">";
    }
    Token current = currentSymbol;
    if (current.getType() == Token.EOF) {
      current = recognizer.getInputStream().LT(-1);
    }
    return _factory.create(current.getTokenSource(), expectedTokenType, tokenText, Token.DEFAULT_CHANNEL, -1, -1, current.getLine(), current.getCharPositionInLine());
  }

  public IntervalSet getExpectedTokens(Parser recognizer) {
    return recognizer.getExpectedTokens();
  }

  /** How should a token be displayed in an error message? The default
	 *  is to display just the text, but during development you might
	 *  want to have a lot of information spit out.  Override in that case
	 *  to use t.toString() (which, for CommonToken, dumps everything about
	 *  the token). This is better than forcing you to override a method in
	 *  your token objects because you don't have to go modify your lexer
	 *  so that it creates a new Java type.
	 */
  public String getTokenErrorDisplay(Token t) {
    if (t == null) {
      return "<no token>";
    }
    String s = getSymbolText(t);
    if (s == null) {
      if (getSymbolType(t) == Token.EOF) {
        s = "<EOF>";
      } else {
        s = "<" + getSymbolType(t) + ">";
      }
    }
    return escapeWSAndQuote(s);
  }

  protected String getSymbolText(@NotNull Token symbol) {
    if (symbol instanceof Token) {
      return ((Token) symbol).getText();
    } else {
      return symbol.toString();
    }
  }

  protected int getSymbolType(@NotNull Token symbol) {
    if (symbol instanceof Token) {
      return ((Token) symbol).getType();
    } else {
      return Token.INVALID_TYPE;
    }
  }

  protected String escapeWSAndQuote(String s) {
    s = s.replaceAll("\n", "\\\\n");
    s = s.replaceAll("\r", "\\\\r");
    s = s.replaceAll("\t", "\\\\t");
    return "\'" + s + "\'";
  }

  protected IntervalSet getErrorRecoverySet(Parser recognizer) {
    ATN atn = recognizer.getInterpreter().atn;
    RuleContext ctx = recognizer._ctx;
    IntervalSet recoverSet = new IntervalSet();
    while (ctx != null && ctx.invokingState >= 0) {
      ATNState invokingState = atn.states.get(ctx.invokingState);
      RuleTransition rt = (RuleTransition) invokingState.transition(0);
      IntervalSet follow = atn.nextTokens(rt.followState);
      recoverSet.addAll(follow);
      ctx = ctx.parent;
    }
    recoverSet.remove(Token.EPSILON);
    return recoverSet;
  }

  /** Consume tokens until one matches the given token set */
  public void consumeUntil(Parser recognizer, IntervalSet set) {
    int ttype = recognizer.getInputStream().LA(1);
    while (ttype != Token.EOF && !set.contains(ttype)) {
      recognizer.consume();
      ttype = recognizer.getInputStream().LA(1);
    }
  }
}