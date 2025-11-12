/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */

package org.elasticsearch.action.admin.indices.close;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionType;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.DestructiveOperations;
import org.elasticsearch.action.support.master.TransportMasterNodeAction;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.block.ClusterBlockException;
import org.elasticsearch.cluster.block.ClusterBlockLevel;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.metadata.MetadataIndexStateService;
import org.elasticsearch.cluster.metadata.ProjectMetadata;
import org.elasticsearch.cluster.project.ProjectResolver;
import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Setting.Property;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.index.Index;
import org.elasticsearch.injection.guice.Inject;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

import java.util.Arrays;
import java.util.Collections;

/**
 * Close index action
 */
public class TransportCloseIndexAction extends TransportMasterNodeAction<CloseIndexRequest, CloseIndexResponse> {

    public static final String NAME = "indices:admin/close";
    public static final ActionType<CloseIndexResponse> TYPE = new ActionType<>(NAME);
    private static final Logger logger = LogManager.getLogger(TransportCloseIndexAction.class);

    private final MetadataIndexStateService indexStateService;
<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/e6a3aff0938d73b9397e22b0a20f1328b4ac1ab0/TransportCloseIndexAction-91a2c0c.java
    private final ProjectResolver projectResolver;
||||||| commits-rmx_100/elasticsearch/elasticsearch/13c75c02f0f328c1a3424cf68ac43d6551d7b007/TransportCloseIndexAction-5a42928.java
=======
    private final IndexNameExpressionResolver indexNameExpressionResolver;
>>>>>>> commits-rmx_100/elasticsearch/elasticsearch/90bfdbc2bc6bedff8edcb07d2168f0684b16b3fa/TransportCloseIndexAction-2362be4.java
    private final DestructiveOperations destructiveOperations;
    private volatile boolean closeIndexEnabled;
    public static final Setting<Boolean> CLUSTER_INDICES_CLOSE_ENABLE_SETTING = Setting.boolSetting(
        "cluster.indices.close.enable",
        true,
        Property.Dynamic,
        Property.NodeScope
    );

    @Inject
    public TransportCloseIndexAction(
        Settings settings,
        TransportService transportService,
        ClusterService clusterService,
        ThreadPool threadPool,
        MetadataIndexStateService indexStateService,
        ClusterSettings clusterSettings,
        ActionFilters actionFilters,
        ProjectResolver projectResolver,
        IndexNameExpressionResolver indexNameExpressionResolver,
        DestructiveOperations destructiveOperations
    ) {
        super(
            NAME,
            transportService,
            clusterService,
            threadPool,
            actionFilters,
            CloseIndexRequest::new,
            CloseIndexResponse::new,
            EsExecutors.DIRECT_EXECUTOR_SERVICE
        );
        this.indexStateService = indexStateService;
<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/e6a3aff0938d73b9397e22b0a20f1328b4ac1ab0/TransportCloseIndexAction-91a2c0c.java
        this.projectResolver = projectResolver;
||||||| commits-rmx_100/elasticsearch/elasticsearch/13c75c02f0f328c1a3424cf68ac43d6551d7b007/TransportCloseIndexAction-5a42928.java
=======
        this.indexNameExpressionResolver = indexNameExpressionResolver;
>>>>>>> commits-rmx_100/elasticsearch/elasticsearch/90bfdbc2bc6bedff8edcb07d2168f0684b16b3fa/TransportCloseIndexAction-2362be4.java
        this.destructiveOperations = destructiveOperations;
        this.closeIndexEnabled = CLUSTER_INDICES_CLOSE_ENABLE_SETTING.get(settings);
        clusterSettings.addSettingsUpdateConsumer(CLUSTER_INDICES_CLOSE_ENABLE_SETTING, this::setCloseIndexEnabled);
    }

    private void setCloseIndexEnabled(boolean closeIndexEnabled) {
        this.closeIndexEnabled = closeIndexEnabled;
    }

    @Override
    protected void doExecute(Task task, CloseIndexRequest request, ActionListener<CloseIndexResponse> listener) {
        destructiveOperations.failDestructive(request.indices());
        if (closeIndexEnabled == false) {
            throw new IllegalStateException(
                "closing indices is disabled - set ["
                    + CLUSTER_INDICES_CLOSE_ENABLE_SETTING.getKey()
                    + ": true] to enable it. NOTE: closed indices still consume a significant amount of diskspace"
            );
        }
        super.doExecute(task, request, listener);
    }

    @Override
    protected ClusterBlockException checkBlock(CloseIndexRequest request, ClusterState state) {
        final ProjectMetadata projectMetadata = projectResolver.getProjectMetadata(state);
        return state.blocks()
            .indicesBlockedException(
                projectMetadata.id(),
                ClusterBlockLevel.METADATA_WRITE,
                indexNameExpressionResolver.concreteIndexNames(projectMetadata, request)
            );
    }

    @Override
    protected void masterOperation(
        final Task task,
        final CloseIndexRequest request,
        final ClusterState state,
        final ActionListener<CloseIndexResponse> listener
    ) throws Exception {
        final Index[] concreteIndices = indexNameExpressionResolver.concreteIndices(state, request);
        if (concreteIndices == null || concreteIndices.length == 0) {
            listener.onResponse(new CloseIndexResponse(true, false, Collections.emptyList()));
            return;
        }

        final CloseIndexClusterStateUpdateRequest closeRequest = new CloseIndexClusterStateUpdateRequest(
            request.masterNodeTimeout(),
            request.ackTimeout(),
            projectResolver.getProjectId(),
            task.getId(),
            request.waitForActiveShards(),
            concreteIndices
        );
        indexStateService.closeIndices(closeRequest, listener.delegateResponse((delegatedListener, t) -> {
            logger.debug(() -> "failed to close indices [" + Arrays.toString(concreteIndices) + "]", t);
            delegatedListener.onFailure(t);
        }));
    }
}
