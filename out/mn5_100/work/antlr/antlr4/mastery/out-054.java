package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class LL1Analyzer {

    public static final int HIT_PRED = Token.INVALID_TYPE;

    @NotNull
    public final ATN atn;

    public LL1Analyzer(@NotNull ATN atn) {
        this.atn = atn;
    }

    @Nullable
    public IntervalSet[] getDecisionLookahead(@Nullable ATNState s) {
        if (s == null) {
            return null;
        }
        IntervalSet[] look = new IntervalSet[s.getNumberOfTransitions()];
        for (int alt = 0; alt < s.getNumberOfTransitions(); alt++) {
            look[alt] = new IntervalSet();
            Set<ATNConfig> lookBusy = new HashSet<ATNConfig>();
            boolean seeThruPreds = false;
            _LOOK(s.transition(alt).target, null, PredictionContext.EMPTY, look[alt], lookBusy, new BitSet(), seeThruPreds, false);
            if (look[alt].size() == 0 || look[alt].contains(HIT_PRED)) {
                look[alt] = null;
            }
        }
        return look;
    }

    protected void _LOOK(@NotNull ATNState s, @Nullable ATNState stopState, @Nullable PredictionContext ctx, @NotNull IntervalSet look, @NotNull Set<ATNConfig> lookBusy, @NotNull BitSet calledRuleStack, boolean seeThruPreds, boolean addEOF) {
        ATNConfig c = new ATNConfig(s, 0, ctx);
        if (!lookBusy.add(c))
            return;
        if (s == stopState) {
            if (ctx == null) {
                look.add(Token.EPSILON);
                return;
            } else if (ctx.isEmpty() && addEOF) {
                look.add(Token.EOF);
                return;
            }
        }
        if (s instanceof RuleStopState) {
            if (ctx == null) {
                look.add(Token.EPSILON);
                return;
            } else if (ctx.isEmpty() && addEOF) {
                look.add(Token.EOF);
                return;
            }
            if (ctx != PredictionContext.EMPTY) {
                
<<<<<<< commits-mn5_100/antlr/antlr4/ca213689619bd108f2fd3863676ea4400f2c220e/LL1Analyzer-6d70c3e.java
for (SingletonPredictionContext p : ctx) {
                    ATNState returnState = atn.states.get(p.returnState);
                    boolean removed = calledRuleStack.get(returnState.ruleIndex);
                    try {
                        calledRuleStack.clear(returnState.ruleIndex);
                        _LOOK(returnState, stopState, p.parent, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                    } finally {
                        if (removed) {
                            calledRuleStack.set(returnState.ruleIndex);
                        }
                    }
                }return;
=======
for (int i = 0; i < ctx.size(); i++) {
                    ATNState returnState = atn.states.get(ctx.getReturnState(i));
                    boolean removed = calledRuleStack.get(returnState.ruleIndex);
                    try {
                        calledRuleStack.clear(returnState.ruleIndex);
                        _LOOK(returnState, ctx.getParent(i), look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                    } finally {
                        if (removed) {
                            calledRuleStack.set(returnState.ruleIndex);
                        }
                    }
                }return;
>>>>>>> commits-mn5_100/antlr/antlr4/84324f1dad2594eeb658c07307dd2b1c8231e97c/LL1Analyzer-1e6a858.java

            }
        }
        int n = s.getNumberOfTransitions();
        for (int i = 0; i < n; i++) {
            Transition t = s.transition(i);
            if (t.getClass() == RuleTransition.class) {
                if (calledRuleStack.get(((RuleTransition) t).target.ruleIndex)) {
                    continue;
                }
                PredictionContext newContext = SingletonPredictionContext.create(ctx, ((RuleTransition) t).followState.stateNumber);
                try {
                    calledRuleStack.set(((RuleTransition) t).target.ruleIndex);
                    _LOOK(t.target, stopState, newContext, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                } finally {
                    calledRuleStack.clear(((RuleTransition) t).target.ruleIndex);
                }
            } else if (t instanceof PredicateTransition) {
                if (seeThruPreds) {
                    _LOOK(t.target, stopState, ctx, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
                } else {
                    look.add(HIT_PRED);
                }
            } else if (t.isEpsilon()) {
                _LOOK(t.target, stopState, ctx, look, lookBusy, calledRuleStack, seeThruPreds, addEOF);
            } else if (t.getClass() == WildcardTransition.class) {
                look.addAll(IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, atn.maxTokenType));
            } else {
                IntervalSet set = t.label();
                if (set != null) {
                    if (t instanceof NotSetTransition) {
                        set = set.complement(IntervalSet.of(Token.MIN_USER_TOKEN_TYPE, atn.maxTokenType));
                    }
                    look.addAll(set);
                }
            }
        }
    }

    @NotNull
    public IntervalSet LOOK(@NotNull ATNState s, @Nullable RuleContext ctx) {
        return LOOK(s, null, ctx);
    }

    @NotNull
    public IntervalSet LOOK(@NotNull ATNState s, @Nullable ATNState stopState, @Nullable RuleContext ctx) {
        IntervalSet r = new IntervalSet();
        boolean seeThruPreds = true;
        PredictionContext lookContext = ctx != null ? PredictionContext.fromRuleContext(s.atn, ctx) : null;
        _LOOK(s, stopState, lookContext, r, new HashSet<ATNConfig>(), new BitSet(), seeThruPreds, true);
        return r;
    }
}
