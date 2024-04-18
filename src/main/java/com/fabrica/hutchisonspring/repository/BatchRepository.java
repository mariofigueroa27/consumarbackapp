package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.config.ApplicationProperties;
import com.fabrica.hutchisonspring.utils.vm.ColumnMetaData;
import com.fabrica.hutchisonspring.web.rest.errors.BadRequestAlertException;
import com.microsoft.sqlserver.jdbc.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

@Repository
public class BatchRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationProperties applicationProperties;

    public BatchRepository(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Bulk load from jdbc for MSSQLServer
     *
     * @param clazz the class to be implemented
     */
    public void bulkSave(Class<T> clazz, ColumnMetaData[] data, boolean includeId) {
        String tableName = clazz.getAnnotation(Table.class).name();
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            var bulkConnection = connection.unwrap(SQLServerConnection.class);
            var record = new SQLServerBulkCSVFileRecord(applicationProperties.getBatchFolder().getPath() + tableName
                    + applicationProperties.getBatchFolder().getOkSuffix()
                    + applicationProperties.getBatchFolder().getExtension(), null, "\\|", false);

            for (ColumnMetaData col : data) {
                record.addColumnMetadata(col.getPointInSource(), col.getName(), col.getJdbcType(), col.getPrecision(),
                        col.getScale());
            }

            var options = new SQLServerBulkCopyOptions();
            options.setBatchSize(4000);
            options.setTableLock(true);
            options.setKeepIdentity(includeId);

            var bulkCopy = new SQLServerBulkCopy(bulkConnection);
            bulkCopy.setBulkCopyOptions(options);
            bulkCopy.setDestinationTableName(tableName);
            try {
                bulkCopy.writeToServer(record);
            } catch (SQLServerException e) {
                sqlServerExceptionHandler(clazz.getSimpleName(), e);
            } finally {
                bulkCopy.close();
            }
        });
    }

    private void sqlServerExceptionHandler(String className, SQLServerException e) {
        SQLServerError error = e.getSQLServerError();
        // for more information about every error number see:
        // https://docs.microsoft.com/en-us/sql/relational-databases/errors-events/database-engine-events-and-errors
        if (error != null && (error.getErrorNumber() == 2601 || error.getErrorNumber() == 2627)) {
            throw new BadRequestAlertException(error.getErrorMessage(), className, "uniqueConstraintViolation");
        }
        e.printStackTrace(System.out);
    }
}
