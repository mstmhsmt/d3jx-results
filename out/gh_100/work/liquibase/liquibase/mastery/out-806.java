package liquibase.change;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.util.StringUtil;

public class ConstraintsConfig extends AbstractLiquibaseSerializable {

    private Boolean nullable;

    private String notNullConstraintName;

    private Boolean primaryKey;

    private String primaryKeyName;

    private String primaryKeyTablespace;

    private String references;

    private String referencedTableCatalogName;

    private String referencedTableSchemaName;

    private String referencedTableName;

    private String referencedColumnNames;

    private Boolean unique;

    private String uniqueConstraintName;

    private String checkConstraint;

    private Boolean deleteCascade;

    private String foreignKeyName;

    private Boolean initiallyDeferred;

    private Boolean deferrable;

    public Boolean isNullable() {
        return nullable;
    }

    public ConstraintsConfig setNullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public ConstraintsConfig setNullable(String nullable) {
        this.nullable = parseBoolean(nullable);
        return this;
    }

    public String getNotNullConstraintName() {
        return notNullConstraintName;
    }

    public ConstraintsConfig setNotNullConstraintName(String notNullConstraintName) {
        this.notNullConstraintName = notNullConstraintName;
        return this;
    }

    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    public ConstraintsConfig setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public ConstraintsConfig setPrimaryKey(String primaryKey) {
        this.primaryKey = parseBoolean(primaryKey);
        return this;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public ConstraintsConfig setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
        return this;
    }

    public String getReferences() {
        return references;
    }

    public ConstraintsConfig setReferences(String references) {
        this.references = references;
        return this;
    }

    public Boolean isUnique() {
        return unique;
    }

    public ConstraintsConfig setUnique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    public ConstraintsConfig setUnique(String unique) {
        this.unique = parseBoolean(unique);
        return this;
    }

    public String getUniqueConstraintName() {
        return uniqueConstraintName;
    }

    public ConstraintsConfig setUniqueConstraintName(String uniqueConstraintName) {
        this.uniqueConstraintName = uniqueConstraintName;
        return this;
    }

    public String getCheckConstraint() {
        return checkConstraint;
    }

    public ConstraintsConfig setCheckConstraint(String checkConstraint) {
        this.checkConstraint = checkConstraint;
        return this;
    }

    public Boolean isDeleteCascade() {
        return deleteCascade;
    }

    public ConstraintsConfig setDeleteCascade(Boolean deleteCascade) {
        this.deleteCascade = deleteCascade;
        return this;
    }

    public ConstraintsConfig setDeleteCascade(String deleteCascade) {
        this.deleteCascade = parseBoolean(deleteCascade);
        return this;
    }

    public String getForeignKeyName() {
        return foreignKeyName;
    }

    public ConstraintsConfig setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
        return this;
    }

    public Boolean isInitiallyDeferred() {
        return initiallyDeferred;
    }

    public ConstraintsConfig setInitiallyDeferred(Boolean initiallyDeferred) {
        this.initiallyDeferred = initiallyDeferred;
        return this;
    }

    public ConstraintsConfig setInitiallyDeferred(String initiallyDeferred) {
        this.initiallyDeferred = parseBoolean(initiallyDeferred);
        return this;
    }

    public Boolean isDeferrable() {
        return deferrable;
    }

    public ConstraintsConfig setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
        return this;
    }

    public ConstraintsConfig setShouldValidateUnique(String validateUnique) {
        this.validateUnique = parseBoolean(validateUnique);
        return this;
    }

    public ConstraintsConfig setShouldValidateUnique(Boolean validateUnique) {
        this.validateUnique = validateUnique;
        return this;
    }

    public ConstraintsConfig setDeferrable(String deferrable) {
        this.deferrable = parseBoolean(deferrable);
        return this;
    }

    public String getPrimaryKeyTablespace() {
        return primaryKeyTablespace;
    }

    public ConstraintsConfig setPrimaryKeyTablespace(String primaryKeyTablespace) {
        this.primaryKeyTablespace = primaryKeyTablespace;
        return this;
    }

    public String getReferencedTableCatalogName() {
        return referencedTableCatalogName;
    }

    public void setReferencedTableCatalogName(String referencedTableCatalogName) {
        this.referencedTableCatalogName = referencedTableCatalogName;
    }

    public String getReferencedTableSchemaName() {
        return referencedTableSchemaName;
    }

    public void setReferencedTableSchemaName(String referencedTableSchemaName) {
        this.referencedTableSchemaName = referencedTableSchemaName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnNames() {
        return referencedColumnNames;
    }

    public void setReferencedColumnNames(String referencedColumnNames) {
        this.referencedColumnNames = referencedColumnNames;
    }

    private Boolean parseBoolean(String value) {
        value = StringUtil.trimToNull(value);
        if ((value == null) || "null".equalsIgnoreCase(value)) {
            return null;
        } else {
            if ("true".equalsIgnoreCase(value) || "1".equals(value)) {
                return true;
            } else if ("false".equalsIgnoreCase(value) || "0".equals(value)) {
                return false;
            } else {
                throw new UnexpectedLiquibaseException("Unparsable boolean value: " + value);
            }
        }
    }

    @Override
    public String getSerializedObjectName() {
        return "constraints";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return STANDARD_CHANGELOG_NAMESPACE;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        throw new RuntimeException("TODO");
    }

    public Boolean shouldValidateNullable() {
        return validateNullable;
    }

    private Boolean validateForeignKey;

    public Boolean shouldValidateUnique() {
        return validateUnique;
    }

    private Boolean validatePrimaryKey;

    public Boolean shouldValidatePrimaryKey() {
        return validatePrimaryKey;
    }

    private Boolean validateUnique;

    public Boolean shouldValidateForeignKey() {
        return validateForeignKey;
    }

    private Boolean validateNullable;

    public ConstraintsConfig setShouldValidateForeignKey(Boolean validateForeignKey) {
        this.validateForeignKey = validateForeignKey;
        return this;
    }

    public ConstraintsConfig setShouldValidatePrimaryKey(Boolean validatePrimaryKey) {
        this.validatePrimaryKey = validatePrimaryKey;
        return this;
    }

    public ConstraintsConfig setShouldValidateNullable(Boolean validateNullable) {
        this.validateNullable = validateNullable;
        return this;
    }

    public ConstraintsConfig setShouldValidateForeignKey(String validateForeignKey) {
        this.validateForeignKey = parseBoolean(validateForeignKey);
        return this;
    }

    public ConstraintsConfig setShouldValidatePrimaryKey(String validatePrimaryKey) {
        this.validatePrimaryKey = parseBoolean(validatePrimaryKey);
        return this;
    }

    public ConstraintsConfig setShouldValidateNullable(String validateNullable) {
        this.validateNullable = parseBoolean(validateNullable);
        return this;
    }
}
