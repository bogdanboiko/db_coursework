package air.com.db_coursework.di

import air.com.db_coursework.data.LocalDatasourceImpl
import air.com.db_coursework.data.datasource.EducationDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {
    @Binds
    abstract fun localDatasource(localDatasourceImpl: LocalDatasourceImpl): EducationDatasource.Local
}