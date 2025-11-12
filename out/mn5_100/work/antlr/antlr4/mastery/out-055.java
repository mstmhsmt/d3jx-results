package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.antlr.v4.runtime.misc.DoubleKeyMap;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserATNSimulator extends ATNSimulator {

    public static final boolean debug = false;

    public static final boolean debug_list_atn_decisions = false;

    public static final boolean dfa_debug = false;

    public static final boolean retry_debug = false;

    @Nullable
    final protected Parser parser;

    @NotNull
    public final DFA[] decisionToDFA;

    @NotNull
    private PredictionMode mode = PredictionMode.LL;

    protected DoubleKeyMap<PredictionContext, PredictionContext, PredictionContext> mergeCache;

    protected TokenStream _input;

    protected int _startIndex;

    protected ParserRuleContext _outerContext;

    public ParserATNSimulator(@NotNull ATN atn, @NotNull DFA[] decisionToDFA, @NotNull PredictionContextCache sharedContextCache) {
        this(null, atn, decisionToDFA, sharedContextCache);
    }

    public ParserATNSimulator(@Nullable Parser parser, @NotNull ATN atn, @NotNull DFA[] decisionToDFA, @NotNull PredictionContextCache sharedContextCache) {
        super(atn, sharedContextCache);
        this.parser = parser;
        this.decisionToDFA = decisionToDFA;
    }

    @Override
    public void reset() {
    }

    public int adaptivePredict(@NotNull TokenStream input, int decision, @Nullable ParserRuleContext outerContext) {
        if (debug || debug_list_atn_decisions) {
            System.out.println("adaptivePredict decision " + decision + " exec LA(1)==" + getLookaheadName(input) + " line " + input.LT(1).getLine() + ":" + input.LT(1).getCharPositionInLine());
        }
        _input = input;
        _startIndex = input.index();
        _outerContext = outerContext;
        DFA dfa = decisionToDFA[decision];
        int m = input.mark();
        int index = input.index();
        try {
            if (dfa.s0 == null) {
                if (outerContext == null)
                    outerContext = ParserRuleContext.EMPTY;
                if (debug || debug_list_atn_decisions) {
                    System.out.println("predictATN decision " + dfa.decision + " exec LA(1)==" + getLookaheadName(input) + ", outerContext=" + outerContext.toString(parser));
                }
                boolean fullCtx = false;
                ATNConfigSet s0_closure = computeStartState(dfa.atnStartState, ParserRuleContext.EMPTY, fullCtx);
                dfa.s0 = addDFAState(dfa, new DFAState(s0_closure));
            }
            int alt = execATN(dfa, dfa.s0, input, index, outerContext);
            if (debug)
                System.out.println("DFA after predictATN: " + dfa.toString(parser.getTokenNames()));
            return alt;
        } finally {
            mergeCache = null;
            input.seek(index);
            input.release(m);
        }
    }

    protected int execATN(@NotNull DFA dfa, @NotNull DFAState s0, @NotNull TokenStream input, int startIndex, ParserRuleContext outerContext) {
        if (debug || debug_list_atn_decisions) {
            System.out.println("execATN decision " + dfa.decision + " exec LA(1)==" + getLookaheadName(input) + " line " + input.LT(1).getLine() + ":" + input.LT(1).getCharPositionInLine());
        }
        DFAState previousD = s0;
        if (debug)
            System.out.println("s0 = " + s0);
        int t = input.LA(1);
        while (true) {
            DFAState D = getExistingTargetState(previousD, t);
            if (D == null) {
                D = computeTargetState(dfa, previousD, t);
            }
            if (D == ERROR) {
                int alt = getAltThatFinishedDecisionEntryRule(previousD.configs);
                if (alt != ATN.INVALID_ALT_NUMBER) {
                    return alt;
                }
                throw noViableAlt(input, outerContext, previousD.configs, startIndex);
            }
            if (D.requiresFullContext && mode != PredictionMode.SLL) {
                BitSet conflictingAlts = null;
                if (D.predicates != null) {
                    if (debug)
                        System.out.println("DFA state has preds in DFA sim LL failover");
                    int conflictIndex = input.index();
                    if (conflictIndex != startIndex) {
                        input.seek(startIndex);
                    }
                    conflictingAlts = evalSemanticContext(D.predicates, outerContext, true);
                    if (conflictingAlts.cardinality() == 1) {
                        if (debug)
                            System.out.println("Full LL avoided");
                        return conflictingAlts.nextSetBit(0);
                    }
                    if (conflictIndex != startIndex) {
                        input.seek(conflictIndex);
                    }
                }
                if (dfa_debug)
                    System.out.println("ctx sensitive state " + outerContext + " in " + D);
                boolean fullCtx = true;
                ATNConfigSet s0_closure = computeStartState(dfa.atnStartState, outerContext, fullCtx);
                reportAttemptingFullContext(dfa, conflictingAlts, D.configs, startIndex, input.index());
                int alt = execATNWithFullContext(dfa, D, s0_closure, input, startIndex, outerContext);
                return alt;
            }
            if (D.isAcceptState) {
                if (D.predicates == null) {
                    return D.prediction;
                }
                int stopIndex = input.index();
                input.seek(startIndex);
                BitSet alts = evalSemanticContext(D.predicates, outerContext, true);
                switch(alts.cardinality()) {
                    case 0:
                        throw noViableAlt(input, outerContext, D.configs, startIndex);
                    case 1:
                        return alts.nextSetBit(0);
                    default:
                        reportAmbiguity(dfa, D, startIndex, stopIndex, false, alts, D.configs);
                        return alts.nextSetBit(0);
                }
            }
            previousD = D;
            if (t != IntStream.EOF) {
                input.consume();
                t = input.LA(1);
            }
        }
    }

    @Nullable
    protected DFAState getExistingTargetState(@NotNull DFAState previousD, int t) {
        DFAState[] edges = previousD.edges;
        if (edges == null || t + 1 < 0 || t + 1 >= edges.length) {
            return null;
        }
        return edges[t + 1];
    }

    @NotNull
    protected DFAState computeTargetState(@NotNull DFA dfa, @NotNull DFAState previousD, int t) {
        ATNConfigSet reach = computeReachSet(previousD.configs, t, false);
        if (reach == null) {
            addDFAEdge(dfa, previousD, t, ERROR);
            return ERROR;
        }
        DFAState D = new DFAState(reach);
        int predictedAlt = getUniqueAlt(reach);
        if (debug) {
            Collection<BitSet> altSubSets = PredictionMode.getConflictingAltSubsets(reach);
            System.out.println("SLL altSubSets=" + altSubSets + ", configs=" + reach + ", predict=" + predictedAlt + ", allSubsetsConflict=" + PredictionMode.allSubsetsConflict(altSubSets) + ", conflictingAlts=" + getConflictingAlts(reach));
        }
        if (predictedAlt != ATN.INVALID_ALT_NUMBER) {
            D.isAcceptState = true;
            D.configs.uniqueAlt = predictedAlt;
            D.prediction = predictedAlt;
        } else if (PredictionMode.hasSLLConflictTerminatingPrediction(mode, reach)) {
            D.configs.conflictingAlts = getConflictingAlts(reach);
            D.requiresFullContext = true;
            D.isAcceptState = true;
            D.prediction = D.configs.conflictingAlts.nextSetBit(0);
        }
        if (D.isAcceptState && D.configs.hasSemanticContext) {
            predicateDFAState(D, atn.getDecisionState(dfa.decision));
            if (D.predicates != null) {
                D.prediction = ATN.INVALID_ALT_NUMBER;
            }
        }
        D = addDFAEdge(dfa, previousD, t, D);
        return D;
    }

    protected void predicateDFAState(DFAState dfaState, DecisionState decisionState) {
        int nalts = decisionState.getNumberOfTransitions();
        BitSet altsToCollectPredsFrom = getConflictingAltsOrUniqueAlt(dfaState.configs);
        SemanticContext[] altToPred = getPredsForAmbigAlts(altsToCollectPredsFrom, dfaState.configs, nalts);
        if (altToPred != null) {
            dfaState.predicates = getPredicatePredictions(altsToCollectPredsFrom, altToPred);
            dfaState.prediction = ATN.INVALID_ALT_NUMBER;
        } else {
            dfaState.prediction = altsToCollectPredsFrom.nextSetBit(0);
        }
    }

    protected int execATNWithFullContext(DFA dfa, DFAState D, @NotNull ATNConfigSet s0, @NotNull TokenStream input, int startIndex, ParserRuleContext outerContext) {
        if (debug || debug_list_atn_decisions) {
            System.out.println("execATNWithFullContext " + s0);
        }
        boolean fullCtx = true;
        boolean foundExactAmbig = false;
        ATNConfigSet reach = null;
        ATNConfigSet previous = s0;
        input.seek(startIndex);
        int t = input.LA(1);
        int predictedAlt;
        while (true) {
            reach = computeReachSet(previous, t, fullCtx);
            if (reach == null) {
                int alt = getAltThatFinishedDecisionEntryRule(previous);
                if (alt != ATN.INVALID_ALT_NUMBER) {
                    return alt;
                }
                throw noViableAlt(input, outerContext, previous, startIndex);
            }
            Collection<BitSet> altSubSets = PredictionMode.getConflictingAltSubsets(reach);
            if (debug) {
                System.out.println("LL altSubSets=" + altSubSets + ", predict=" + PredictionMode.getUniqueAlt(altSubSets) + ", resolvesToJustOneViableAlt=" + PredictionMode.resolvesToJustOneViableAlt(altSubSets));
            }
            reach.uniqueAlt = getUniqueAlt(reach);
            if (reach.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
                predictedAlt = reach.uniqueAlt;
                break;
            }
            if (mode != PredictionMode.LL_EXACT_AMBIG_DETECTION) {
                predictedAlt = PredictionMode.resolvesToJustOneViableAlt(altSubSets);
                if (predictedAlt != ATN.INVALID_ALT_NUMBER) {
                    break;
                }
            } else {
                if (PredictionMode.allSubsetsConflict(altSubSets) && PredictionMode.allSubsetsEqual(altSubSets)) {
                    foundExactAmbig = true;
                    predictedAlt = PredictionMode.getSingleViableAlt(altSubSets);
                    break;
                }
            }
            previous = reach;
            if (t != IntStream.EOF) {
                input.consume();
                t = input.LA(1);
            }
        }
        if (reach.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
            reportContextSensitivity(dfa, predictedAlt, reach, startIndex, input.index());
            return predictedAlt;
        }
        reportAmbiguity(dfa, D, startIndex, input.index(), foundExactAmbig, null, reach);
        return predictedAlt;
    }

    protected ATNConfigSet computeReachSet(ATNConfigSet closure, int t, boolean fullCtx) {
        if (debug)
            System.out.println("in computeReachSet, starting closure: " + closure);
        if (mergeCache == null) {
            mergeCache = new DoubleKeyMap<PredictionContext, PredictionContext, PredictionContext>();
        }
        ATNConfigSet intermediate = new ATNConfigSet(fullCtx);
        List<ATNConfig> skippedStopStates = null;
        for (ATNConfig c : closure) {
            if (debug)
                System.out.println("testing " + getTokenName(t) + " at " + c.toString());
            if (c.state instanceof RuleStopState) {
                assert c.context.isEmpty();
                if (fullCtx || t == IntStream.EOF) {
                    if (skippedStopStates == null) {
                        skippedStopStates = new ArrayList<ATNConfig>();
                    }
                    skippedStopStates.add(c);
                }
                continue;
            }
            int n = c.state.getNumberOfTransitions();
            for (int ti = 0; ti < n; ti++) {
                Transition trans = c.state.transition(ti);
                ATNState target = getReachableTarget(trans, t);
                if (target != null) {
                    intermediate.add(new ATNConfig(c, target), mergeCache);
                }
            }
        }
        ATNConfigSet reach = null;
        if (skippedStopStates == null) {
            if (intermediate.size() == 1) {
                reach = intermediate;
            } else if (getUniqueAlt(intermediate) != ATN.INVALID_ALT_NUMBER) {
                reach = intermediate;
            }
        }
        if (reach == null) {
            reach = new ATNConfigSet(fullCtx);
            Set<ATNConfig> closureBusy = new HashSet<ATNConfig>();
            for (ATNConfig c : intermediate) {
                closure(c, reach, closureBusy, false, fullCtx);
            }
        }
        if (t == IntStream.EOF) {
            reach = removeAllConfigsNotInRuleStopState(reach, reach == intermediate);
        }
        if (skippedStopStates != null && (!fullCtx || !PredictionMode.hasConfigInRuleStopState(reach))) {
            assert !skippedStopStates.isEmpty();
            for (ATNConfig c : skippedStopStates) {
                reach.add(c, mergeCache);
            }
        }
        if (reach.isEmpty())
            return null;
        return reach;
    }

    @NotNull
    protected ATNConfigSet removeAllConfigsNotInRuleStopState(@NotNull ATNConfigSet configs, boolean lookToEndOfRule) {
        if (PredictionMode.allConfigsInRuleStopStates(configs)) {
            return configs;
        }
        ATNConfigSet result = new ATNConfigSet(configs.fullCtx);
        for (ATNConfig config : configs) {
            if (config.state instanceof RuleStopState) {
                result.add(config, mergeCache);
                continue;
            }
            if (lookToEndOfRule && config.state.onlyHasEpsilonTransitions()) {
                IntervalSet nextTokens = atn.nextTokens(config.state);
                if (nextTokens.contains(Token.EPSILON)) {
                    ATNState endOfRuleState = atn.ruleToStopState[config.state.ruleIndex];
                    result.add(new ATNConfig(config, endOfRuleState), mergeCache);
                }
            }
        }
        return result;
    }

    @NotNull
    protected ATNConfigSet computeStartState(@NotNull ATNState p, @Nullable RuleContext ctx, boolean fullCtx) {
        PredictionContext initialContext = PredictionContext.fromRuleContext(atn, ctx);
        ATNConfigSet configs = new ATNConfigSet(fullCtx);
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            ATNState target = p.transition(i).target;
            ATNConfig c = new ATNConfig(target, i + 1, initialContext);
            Set<ATNConfig> closureBusy = new HashSet<ATNConfig>();
            closure(c, configs, closureBusy, true, fullCtx);
        }
        return configs;
    }

    @Nullable
    protected ATNState getReachableTarget(@NotNull Transition trans, int ttype) {
        if (trans.matches(ttype, 0, atn.maxTokenType)) {
            return trans.target;
        }
        return null;
    }

    protected SemanticContext[] getPredsForAmbigAlts(@NotNull BitSet ambigAlts, @NotNull ATNConfigSet configs, int nalts) {
        SemanticContext[] altToPred = new SemanticContext[nalts + 1];
        for (ATNConfig c : configs) {
            if (ambigAlts.get(c.alt)) {
                altToPred[c.alt] = SemanticContext.or(altToPred[c.alt], c.semanticContext);
            }
        }
        int nPredAlts = 0;
        for (int i = 1; i <= nalts; i++) {
            if (altToPred[i] == null) {
                altToPred[i] = SemanticContext.NONE;
            } else if (altToPred[i] != SemanticContext.NONE) {
                nPredAlts++;
            }
        }
        if (nPredAlts == 0)
            altToPred = null;
        if (debug)
            System.out.println("getPredsForAmbigAlts result " + Arrays.toString(altToPred));
        return altToPred;
    }

    protected DFAState.PredPrediction[] getPredicatePredictions(BitSet ambigAlts, SemanticContext[] altToPred) {
        List<DFAState.PredPrediction> pairs = new ArrayList<DFAState.PredPrediction>();
        boolean containsPredicate = false;
        for (int i = 1; i < altToPred.length; i++) {
            SemanticContext pred = altToPred[i];
            assert pred != null;
            if (ambigAlts != null && ambigAlts.get(i)) {
                pairs.add(new DFAState.PredPrediction(pred, i));
            }
            if (pred != SemanticContext.NONE)
                containsPredicate = true;
        }
        if (!containsPredicate) {
            pairs = null;
        }
        return pairs.toArray(new DFAState.PredPrediction[pairs.size()]);
    }

    protected int getAltThatFinishedDecisionEntryRule(ATNConfigSet configs) {
        IntervalSet alts = new IntervalSet();
        for (ATNConfig c : configs) {
            if (c.reachesIntoOuterContext > 0 || (c.state instanceof RuleStopState && c.context.hasEmptyPath())) {
                alts.add(c.alt);
            }
        }
        if (alts.size() == 0)
            return ATN.INVALID_ALT_NUMBER;
        return alts.getMinElement();
    }

    protected BitSet evalSemanticContext(@NotNull DFAState.PredPrediction[] predPredictions, ParserRuleContext outerContext, boolean complete) {
        BitSet predictions = new BitSet();
        for (DFAState.PredPrediction pair : predPredictions) {
            if (pair.pred == SemanticContext.NONE) {
                predictions.set(pair.alt);
                if (!complete) {
                    break;
                }
                continue;
            }
            boolean predicateEvaluationResult = pair.pred.eval(parser, outerContext);
            if (debug || dfa_debug) {
                System.out.println("eval pred " + pair + "=" + predicateEvaluationResult);
            }
            if (predicateEvaluationResult) {
                if (debug || dfa_debug)
                    System.out.println("PREDICT " + pair.alt);
                predictions.set(pair.alt);
                if (!complete) {
                    break;
                }
            }
        }
        return predictions;
    }

    protected void closure(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean fullCtx) {
        final int initialDepth = 0;
        closureCheckingStopState(config, configs, closureBusy, collectPredicates, fullCtx, initialDepth);
        assert !fullCtx || !configs.dipsIntoOuterContext;
    }

    protected void closureCheckingStopState(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean fullCtx, int depth) {
        if (debug)
            System.out.println("closure(" + config.toString(parser, true) + ")");
        if (config.state instanceof RuleStopState) {
            if (!config.context.isEmpty()) {
                for (int i = 0; i < config.context.size(); i++) {
                    if (config.context.getReturnState(i) == PredictionContext.EMPTY_RETURN_STATE) {
                        if (fullCtx) {
                            configs.add(new ATNConfig(config, config.state, PredictionContext.EMPTY), mergeCache);
                            continue;
                        } else {
                            if (debug)
                                System.out.println("FALLING off rule " + getRuleName(config.state.ruleIndex));
                            closure_(config, configs, closureBusy, collectPredicates, fullCtx, depth);
                        }
                        continue;
                    }
                    ATNState returnState = atn.states.get(config.context.getReturnState(i));
                    PredictionContext newContext = config.context.getParent(i);
                    ATNConfig c = new ATNConfig(returnState, config.alt, newContext, config.semanticContext);
                    c.reachesIntoOuterContext = config.reachesIntoOuterContext;
                    assert depth > Integer.MIN_VALUE;
                    closureCheckingStopState(c, configs, closureBusy, collectPredicates, fullCtx, depth - 1);
                }
                return;
            } else if (fullCtx) {
                configs.add(config, mergeCache);
                return;
            } else {
                if (debug)
                    System.out.println("FALLING off rule " + getRuleName(config.state.ruleIndex));
            }
        }
        closure_(config, configs, closureBusy, collectPredicates, fullCtx, depth);
    }

    protected void closure_(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean fullCtx, int depth) {
        ATNState p = config.state;
        if (!p.onlyHasEpsilonTransitions()) {
            configs.add(config, mergeCache);
        }
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            Transition t = p.transition(i);
            boolean continueCollecting = !(t instanceof ActionTransition) && collectPredicates;
            ATNConfig c = getEpsilonTarget(config, t, continueCollecting, depth == 0, fullCtx);
            if (c != null) {
                int newDepth = depth;
                if (config.state instanceof RuleStopState) {
                    assert !fullCtx;
                    if (!closureBusy.add(c)) {
                        continue;
                    }
                    c.reachesIntoOuterContext++;
                    configs.dipsIntoOuterContext = true;
                    assert newDepth > Integer.MIN_VALUE;
                    newDepth--;
                    if (debug)
                        System.out.println("dips into outer ctx: " + c);
                } else if (t instanceof RuleTransition) {
                    if (newDepth >= 0) {
                        newDepth++;
                    }
                }
                closureCheckingStopState(c, configs, closureBusy, continueCollecting, fullCtx, newDepth);
            }
        }
    }

    @NotNull
    public String getRuleName(int index) {
        if (parser != null && index >= 0)
            return parser.getRuleNames()[index];
        return "<rule " + index + ">";
    }

    @Nullable
    protected ATNConfig getEpsilonTarget(@NotNull ATNConfig config, @NotNull Transition t, boolean collectPredicates, boolean inContext, boolean fullCtx) {
        switch(t.getSerializationType()) {
            case Transition.RULE:
                return ruleTransition(config, (RuleTransition) t);
            case Transition.PREDICATE:
                return predTransition(config, (PredicateTransition) t, collectPredicates, inContext, fullCtx);
            case Transition.ACTION:
                return actionTransition(config, (ActionTransition) t);
            case Transition.EPSILON:
                return new ATNConfig(config, t.target);
            default:
                return null;
        }
    }

    @NotNull
    protected ATNConfig actionTransition(@NotNull ATNConfig config, @NotNull ActionTransition t) {
        if (debug)
            System.out.println("ACTION edge " + t.ruleIndex + ":" + t.actionIndex);
        return new ATNConfig(config, t.target);
    }

    @Nullable
    protected ATNConfig predTransition(@NotNull ATNConfig config, @NotNull PredicateTransition pt, boolean collectPredicates, boolean inContext, boolean fullCtx) {
        if (debug) {
            System.out.println("PRED (collectPredicates=" + collectPredicates + ") " + pt.ruleIndex + ":" + pt.predIndex + ", ctx dependent=" + pt.isCtxDependent);
            if (parser != null) {
                System.out.println("context surrounding pred is " + parser.getRuleInvocationStack());
            }
        }
        ATNConfig c = null;
        if (collectPredicates && (!pt.isCtxDependent || (pt.isCtxDependent && inContext))) {
            if (fullCtx) {
                int currentPosition = _input.index();
                _input.seek(_startIndex);
                boolean predSucceeds = pt.getPredicate().eval(parser, _outerContext);
                _input.seek(currentPosition);
                if (predSucceeds) {
                    c = new ATNConfig(config, pt.target);
                }
            } else {
                SemanticContext newSemCtx = SemanticContext.and(config.semanticContext, pt.getPredicate());
                c = new ATNConfig(config, pt.target, newSemCtx);
            }
        } else {
            c = new ATNConfig(config, pt.target);
        }
        if (debug)
            System.out.println("config from pred transition=" + c);
        return c;
    }

    @NotNull
    protected ATNConfig ruleTransition(@NotNull ATNConfig config, @NotNull RuleTransition t) {
        if (debug) {
            System.out.println("CALL rule " + getRuleName(t.target.ruleIndex) + ", ctx=" + config.context);
        }
        ATNState returnState = t.followState;
        PredictionContext newContext = SingletonPredictionContext.create(config.context, returnState.stateNumber);
        return new ATNConfig(config, t.target, newContext);
    }

    protected BitSet getConflictingAlts(ATNConfigSet configs) {
        Collection<BitSet> altsets = PredictionMode.getConflictingAltSubsets(configs);
        return PredictionMode.getAlts(altsets);
    }

    protected BitSet getConflictingAltsOrUniqueAlt(ATNConfigSet configs) {
        BitSet conflictingAlts;
        if (configs.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
            conflictingAlts = new BitSet();
            conflictingAlts.set(configs.uniqueAlt);
        } else {
            conflictingAlts = configs.conflictingAlts;
        }
        return conflictingAlts;
    }

    @NotNull
    public String getTokenName(int t) {
        if (t == Token.EOF)
            return "EOF";
        if (parser != null && parser.getTokenNames() != null) {
            String[] tokensNames = parser.getTokenNames();
            if (t >= tokensNames.length) {
                System.err.println(t + " ttype out of range: " + Arrays.toString(tokensNames));
                System.err.println(((CommonTokenStream) parser.getInputStream()).getTokens());
            } else {
                return tokensNames[t] + "<" + t + ">";
            }
        }
        return String.valueOf(t);
    }

    public String getLookaheadName(TokenStream input) {
        return getTokenName(input.LA(1));
    }

    public void dumpDeadEndConfigs(@NotNull NoViableAltException nvae) {
        System.err.println("dead end configs: ");
        for (ATNConfig c : nvae.getDeadEndConfigs()) {
            String trans = "no edges";
            if (c.state.getNumberOfTransitions() > 0) {
                Transition t = c.state.transition(0);
                if (t instanceof AtomTransition) {
                    AtomTransition at = (AtomTransition) t;
                    trans = "Atom " + getTokenName(at.label);
                } else if (t instanceof SetTransition) {
                    SetTransition st = (SetTransition) t;
                    boolean not = st instanceof NotSetTransition;
                    trans = (not ? "~" : "") + "Set " + st.set.toString();
                }
            }
            System.err.println(c.toString(parser, true) + ":" + trans);
        }
    }

    @NotNull
    protected NoViableAltException noViableAlt(@NotNull TokenStream input, @NotNull ParserRuleContext outerContext, @NotNull ATNConfigSet configs, int startIndex) {
        return new NoViableAltException(parser, input, input.get(startIndex), input.LT(1), configs, outerContext);
    }

    static protected int getUniqueAlt(@NotNull ATNConfigSet configs) {
        int alt = ATN.INVALID_ALT_NUMBER;
        for (ATNConfig c : configs) {
            if (alt == ATN.INVALID_ALT_NUMBER) {
                alt = c.alt;
            } else if (c.alt != alt) {
                return ATN.INVALID_ALT_NUMBER;
            }
        }
        return alt;
    }

    protected DFAState addDFAEdge(@NotNull DFA dfa, @Nullable DFAState from, int t, @Nullable DFAState to) {
        if (debug) {
            System.out.println("EDGE " + from + " -> " + to + " upon " + getTokenName(t));
        }
        if (to == null) {
            return null;
        }
        to = addDFAState(dfa, to);
        if (from == null || t < -1 || t > atn.maxTokenType) {
            return to;
        }
        synchronized (from) {
            if (from.edges == null) {
                from.edges = new DFAState[atn.maxTokenType + 1 + 1];
            }
            from.edges[t + 1] = to;
        }
        if (debug) {
            System.out.println("DFA=\n" + dfa.toString(parser != null ? parser.getTokenNames() : null));
        }
        return to;
    }

    @NotNull
    protected DFAState addDFAState(@NotNull DFA dfa, @NotNull DFAState D) {
        if (D == ERROR) {
            return D;
        }
        synchronized (dfa.states) {
            DFAState existing = dfa.states.get(D);
            if (existing != null)
                return existing;
            D.stateNumber = dfa.states.size();
            if (!D.configs.isReadonly()) {
                D.configs.optimizeConfigs(this);
                D.configs.setReadonly(true);
            }
            dfa.states.put(D, D);
            if (debug)
                System.out.println("adding new DFA state: " + D);
            return D;
        }
    }

    protected void reportAttemptingFullContext(DFA dfa, @Nullable BitSet conflictingAlts, @NotNull ATNConfigSet configs, int startIndex, int stopIndex) {
        if (debug || retry_debug) {
            Interval interval = Interval.of(startIndex, stopIndex);
            System.out.println("reportAttemptingFullContext decision=" + dfa.decision + ":" + configs + ", input=" + parser.getTokenStream().getText(interval));
        }
        if (parser != null)
            parser.getErrorListenerDispatch().reportAttemptingFullContext(parser, dfa, startIndex, stopIndex, conflictingAlts, configs);
    }

    protected void reportContextSensitivity(DFA dfa, int prediction, @NotNull ATNConfigSet configs, int startIndex, int stopIndex) {
        if (debug || retry_debug) {
            Interval interval = Interval.of(startIndex, stopIndex);
            System.out.println("reportContextSensitivity decision=" + dfa.decision + ":" + configs + ", input=" + parser.getTokenStream().getText(interval));
        }
        if (parser != null)
            parser.getErrorListenerDispatch().reportContextSensitivity(parser, dfa, startIndex, stopIndex, prediction, configs);
    }

    protected void reportAmbiguity(@NotNull DFA dfa, DFAState D, int startIndex, int stopIndex, boolean exact, @Nullable BitSet ambigAlts, @NotNull ATNConfigSet configs) {
        if (debug || retry_debug) {
            Interval interval = Interval.of(startIndex, stopIndex);
            System.out.println("reportAmbiguity " + ambigAlts + ":" + configs + ", input=" + parser.getTokenStream().getText(interval));
        }
        if (parser != null)
            parser.getErrorListenerDispatch().reportAmbiguity(parser, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
    }

    public final void setPredictionMode(@NotNull PredictionMode mode) {
        this.mode = mode;
    }

    @NotNull
    public final PredictionMode getPredictionMode() {
        return mode;
    }
}
