package io.archimedesfw.data.tx.spring

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Factory
internal class DataSourceTxManagerFactory {

    @Singleton
    fun transactionManager(dataSource: DataSource): DataSourceTransactionManager =
        DataSourceTransactionManager(dataSource)

}
