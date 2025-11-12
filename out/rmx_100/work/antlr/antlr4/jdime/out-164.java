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


<<<<<<< Unknown file: This is a bug in JDime.
=======
/** An interface to access the tree of RuleContext objects created
 *  during a parse that makes the data structure look like a simple parse tree.
 *  This node represents both internal nodes, rule invocations,
 *  and leaf nodes, token matches.
 *
 *  The payload is either a token or a context object.
 */
public interface ParseTree extends SyntaxTree {
  public interface RuleNode extends ParseTree {
    RuleContext getRuleContext();
  }

  public interface TerminalNode<Symbol extends Token> extends ParseTree {
    Symbol getSymbol();
  }

  public static class TerminalNodeImpl<Symbol extends Token> implements TerminalNode<Symbol> {
    public Symbol symbol;

    public ParseTree parent;

    /** Which ATN node matched this token? */
    public int s;

    public TerminalNodeImpl(Symbol symbol) {
      this.symbol = symbol;
    }

    @Override public ParseTree getChild(int i) {
      return null;
    }

    @Override public Symbol getSymbol() {
      return symbol;
    }

    @Override public ParseTree getParent() {
      return parent;
    }

    @Override public Symbol getPayload() {
      return symbol;
    }

    @Override public Interval getSourceInterval() {
      if (symbol == null) {
        return Interval.INVALID;
      }
      return new Interval(symbol.getStartIndex(), symbol.getStopIndex());
    }

    @Override public int getChildCount() {
      return 0;
    }

    public boolean isErrorNode() {
      return this instanceof ErrorNodeImpl;
    }

    @Override public String toString() {
      if (symbol.getType() == Token.EOF) {
        return "<EOF>";
      }
      return symbol.getText();
    }

    @Override public String toStringTree() {
      return toString();
    }
  }

  public interface ErrorNode<Symbol extends Token> extends TerminalNode<Symbol> {
  }

  public static class ErrorNodeImpl<Symbol extends Token> extends TerminalNodeImpl<Symbol> implements ErrorNode<Symbol> {
    public ErrorNodeImpl(Symbol token) {
      super(token);
    }
  }

  @Override ParseTree getParent();

  @Override ParseTree getChild(int i);
}
>>>>>>> commits-rmx_100/antlr/antlr4/dbc5eae48fff47edceb9ad4dd5c739da439076fd/ParseTree-0fd1f5e.java
