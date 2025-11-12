package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Recognizer;

public final class EmptyPredictionContext extends PredictionContext {

    private EmptyPredictionContext(boolean fullContext) {
        super(calculateHashCode(calculateEmptyParentHashCode(), calculateEmptyInvokingStateHashCode()), 

);
        this.fullContext = fullContext;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public PredictionContext getParent(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getReturnState(int index) {
        
throw new IndexOutOfBoundsException();

    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    public static final EmptyPredictionContext LOCAL_CONTEXT = new EmptyPredictionContext(false);

    @Override
    public String[] toStrings(Recognizer<?, ?> recognizer, PredictionContext stop, int currentState) {
        return new String[] { "[]" };
    }

    @Override
    public String[] toStrings(Recognizer<?, ?> recognizer, int currentState) {
        return new String[] { "[]" };
    }

    final private boolean fullContext;

    @Override
    protected PredictionContext addEmptyContext() {
        return this;
    }

    @Override
    public int findInvokingState(int invokingState) {
        return -1;
    }

    public boolean isFullContext() {
        return fullContext;
    }

    @Override
    public boolean hasEmpty() {
        return true;
    }

    @Override
    public PredictionContext appendContext(int invokingContext, PredictionContextCache contextCache) {
        return contextCache.getChild(this, invokingContext);
    }

    @Override
    protected PredictionContext removeEmptyContext() {
        throw new UnsupportedOperationException("Cannot remove the empty context from itself.");
    }

    public static final EmptyPredictionContext FULL_CONTEXT = new EmptyPredictionContext(true);

    @Override
    public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
        return suffix;
    }
}
