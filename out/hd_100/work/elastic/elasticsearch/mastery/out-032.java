package org.elasticsearch.action.support;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.common.ParseFieldMatcher;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.tasks.TaskManager;
import org.elasticsearch.threadpool.ThreadPool;
import java.util.concurrent.atomic.AtomicInteger;
import static org.elasticsearch.action.support.PlainActionFuture.newFuture;
import org.elasticsearch.tasks.TaskListener;

public abstract class TransportAction<Request extends ActionRequest<Request>, Response extends ActionResponse> extends AbstractComponent {

    protected final ThreadPool threadPool;

    protected final String actionName;

    final private ActionFilter[] filters;

    protected final ParseFieldMatcher parseFieldMatcher;

    protected final IndexNameExpressionResolver indexNameExpressionResolver;

    protected final TaskManager taskManager;

    protected TransportAction(Settings settings, String actionName, ThreadPool threadPool, ActionFilters actionFilters, IndexNameExpressionResolver indexNameExpressionResolver, TaskManager taskManager) {
        super(settings);
        this.threadPool = threadPool;
        this.actionName = actionName;
        this.filters = actionFilters.filters();
        this.parseFieldMatcher = new ParseFieldMatcher(settings);
        this.indexNameExpressionResolver = indexNameExpressionResolver;
        this.taskManager = taskManager;
    }

    final public ActionFuture<Response> execute(Request request) {
        PlainActionFuture<Response> future = newFuture();
        execute(request, future);
        return future;
    }

    final public Task execute(Request request, ActionListener<Response> listener) {
        Task task = taskManager.register("transport", actionName, request);
        if (task == null) {
            execute(null, request, listener);
        } else {
            execute(task, request, new ActionListener<Response>() {

                @Override
                public void onResponse(Response response) {
                    taskManager.unregister(task);
                    listener.onResponse(response);
                }

                @Override
                public void onFailure(Throwable e) {
                    taskManager.unregister(task);
                    listener.onFailure(e);
                }
            });
        }
        return task;
    }

    final public void execute(Task task, Request request, ActionListener<Response> listener) {
        ActionRequestValidationException validationException = request.validate();
        if (validationException != null) {
            listener.onFailure(validationException);
            return;
        }
        if (filters.length == 0) {
            try {
                doExecute(task, request, listener);
            } catch (Throwable t) {
                logger.trace("Error during transport action execution.", t);
                listener.onFailure(t);
            }
        } else {
            RequestFilterChain<Request, Response> requestFilterChain = new RequestFilterChain<>(this, logger);
            requestFilterChain.proceed(task, actionName, request, listener);
        }
    }

    protected void doExecute(Task task, Request request, ActionListener<Response> listener) {
        doExecute(request, listener);
    }

    protected abstract void doExecute(Request request, ActionListener<Response> listener);

    private static class RequestFilterChain<Request extends ActionRequest<Request>, Response extends ActionResponse> implements ActionFilterChain<Request, Response> {

        final private TransportAction<Request, Response> action;

        final private AtomicInteger index = new AtomicInteger();

        final private ESLogger logger;

        private RequestFilterChain(TransportAction<Request, Response> action, ESLogger logger) {
            this.action = action;
            this.logger = logger;
        }

        @Override
        public void proceed(Task task, String actionName, Request request, ActionListener<Response> listener) {
            int i = index.getAndIncrement();
            try {
                if (i < this.action.filters.length) {
                    this.action.filters[i].apply(task, actionName, request, listener, this);
                } else if (i == this.action.filters.length) {
                    this.action.doExecute(task, request, new FilteredActionListener<Response>(actionName, listener, new ResponseFilterChain<>(this.action.filters, logger)));
                } else {
                    listener.onFailure(new IllegalStateException("proceed was called too many times"));
                }
            } catch (Throwable t) {
                logger.trace("Error during transport action execution.", t);
                listener.onFailure(t);
            }
        }

        @Override
        public void proceed(String action, Response response, ActionListener<Response> listener) {
            assert false : "request filter chain should never be called on the response side";
        }
    }

    private static class ResponseFilterChain<Request extends ActionRequest<Request>, Response extends ActionResponse> implements ActionFilterChain<Request, Response> {

        final private ActionFilter[] filters;

        final private AtomicInteger index;

        final private ESLogger logger;

        private ResponseFilterChain(ActionFilter[] filters, ESLogger logger) {
            this.filters = filters;
            this.index = new AtomicInteger(filters.length);
            this.logger = logger;
        }

        @Override
        public void proceed(Task task, String action, Request request, ActionListener<Response> listener) {
            assert false : "response filter chain should never be called on the request side";
        }

        @Override
        public void proceed(String action, Response response, ActionListener<Response> listener) {
            int i = index.decrementAndGet();
            try {
                if (i >= 0) {
                    filters[i].apply(action, response, listener, this);
                } else if (i == -1) {
                    listener.onResponse(response);
                } else {
                    listener.onFailure(new IllegalStateException("proceed was called too many times"));
                }
            } catch (Throwable t) {
                logger.trace("Error during transport action execution.", t);
                listener.onFailure(t);
            }
        }
    }

    private static class FilteredActionListener<Response extends ActionResponse> implements ActionListener<Response> {

        final private String actionName;

        @Override
        public void onResponse(Response response) {
            chain.proceed(actionName, response, listener);
        }

        @Override
        public void onFailure(Throwable e) {
            listener.onFailure(e);
        }

        final private ActionListener<Response> listener;

        final private ResponseFilterChain<?, Response> chain;

        private FilteredActionListener(String actionName, ActionListener<Response> listener, ResponseFilterChain<?, Response> chain) {
            this.actionName = actionName;
            this.listener = listener;
            this.chain = chain;
        }
    }

    final public Task execute(Request request, TaskListener<Response> listener) {
        Task task = taskManager.register("transport", actionName, request);
        execute(task, request, new ActionListener<Response>() {

            @Override
            public void onResponse(Response response) {
                if (task != null) {
                    taskManager.unregister(task);
                }
                listener.onResponse(task, response);
            }

            @Override
            public void onFailure(Throwable e) {
                if (task != null) {
                    taskManager.unregister(task);
                }
                listener.onFailure(task, e);
            }
        });
        return task;
    }
}
