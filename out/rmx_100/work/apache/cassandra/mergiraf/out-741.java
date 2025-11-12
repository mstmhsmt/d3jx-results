/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.cql3.statements;

import org.apache.cassandra.cql3.CFName;
import org.apache.cassandra.cql3.CQLStatement;
import org.apache.cassandra.cql3.QueryOptions;
import org.apache.cassandra.exceptions.*;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.transport.Event;
import org.apache.cassandra.transport.messages.ResultMessage;

/**
 * Abstract class for statements that alter the schema.
 */
public abstract class SchemaAlteringStatement extends CFStatement implements CQLStatement
{
    private final boolean isColumnFamilyLevel;

    protected SchemaAlteringStatement()
    {
        super(null);
        this.isColumnFamilyLevel = false;
    }

    protected SchemaAlteringStatement(CFName name)
    {
        super(name);
        this.isColumnFamilyLevel = true;
    }

    public int getBoundTerms()
    {
        return 0;
    }

    @Override
    public void prepareKeyspace(ClientState state) throws InvalidRequestException
    {
        if (isColumnFamilyLevel)
            super.prepareKeyspace(state);
    }

    @Override
    public Prepared prepare()
    {
        return new Prepared(this);
    }

    public abstract Event.SchemaChange changeEvent();

    /**
     * Announces the migration to other nodes in the cluster.
     * @return true if the execution of this statement resulted in a schema change, false otherwise (when IF NOT EXISTS
     * is used, for example)
     * @throws RequestValidationException
     */
    public abstract boolean announceMigration(boolean isLocalOnly) throws RequestValidationException;

    public ResultMessage execute(QueryState state, QueryOptions options) throws RequestValidationException
    {
<<<<<<< commits-rmx_100/apache/cassandra/d088f02993e44089d6bd7711aae549e7f2bc37f5/SchemaAlteringStatement-e70aac9.java
        announceMigration(false);
        return new ResultMessage.SchemaChange(changeEvent());
||||||| commits-rmx_100/apache/cassandra/fe39eb7a9e2b017e3cd31b1c09693c8d565dee18/SchemaAlteringStatement-94df854.java
        announceMigration();
        String tableName = cfName == null || columnFamily() == null ? "" : columnFamily();
        return new ResultMessage.SchemaChange(changeType(), keyspace(), tableName);
=======
        // If an IF [NOT] EXISTS clause was used, this may not result in an actual schema change.  To avoid doing
        // extra work in the drivers to handle schema changes, we return an empty message in this case. (CASSANDRA-7600)
        boolean didChangeSchema = announceMigration();
        if (!didChangeSchema)
            return new ResultMessage.Void();

        String tableName = cfName == null || columnFamily() == null ? "" : columnFamily();
        return new ResultMessage.SchemaChange(changeType(), keyspace(), tableName);
>>>>>>> commits-rmx_100/apache/cassandra/e4d5edae72838533445efadc001d2b6b656fd0ce/SchemaAlteringStatement-845d8cc.java
    }

    public ResultMessage executeInternal(QueryState state, QueryOptions options)
    {
        try
        {
            announceMigration(true);
            return new ResultMessage.SchemaChange(changeEvent());
        }
        catch (RequestValidationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
