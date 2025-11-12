package org.antlr.v4.runtime;

/** We must distinguish between listeners triggered during the parse
 *  from listeners triggered during a subsequent tree walk.  During
 *  the parse, the ctx object arg for enter methods don't have any labels set.
 *  We can only access the general ParserRuleContext<Symbol> ctx.
 *  Also, we can only call exit methods for left-recursive rules. Let's
 *  make the interface clear these semantics up. If you need the ctx,
 *  use Parser.getRuleContext().
 */
public interface ParseListener<Symbol extends Token> {
<<<<<<< commits-rmx_100/antlr/antlr4/fce18b2f8f10209ff47af6f7d4d8616943cfea64/ParseListener-3b48355.java
	<T extends Symbol> void visitTerminal(ParserRuleContext<T> parent, T symbol);
||||||| commits-rmx_100/antlr/antlr4/019ea15e3f1f87b402fbcb23d17299a419e87e14/ParseListener-73c1168.java
	<T extends Symbol> void visitTerminal(ParserRuleContext<T> parent, Symbol symbol);
=======
	<T extends Symbol> void visitTerminal(ParserRuleContext<T> parent, Symbol token);
>>>>>>> commits-rmx_100/antlr/antlr4/dbc5eae48fff47edceb9ad4dd5c739da439076fd/ParseListener-d03ca0c.java

	/** Enter all but left-recursive rules */
	void enterNonLRRule(ParserRuleContext<? extends Symbol> ctx);

	void exitEveryRule(ParserRuleContext<? extends Symbol> ctx);
}
