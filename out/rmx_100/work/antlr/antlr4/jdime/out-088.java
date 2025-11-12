package org.antlr.v4.runtime.atn;
import org.antlr.v4.runtime.Recognizer;

public final class EmptyPredictionContext extends PredictionContext {
  public static final EmptyPredictionContext LOCAL_CONTEXT = new EmptyPredictionContext(false);

  public static final EmptyPredictionContext FULL_CONTEXT = new EmptyPredictionContext(true);

  private final boolean fullContext;

  private EmptyPredictionContext(boolean fullContext) {
    super(calculateHashCode(calculateEmptyParentHashCode(), calculateEmptyInvokingStateHashCode()), EMPTY_RETURN_STATE);
    this.fullContext = fullContext;
  }

  public boolean isFullContext() {
    return fullContext;
  }

  @Override protected PredictionContext addEmptyContext() {
    return this;
  }

  @Override protected PredictionContext removeEmptyContext() {
    throw new UnsupportedOperationException("Cannot remove the empty context from itself.");
  }

  @Override public PredictionContext getParent(int index) {
    throw new IndexOutOfBoundsException();
  }

  @Override public boolean isEmpty() {
    return true;
  }


<<<<<<< commits-rmx_100/antlr/antlr4/35c384169ffadc5b21c66ee3a8e444760adcd4a9/EmptyPredictionContext-db10a12.java
  @Override public int getInvokingState(int index) {
    throw new IndexOutOfBoundsException();
  }
=======
>>>>>>> Unknown file: This is a bug in JDime.


  @Override public int findInvokingState(int invokingState) {
    return -1;
  }

  @Override public int size() {
    return 0;
  }

  @Override public PredictionContext appendContext(int invokingContext, PredictionContextCache contextCache) {
    return contextCache.getChild(this, invokingContext);
  }

  @Override public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
    return suffix;
  }

  @Override public boolean hasEmpty() {
    return true;
  }

  @Override public int getReturnState(int index) {
    return returnState;
  }

  @Override public boolean equals(Object o) {
    return this == o;
  }

  @Override public String[] toStrings(Recognizer<?, ?> recognizer, int currentState) {
    return new String[] { "[]" };
  }

  @Override public String[] toStrings(Recognizer<?, ?> recognizer, PredictionContext stop, int currentState) {
    return new String[] { "[]" };
  }
}