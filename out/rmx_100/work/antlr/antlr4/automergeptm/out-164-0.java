package org.antlr.v4.runtime.tree;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

/** An interface to access the tree of RuleContext objects created
 *  during a parse that makes the data structure look like a simple parse tree.
 *  This node represents both internal nodes, rule invocations,
 *  and leaf nodes, token matches.
 *
 *  The payload is either a token or a context object.
 */
public interface ParseTree<Symbol extends java.lang.Object> extends SyntaxTree {
  public interface RuleNode<Symbol extends java.lang.Object> extends ParseTree<Symbol> {
    RuleContext<Symbol> getRuleContext();

    @Override RuleNode<Symbol> getParent();
  }

  public interface TerminalNode<Symbol extends java.lang.Object> extends ParseTree<Symbol> {
    Symbol getSymbol();

    @Override RuleNode<Symbol> getParent();
  }

  public static class TerminalNodeImpl<Symbol extends java.lang.Object> implements TerminalNode<Symbol> {
    public Symbol symbol;

    public RuleNode<Symbol> parent;

    /** Which ATN node matched this token? */
    public int s;

    public TerminalNodeImpl(Symbol symbol) {
      this.symbol = symbol;
    }

    @Override public ParseTree<Symbol> getChild(int i) {
      return null;
    }

    @Override public Symbol getSymbol() {
      return symbol;
    }

    @Override public RuleNode<Symbol> getParent() {
      return parent;
    }

    @Override public Symbol getPayload() {
      return symbol;
    }

    @Override public Interval getSourceInterval() {
      if (!(symbol instanceof Token)) {
        return Interval.INVALID;
      }
      return new Interval(((Token) symbol).getStartIndex(), ((Token) symbol).getStopIndex());
    }

    @Override public int getChildCount() {
      return 0;
    }

    @Override public String toString() {
      if (symbol instanceof Token) {
        if (((Token) symbol).getType() == Token.EOF) {
          return "<EOF>";
        }
        return ((Token) symbol).getText();
      } else {
        return symbol != null ? symbol.toString() : "<null>";
      }
    }

    @Override public String toStringTree() {
      return toString();
    }
  }

  public static class ErrorNodeImpl<Symbol extends java.lang.Object> extends TerminalNodeImpl<Symbol> {
    public ErrorNodeImpl(Symbol token) {
      super(token);
    }
  }

  @Override ParseTree<Symbol> getParent();

  @Override ParseTree<Symbol> getChild(int i);
}


