package org.antlr.v4.runtime;

import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.atn.PredicateTransition;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import java.util.Locale;
import org.antlr.v4.runtime.atn.AbstractPredicateTransition;

public class FailedPredicateException extends RecognitionException {

    private final int ruleIndex;

    private final int predicateIndex;

    private final String predicate;

    public FailedPredicateException(@NotNull Parser recognizer) {
        this(recognizer, null);
    }

    public FailedPredicateException(@NotNull Parser recognizer, @Nullable String predicate) {
        this(recognizer, predicate, null);
    }

    public FailedPredicateException(@NotNull Parser recognizer, @Nullable String predicate, @Nullable String message) {
        super(formatMessage(predicate, message), recognizer, recognizer.getInputStream(), recognizer._ctx);
        ATNState s = recognizer.getInterpreter().atn.states.get(recognizer.getState());
        AbstractPredicateTransition trans = (AbstractPredicateTransition) s.transition(0);
        if (trans instanceof PredicateTransition) {
            this.ruleIndex = ((PredicateTransition) trans).ruleIndex;
            this.predicateIndex = ((PredicateTransition) trans).predIndex;
        } else {
            this.ruleIndex = 0;
            this.predicateIndex = 0;
        }
        this.predicate = predicate;
        this.setOffendingToken(recognizer.getCurrentToken());
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public int getPredIndex() {
        return predicateIndex;
    }

    @Nullable
    public String getPredicate() {
        return predicate;
    }

    @NotNull
    private static String formatMessage(@Nullable String predicate, @Nullable String message) {
        if (message != null) {
            return message;
        }
        return String.format(Locale.getDefault(), "failed predicate: {%s}?", predicate);
    }
}
