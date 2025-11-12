package org.apache.cassandra.cql3.statements;

import org.apache.cassandra.cql3.CFName;
import org.apache.cassandra.cql3.CQLStatement;
import org.apache.cassandra.cql3.QueryOptions;
import org.apache.cassandra.exceptions.*;
import org.apache.cassandra.service.ClientState;
import org.apache.cassandra.service.QueryState;
import org.apache.cassandra.transport.messages.ResultMessage;
import org.apache.cassandra.transport.Event;

public abstract class SchemaAlteringStatement extends CFStatement implements CQLStatement {

    private final boolean isColumnFamilyLevel;

    protected SchemaAlteringStatement() {
        super(null);
        this.isColumnFamilyLevel = false;
    }

    protected SchemaAlteringStatement(CFName name) {
        super(name);
        this.isColumnFamilyLevel = true;
    }

    public int getBoundTerms() {
        return 0;
    }

    @Override
    public void prepareKeyspace(ClientState state) throws InvalidRequestException {
        if (isColumnFamilyLevel)
            super.prepareKeyspace(state);
    }

    @Override
    public Prepared prepare() {
        return new Prepared(this);
    }

    public abstract boolean announceMigration(boolean isLocalOnly) throws RequestValidationException;

    public ResultMessage execute(QueryState state, QueryOptions options) throws RequestValidationException {
        boolean didChangeSchema = announceMigration(false);
        if (!didChangeSchema)
            return new ResultMessage.Void();
        return new ResultMessage.SchemaChange(changeEvent());
    }

    public ResultMessage executeInternal(QueryState state, QueryOptions options) {
        try {
            announceMigration(true);
            return new ResultMessage.SchemaChange(changeEvent());
        } catch (RequestValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Event.SchemaChange changeEvent();
}
