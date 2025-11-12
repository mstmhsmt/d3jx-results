/*
 * [The "BSD license"]
 *  Copyright (c) 2012 Sam Harwell
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *  1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.Recognizer;

public final class EmptyPredictionContext extends PredictionContext {
	public static final EmptyPredictionContext LOCAL_CONTEXT = new EmptyPredictionContext(false);
<<<<<<< commits-rmx_100/antlr/antlr4/35c384169ffadc5b21c66ee3a8e444760adcd4a9/EmptyPredictionContext-db10a12.java
	public static final EmptyPredictionContext FULL_CONTEXT = new EmptyPredictionContext(true);

	private final boolean fullContext;

	private EmptyPredictionContext(boolean fullContext) {
		super(calculateHashCode(calculateEmptyParentHashCode(), calculateEmptyInvokingStateHashCode()));
||||||| commits-rmx_100/antlr/antlr4/2ce42f46f8a3b8cf7cf3ee75b45a663057b32505/EmptyPredictionContext-690b32f.java
		super(null, EMPTY_INVOKING_STATE);
=======
		super(null, EMPTY_RETURN_STATE);
>>>>>>> commits-rmx_100/antlr/antlr4/347097874998b076937daeba3d165e780637cea6/EmptyPredictionContext-0cd3d0b.java
		this.fullContext = fullContext;
	}

	public boolean isFullContext() {
		return fullContext;
	}

	@Override
	protected PredictionContext addEmptyContext() {
		return this;
	}

	@Override
	protected PredictionContext removeEmptyContext() {
		throw new UnsupportedOperationException("Cannot remove the empty context from itself.");
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
	public int findInvokingState(int returnState) {
		return -1;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public PredictionContext appendContext(int invokingContext, PredictionContextCache contextCache) {
		return contextCache.getChild(this, invokingContext);
	}

	@Override
	public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
		return suffix;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean hasEmpty() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	@Override
	public String[] toStrings(Recognizer<?, ?> recognizer, int currentState) {
		return new String[] { "[]" };
	}

	@Override
	public String[] toStrings(Recognizer<?, ?> recognizer, PredictionContext stop, int currentState) {
		return new String[] { "[]" };
	}

}
