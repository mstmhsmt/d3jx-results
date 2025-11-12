package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.stringtemplate.v4.misc.MultiMap;
import java.util.*;

public class ParserATNSimulator<Symbol extends Token> extends ATNSimulator {

    public static boolean debug = false;

    public static boolean dfa_debug = false;

    public static boolean retry_debug = false;

    public static int ATN_failover = 0;

    public static int predict_calls = 0;

    public static int retry_with_context = 0;

    public static int retry_with_context_indicates_no_conflict = 0;

    @Nullable
    protected final Parser parser;

    @NotNull
    public final DFA[] decisionToDFA;

    public ParserATNSimulator(@NotNull ATN atn) {
        this(null, atn);
    }

    public ParserATNSimulator(@Nullable Parser parser, @NotNull ATN atn) {
        super(atn);
        this.parser = parser;
        decisionToDFA = new DFA[atn.getNumberOfDecisions() + (parser != null ? 1 : 0)];
    }

    @Override
    public void reset() {
    }

    public int adaptivePredict(@NotNull SymbolStream<? extends Symbol> input, int decision, @Nullable ParserRuleContext<?> outerContext) {
        predict_calls++;
        DFA dfa = decisionToDFA[decision];
        if (dfa == null || dfa.s0 == null) {
            DecisionState startState = atn.decisionToState.get(decision);
            decisionToDFA[decision] = dfa = new DFA(startState, decision);
            return predictATN(dfa, input, outerContext);
        } else {
            int m = input.mark();
            int index = input.index();
            try {
                int alt = execDFA(dfa, dfa.s0, input, index, outerContext);
                return alt;
            } finally {
                input.seek(index);
                input.release(m);
            }
        }
    }

    public int predictATN(@NotNull DFA dfa, @NotNull SymbolStream<? extends Symbol> input, @Nullable ParserRuleContext<?> outerContext) {
        if (outerContext == null)
            outerContext = ParserRuleContext.EMPTY;
        if (debug)
            System.out.println("ATN decision " + dfa.decision + " exec LA(1)==" + getLookaheadName(input) + ", outerContext=" + outerContext.toString(parser));
        DecisionState decState = atn.getDecisionState(dfa.decision);
        boolean greedy = decState.isGreedy;
        boolean loopsSimulateTailRecursion = false;
        ATNConfigSet s0_closure = computeStartState(dfa.atnStartState, ParserRuleContext.EMPTY, greedy, loopsSimulateTailRecursion);
        dfa.s0 = addDFAState(dfa, s0_closure);
        int alt = 0;
        int m = input.mark();
        int index = input.index();
        try {
            alt = execATN(dfa, dfa.s0, input, index, outerContext);
        } catch (NoViableAltException nvae) {
            if (debug)
                dumpDeadEndConfigs(nvae);
            throw nvae;
        } finally {
            input.seek(index);
            input.release(m);
        }
        if (debug)
            System.out.println("DFA after predictATN: " + dfa.toString(parser.getTokenNames()));
        return alt;
    }

    public int execDFA(@NotNull DFA dfa, @NotNull DFAState s0, @NotNull SymbolStream<? extends Symbol> input, int startIndex, @Nullable ParserRuleContext<?> outerContext) 



    public int execATN(@NotNull DFA dfa, @NotNull DFAState s0, @NotNull SymbolStream<? extends Symbol> input, int startIndex, ParserRuleContext<?> outerContext) {
        if (debug)
            System.out.println("execATN decision " + dfa.decision + " exec LA(1)==" + getLookaheadName(input));
        ATN_failover++;
        ATNConfigSet previous = s0.configset;
        DFAState D;
        ATNConfigSet fullCtxSet;
        if (debug)
            System.out.println("s0 = " + s0);
        int t = input.LA(1);
        DecisionState decState = atn.getDecisionState(dfa.decision);
        boolean greedy = decState.isGreedy;
        while (true) {
            boolean loopsSimulateTailRecursion = false;
            ATNConfigSet reach = computeReachSet(previous, t, greedy, loopsSimulateTailRecursion);
            if (reach == null)
                throw noViableAlt(input, outerContext, previous, startIndex);
            D = addDFAEdge(dfa, previous, t, reach);
            int predictedAlt = getUniqueAlt(reach);
            if (predictedAlt != ATN.INVALID_ALT_NUMBER) {
                D.isAcceptState = true;
                D.configset.uniqueAlt = predictedAlt;
                D.prediction = predictedAlt;
            } else {
                boolean fullCtx = false;
                D.configset.conflictingAlts = getConflictingAlts(reach, fullCtx);
                if (D.configset.conflictingAlts != null) {
                    if (greedy) {
                        int k = input.index() - startIndex + 1;
                        if (outerContext == ParserRuleContext.EMPTY || !D.configset.dipsIntoOuterContext || k == 1) {
                            if (!D.configset.hasSemanticContext) {
                                reportAmbiguity(dfa, D, startIndex, input.index(), D.configset.conflictingAlts, D.configset);
                            }
                            D.isAcceptState = true;
                            predictedAlt = resolveToMinAlt(D, D.configset.conflictingAlts);
                        } else {
                            if (debug)
                                System.out.println("RETRY with outerContext=" + outerContext);
                            loopsSimulateTailRecursion = true;
                            ATNConfigSet s0_closure = computeStartState(dfa.atnStartState, outerContext, greedy, loopsSimulateTailRecursion);
                            fullCtxSet = execATNWithFullContext(dfa, D, s0_closure, input, startIndex, outerContext, decState.getNumberOfTransitions(), greedy);
                            D.isCtxSensitive = true;
                            D.prediction = predictedAlt = fullCtxSet.uniqueAlt;
                            return predictedAlt;
                        }
                    } else {
                        int exitAlt = 2;
                        D.isAcceptState = true;
                        D.prediction = predictedAlt = exitAlt;
                    }
                }
            }
            if (!greedy) {
                int exitAlt = 2;
                if (predictedAlt != ATN.INVALID_ALT_NUMBER && configWithAltAtStopState(reach, 1)) {
                    if (debug)
                        System.out.println("nongreedy loop but unique alt " + D.configset.uniqueAlt + " at " + reach);
                    D.isAcceptState = true;
                    D.prediction = predictedAlt = exitAlt;
                } else {
                    if (configWithAltAtStopState(reach, exitAlt)) {
                        if (debug)
                            System.out.println("nongreedy at stop state for exit branch");
                        D.isAcceptState = true;
                        D.prediction = predictedAlt = exitAlt;
                    }
                }
            }
            if (D.isAcceptState && D.configset.hasSemanticContext) {
                int nalts = decState.getNumberOfTransitions();
                List<DFAState.PredPrediction> predPredictions = predicateDFAState(D, D.configset, outerContext, nalts);
                IntervalSet conflictingAlts = getConflictingAltsFromConfigSet(D.configset);
                if (D.predicates.size() < conflictingAlts.size()) {
                    reportInsufficientPredicates(dfa, startIndex, input.index(), conflictingAlts, decState, getPredsForAmbigAlts(conflictingAlts, D.configset, nalts), D.configset, false);
                }
                input.seek(startIndex);
                predictedAlt = evalSemanticContext(predPredictions, outerContext);
                if (predictedAlt != ATN.INVALID_ALT_NUMBER) {
                    return predictedAlt;
                }
                throw noViableAlt(input, outerContext, D.configset, startIndex);
            }
            if (D.isAcceptState)
                return predictedAlt;
            previous = reach;
            input.consume();
            t = input.LA(1);
        }
    }

    public ATNConfigSet execATNWithFullContext(DFA dfa, DFAState D, @NotNull ATNConfigSet s0, @NotNull SymbolStream<? extends Symbol> input, int startIndex, ParserRuleContext<?> outerContext, int nalts, boolean greedy) {
        retry_with_context++;
        reportAttemptingFullContext(dfa, s0, startIndex, input.index());
        if (debug)
            System.out.println("execATNWithFullContext " + s0 + ", greedy=" + greedy);
        ATNConfigSet reach = null;
        ATNConfigSet previous = s0;
        input.seek(startIndex);
        int t = input.LA(1);
        while (true) {
            reach = computeReachSet(previous, t, greedy, true);
            if (reach == null) {
                throw noViableAlt(input, outerContext, previous, startIndex);
            }
            reach.uniqueAlt = getUniqueAlt(reach);
            if (reach.uniqueAlt != ATN.INVALID_ALT_NUMBER)
                break;
            boolean fullCtx = true;
            reach.conflictingAlts = getConflictingAlts(reach, fullCtx);
            if (reach.conflictingAlts != null)
                break;
            previous = reach;
            input.consume();
            t = input.LA(1);
        }
        if (reach.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
            retry_with_context_indicates_no_conflict++;
            reportContextSensitivity(dfa, reach, startIndex, input.index());
            return reach;
        }
        if (reach.hasSemanticContext) {
            SemanticContext[] altToPred = getPredsForAmbigAlts(reach.conflictingAlts, reach, nalts);
            List<DFAState.PredPrediction> predPredictions;
            if (altToPred != null) {
                predPredictions = getPredicatePredictions(reach.conflictingAlts, altToPred);
                if (predPredictions.size() < nalts) {
                    IntervalSet conflictingAlts = getConflictingAltsFromConfigSet(reach);
                    DecisionState decState = atn.getDecisionState(dfa.decision);
                    reportInsufficientPredicates(dfa, startIndex, input.index(), conflictingAlts, decState, altToPred, reach, true);
                }
                input.seek(startIndex);
                reach.uniqueAlt = evalSemanticContext(predPredictions, outerContext);
                if (reach.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
                    return reach;
                }
                throw noViableAlt(input, outerContext, reach, startIndex);
            }
        }
        reportAmbiguity(dfa, D, startIndex, input.index(), reach.conflictingAlts, reach);
        reach.uniqueAlt = reach.conflictingAlts.getMinElement();
        return reach;
    }

    protected ATNConfigSet computeReachSet(ATNConfigSet closure, int t, boolean greedy, boolean loopsSimulateTailRecursion) {
        if (debug)
            System.out.println("in computeReachSet, starting closure: " + closure);
        ATNConfigSet reach = new ATNConfigSet();
        for (ATNConfig c : closure) {
            if (debug)
                System.out.println("testing " + getTokenName(t) + " at " + c.toString());
            int n = c.state.getNumberOfTransitions();
            for (int ti = 0; ti < n; ti++) {
                Transition trans = c.state.transition(ti);
                ATNState target = getReachableTarget(trans, t);
                if (target != null) {
                    Set<ATNConfig> closureBusy = new HashSet<ATNConfig>();
                    closure(new ATNConfig(c, target), reach, closureBusy, false, greedy, loopsSimulateTailRecursion);
                }
            }
        }
        if (reach.size() == 0)
            return null;
        return reach;
    }

    @NotNull
    public ATNConfigSet computeStartState(@NotNull ATNState p, @Nullable RuleContext ctx, boolean greedy, boolean loopsSimulateTailRecursion) {
        RuleContext initialContext = ctx;
        ATNConfigSet configs = new ATNConfigSet();
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            ATNState target = p.transition(i).target;
            ATNConfig c = new ATNConfig(target, i + 1, initialContext);
            Set<ATNConfig> closureBusy = new HashSet<ATNConfig>();
            closure(c, configs, closureBusy, true, greedy, loopsSimulateTailRecursion);
        }
        return configs;
    }

    @Nullable
    public ATNState getReachableTarget(@NotNull Transition trans, int ttype) {
        if (trans instanceof AtomTransition) {
            AtomTransition at = (AtomTransition) trans;
            if (at.label == ttype) {
                return at.target;
            }
        } else if (trans instanceof SetTransition) {
            SetTransition st = (SetTransition) trans;
            boolean not = trans instanceof NotSetTransition;
            if (!not && st.set.contains(ttype) || not && !st.set.contains(ttype)) {
                return st.target;
            }
        } else if (trans instanceof RangeTransition) {
            RangeTransition rt = (RangeTransition) trans;
            if (ttype >= rt.from && ttype <= rt.to)
                return rt.target;
        } else if (trans instanceof WildcardTransition && ttype != Token.EOF) {
            return trans.target;
        }
        return null;
    }

    public List<DFAState.PredPrediction> predicateDFAState(DFAState D, ATNConfigSet configs, RuleContext outerContext, int nalts) {
        IntervalSet conflictingAlts = getConflictingAltsFromConfigSet(configs);
        if (debug)
            System.out.println("predicateDFAState " + D);
        SemanticContext[] altToPred = getPredsForAmbigAlts(conflictingAlts, configs, nalts);
        List<DFAState.PredPrediction> predPredictions = null;
        if (altToPred != null) {
            predPredictions = getPredicatePredictions(conflictingAlts, altToPred);
            if (D.isCtxSensitive) {
            } else {
                D.predicates = predPredictions;
            }
        }
        D.prediction = ATN.INVALID_ALT_NUMBER;
        return predPredictions;
    }

    public SemanticContext[] getPredsForAmbigAlts(@NotNull IntervalSet ambigAlts, @NotNull ATNConfigSet configs, int nalts) {
        SemanticContext[] altToPred = new SemanticContext[nalts + 1];
        int n = altToPred.length;
        for (int i = 0; i < n; i++) altToPred[i] = SemanticContext.NONE;
        int nPredAlts = 0;
        for (ATNConfig c : configs) {
            if (c.semanticContext != SemanticContext.NONE && ambigAlts.contains(c.alt)) {
                altToPred[c.alt] = SemanticContext.or(altToPred[c.alt], c.semanticContext);
                nPredAlts++;
            }
        }
        for (int i = 0; i < altToPred.length; i++) {
            if (altToPred[i] != null)
                altToPred[i] = altToPred[i].optimize();
        }
        if (nPredAlts == 0)
            altToPred = null;
        if (debug)
            System.out.println("getPredsForAmbigAlts result " + Arrays.toString(altToPred));
        return altToPred;
    }

    public List<DFAState.PredPrediction> getPredicatePredictions(IntervalSet ambigAlts, SemanticContext[] altToPred) {
        List<DFAState.PredPrediction> pairs = new ArrayList<DFAState.PredPrediction>();
        int firstUnpredicated = ATN.INVALID_ALT_NUMBER;
        for (int i = 1; i < altToPred.length; i++) {
            SemanticContext pred = altToPred[i];
            if (ambigAlts != null && ambigAlts.contains(i) && pred == SemanticContext.NONE && firstUnpredicated == ATN.INVALID_ALT_NUMBER) {
                firstUnpredicated = i;
            }
            if (pred != null && pred != SemanticContext.NONE) {
                pairs.add(new DFAState.PredPrediction(pred, i));
            }
        }
        if (pairs.isEmpty())
            pairs = null;
        else if (firstUnpredicated != ATN.INVALID_ALT_NUMBER) {
            pairs.add(new DFAState.PredPrediction(null, firstUnpredicated));
        }
        return pairs;
    }

    public int evalSemanticContext(List<DFAState.PredPrediction> predPredictions, ParserRuleContext<?> outerContext) {
        int predictedAlt = ATN.INVALID_ALT_NUMBER;
        for (DFAState.PredPrediction pair : predPredictions) {
            if (pair.pred == null) {
                predictedAlt = pair.alt;
                break;
            }
            if (debug || dfa_debug) {
                System.out.println("eval pred " + pair + "=" + pair.pred.eval(parser, outerContext));
            }
            if (pair.pred.eval(parser, outerContext)) {
                if (debug || dfa_debug)
                    System.out.println("PREDICT " + pair.alt);
                predictedAlt = pair.alt;
                break;
            }
        }
        return predictedAlt;
    }

    @NotNull
    public String getRuleName(int index) {
        if (parser != null && index >= 0)
            return parser.getRuleNames()[index];
        return "<rule " + index + ">";
    }

    @Nullable
    public ATNConfig getEpsilonTarget(@NotNull ATNConfig config, @NotNull Transition t, boolean collectPredicates, boolean inContext) {
        if (t instanceof RuleTransition) {
            return ruleTransition(config, t);
        } else if (t instanceof PredicateTransition) {
            return predTransition(config, (PredicateTransition) t, collectPredicates, inContext);
        } else if (t instanceof ActionTransition) {
            return actionTransition(config, (ActionTransition) t);
        } else if (t.isEpsilon()) {
            return new ATNConfig(config, t.target);
        }
        return null;
    }

    @NotNull
    public ATNConfig actionTransition(@NotNull ATNConfig config, @NotNull ActionTransition t) {
        if (debug)
            System.out.println("ACTION edge " + t.ruleIndex + ":" + t.actionIndex);
        return new ATNConfig(config, t.target);
    }

    @Nullable
    public ATNConfig predTransition(@NotNull ATNConfig config, @NotNull PredicateTransition pt, boolean collectPredicates, boolean inContext) {
        if (debug) {
            System.out.println("PRED (collectPredicates=" + collectPredicates + ") " + pt.ruleIndex + ":" + pt.predIndex + ", ctx dependent=" + pt.isCtxDependent);
            if (parser != null) {
                System.out.println("context surrounding pred is " + parser.getRuleInvocationStack());
            }
        }
        ATNConfig c;
        if (collectPredicates && (!pt.isCtxDependent || (pt.isCtxDependent && inContext))) {
            SemanticContext newSemCtx = SemanticContext.and(config.semanticContext, pt.getPredicate());
            c = new ATNConfig(config, pt.target, newSemCtx);
        } else {
            c = new ATNConfig(config, pt.target);
        }
        if (debug)
            System.out.println("config from pred transition=" + c);
        return c;
    }

    @NotNull
    public ATNConfig ruleTransition(@NotNull ATNConfig config, @NotNull Transition t) {
        if (debug) {
            System.out.println("CALL rule " + getRuleName(t.target.ruleIndex) + ", ctx=" + config.context);
        }
        ATNState p = config.state;
        RuleContext newContext = new RuleContext(config.context, p.stateNumber);
        return new ATNConfig(config, t.target, newContext);
    }

    @Nullable
    public IntervalSet getConflictingAlts(@NotNull ATNConfigSet configs, boolean fullCtx) {
        if (debug)
            System.out.println("### check ambiguous  " + configs);
        MultiMap<Integer, ATNConfig> stateToConfigListMap = new MultiMap<Integer, ATNConfig>();
        Map<Integer, IntervalSet> stateToAltListMap = new HashMap<Integer, IntervalSet>();
        for (ATNConfig c : configs) {
            stateToConfigListMap.map(c.state.stateNumber, c);
            IntervalSet alts = stateToAltListMap.get(c.state.stateNumber);
            if (alts == null) {
                alts = new IntervalSet();
                stateToAltListMap.put(c.state.stateNumber, alts);
            }
            alts.add(c.alt);
        }
        int numPotentialConflicts = 0;
        IntervalSet altsToIgnore = new IntervalSet();
        for (int state : stateToConfigListMap.keySet()) {
            IntervalSet alts = stateToAltListMap.get(state);
            if (alts.size() == 1) {
                if (!atn.states.get(state).onlyHasEpsilonTransitions()) {
                    List<ATNConfig> configsPerState = stateToConfigListMap.get(state);
                    ATNConfig anyConfig = configsPerState.get(0);
                    altsToIgnore.add(anyConfig.alt);
                    if (debug)
                        System.out.println("### one alt and all non-ep: " + configsPerState);
                }
                stateToConfigListMap.put(state, null);
            } else {
                numPotentialConflicts++;
            }
        }
        if (debug)
            System.out.println("### altsToIgnore: " + altsToIgnore);
        if (debug)
            System.out.println("### stateToConfigListMap=" + stateToConfigListMap);
        if (numPotentialConflicts == 0) {
            return null;
        }
        IntervalSet ambigAlts = new IntervalSet();
        for (int state : stateToConfigListMap.keySet()) {
            List<ATNConfig> configsPerState = stateToConfigListMap.get(state);
            if (configsPerState == null)
                continue;
            IntervalSet alts = stateToAltListMap.get(state);
            if (!altsToIgnore.isNil() && alts.and(altsToIgnore).size() <= 1) {
                continue;
            }
            int size = configsPerState.size();
            for (int i = 0; i < size; i++) {
                ATNConfig c = configsPerState.get(i);
                for (int j = i + 1; j < size; j++) {
                    ATNConfig d = configsPerState.get(j);
                    if (c.alt != d.alt) {
                        boolean conflicting = (fullCtx && c.context.equals(d.context)) || (!fullCtx && c.context.conflictsWith(d.context));
                        if (conflicting) {
                            if (debug) {
                                System.out.println("we reach state " + c.state.stateNumber + " in rule " + (parser != null ? getRuleName(c.state.ruleIndex) : "n/a") + " alts " + c.alt + "," + d.alt + " from ctx " + c.context.toString(parser) + " and " + d.context.toString(parser));
                            }
                            ambigAlts.add(c.alt);
                            ambigAlts.add(d.alt);
                        }
                    }
                }
            }
        }
        if (debug)
            System.out.println("### ambigAlts=" + ambigAlts);
        if (ambigAlts.isNil())
            return null;
        return ambigAlts;
    }

    protected IntervalSet getConflictingAltsFromConfigSet(ATNConfigSet configs) {
        IntervalSet conflictingAlts;
        if (configs.uniqueAlt != ATN.INVALID_ALT_NUMBER) {
            conflictingAlts = IntervalSet.of(configs.uniqueAlt);
        } else {
            conflictingAlts = configs.conflictingAlts;
        }
        return conflictingAlts;
    }

    protected int resolveToMinAlt(@NotNull DFAState D, IntervalSet conflictingAlts) {
        D.prediction = conflictingAlts.getMinElement();
        if (debug)
            System.out.println("RESOLVED TO " + D.prediction + " for " + D);
        return D.prediction;
    }

    protected int resolveNongreedyToExitBranch(@NotNull ATNConfigSet reach, @NotNull IntervalSet conflictingAlts) {
        int exitAlt = 2;
        conflictingAlts.remove(exitAlt);
        if (debug)
            System.out.println("RESOLVED TO " + reach);
        return exitAlt;
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

    public String getLookaheadName(SymbolStream<? extends Symbol> input) {
        return getTokenName(input.LA(1));
    }

    public void dumpDeadEndConfigs(@NotNull NoViableAltException nvae) {
        System.err.println("dead end configs: ");
        for (ATNConfig c : nvae.deadEndConfigs) {
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
    public NoViableAltException noViableAlt(@NotNull SymbolStream<? extends Symbol> input, @NotNull ParserRuleContext<?> outerContext, @NotNull ATNConfigSet configs, int startIndex) {
        return new NoViableAltException(parser, input, input.get(startIndex), input.LT(1), configs, outerContext);
    }

    public int getUniqueAlt(@NotNull Collection<ATNConfig> configs) {
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

    @Nullable
    public boolean configWithAltAtStopState(@NotNull Collection<ATNConfig> configs, int alt) {
        for (ATNConfig c : configs) {
            if (c.alt == alt) {
                if (c.state.getClass() == RuleStopState.class) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    protected DFAState addDFAEdge(@NotNull DFA dfa, @NotNull ATNConfigSet p, int t, @NotNull ATNConfigSet q) {
        DFAState from = addDFAState(dfa, p);
        DFAState to = addDFAState(dfa, q);
        if (debug)
            System.out.println("EDGE " + from + " -> " + to + " upon " + getTokenName(t));
        addDFAEdge(from, t, to);
        if (debug)
            System.out.println("DFA=\n" + dfa.toString(parser != null ? parser.getTokenNames() : null));
        return to;
    }

    protected void addDFAEdge(@Nullable DFAState p, int t, @Nullable DFAState q) {
        if (p == null || t < -1 || q == null)
            return;
        if (p.edges == null) {
            p.edges = new DFAState[atn.maxTokenType + 1 + 1];
        }
        p.edges[t + 1] = q;
    }

    @NotNull
    protected DFAState addDFAState(@NotNull DFA dfa, @NotNull ATNConfigSet configs) {
        DFAState proposed = new DFAState(configs);
        DFAState existing = dfa.states.get(proposed);
        if (existing != null)
            return existing;
        DFAState newState = proposed;
        newState.stateNumber = dfa.states.size();
        newState.configset = new ATNConfigSet(configs);
        dfa.states.put(newState, newState);
        if (debug)
            System.out.println("adding new DFA state: " + newState);
        return newState;
    }

    public void reportAttemptingFullContext(DFA dfa, ATNConfigSet configs, int startIndex, int stopIndex) {
        if (debug || retry_debug) {
            System.out.println("reportAttemptingFullContext decision=" + dfa.decision + ":" + configs + ", input=" + parser.getInputString(startIndex, stopIndex));
        }
        if (parser != null)
            parser.getErrorHandler().reportAttemptingFullContext(parser, dfa, startIndex, stopIndex, configs);
    }

    public void reportContextSensitivity(DFA dfa, ATNConfigSet configs, int startIndex, int stopIndex) {
        if (debug || retry_debug) {
            System.out.println("reportContextSensitivity decision=" + dfa.decision + ":" + configs + ", input=" + parser.getInputString(startIndex, stopIndex));
        }
        if (parser != null)
            parser.getErrorHandler().reportContextSensitivity(parser, dfa, startIndex, stopIndex, configs);
    }

    public void reportAmbiguity(@NotNull DFA dfa, DFAState D, int startIndex, int stopIndex, @NotNull IntervalSet ambigAlts, @NotNull ATNConfigSet configs) {
        if (debug || retry_debug) {
            System.out.println("reportAmbiguity " + ambigAlts + ":" + configs + ", input=" + parser.getInputString(startIndex, stopIndex));
        }
        if (parser != null)
            parser.getErrorHandler().reportAmbiguity(parser, dfa, startIndex, stopIndex, ambigAlts, configs);
    }

    public void reportInsufficientPredicates(@NotNull DFA dfa, int startIndex, int stopIndex, @NotNull IntervalSet ambigAlts, DecisionState decState, @NotNull SemanticContext[] altToPred, @NotNull ATNConfigSet configs, boolean fullContextParse) {
        if (debug || retry_debug) {
            System.out.println("reportInsufficientPredicates " + ambigAlts + ", decState=" + decState + ": " + Arrays.toString(altToPred) + parser.getInputString(startIndex, stopIndex));
        }
        if (parser != null) {
            parser.getErrorHandler().reportInsufficientPredicates(parser, dfa, startIndex, stopIndex, ambigAlts, decState, altToPred, configs, fullContextParse);
        }
    }

    protected void closure(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean greedy, boolean loopsSimulateTailRecursion, int depth) {
        if (debug)
            System.out.println("closure(" + config.toString(parser, true) + ")");
        if (!closureBusy.add(config))
            return;
        if (config.state instanceof RuleStopState) {
            if (!greedy) {
                if (debug)
                    System.out.println("NONGREEDY at stop state of " + getRuleName(config.state.ruleIndex));
                configs.add(config);
                return;
            }
            if (config.context != null && !config.context.isEmpty()) {
                RuleContext newContext = config.context.parent;
                ATNState invokingState = atn.states.get(config.context.invokingState);
                RuleTransition rt = (RuleTransition) invokingState.transition(0);
                ATNState retState = rt.followState;
                ATNConfig c = new ATNConfig(retState, config.alt, newContext, config.semanticContext);
                c.reachesIntoOuterContext = config.reachesIntoOuterContext;
                closure(c, configs, closureBusy, collectPredicates, greedy, loopsSimulateTailRecursion, depth - 1);
                return;
            } else {
                if (debug)
                    System.out.println("FALLING off rule " + getRuleName(config.state.ruleIndex));
            }
        } else if (loopsSimulateTailRecursion) {
            if (config.state.getClass() == StarLoopbackState.class || config.state.getClass() == PlusLoopbackState.class) {
                config.context = new RuleContext(config.context, config.state.stateNumber);
                if (debug)
                    System.out.println("Loop back; push " + config.state.stateNumber + ", stack=" + config.context);
            } else if (config.state.getClass() == LoopEndState.class) {
                if (debug)
                    System.out.println("Loop end; pop, stack=" + config.context);
                RuleContext p = config.context;
                LoopEndState end = (LoopEndState) config.state;
                while (!p.isEmpty() && p.invokingState == end.loopBackStateNumber) {
                    p = config.context = config.context.parent;
                }
            }
        }
        ATNState p = config.state;
        if (!p.onlyHasEpsilonTransitions()) {
            configs.add(config);
            if (config.semanticContext != null && config.semanticContext != SemanticContext.NONE) {
                configs.hasSemanticContext = true;
            }
            if (config.reachesIntoOuterContext > 0) {
                configs.dipsIntoOuterContext = true;
            }
            if (debug)
                System.out.println("added config " + configs);
        }
        for (int i = 0; i < p.getNumberOfTransitions(); i++) {
            Transition t = p.transition(i);
            boolean continueCollecting = !(t instanceof ActionTransition) && collectPredicates;
            ATNConfig c = getEpsilonTarget(config, t, continueCollecting, depth == 0);
            if (c != null) {
                int newDepth = depth;
                if (config.state instanceof RuleStopState) {
                    c.reachesIntoOuterContext++;
                    configs.dipsIntoOuterContext = true;
                    newDepth--;
                    if (debug)
                        System.out.println("dips into outer ctx: " + c);
                } else if (t instanceof RuleTransition) {
                    if (newDepth >= 0) {
                        newDepth++;
                    }
                }
                closure(c, configs, closureBusy, continueCollecting, greedy, loopsSimulateTailRecursion, newDepth);
            }
        }
    }

    protected void closure(@NotNull ATNConfig config, @NotNull ATNConfigSet configs, @NotNull Set<ATNConfig> closureBusy, boolean collectPredicates, boolean greedy, boolean loopsSimulateTailRecursion) {
        final int initialDepth = 0;
        closure(config, configs, closureBusy, collectPredicates, greedy, loopsSimulateTailRecursion, initialDepth);
    }
}
