package air.com.db_coursework.di

import air.com.db_coursework.data.EducationDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, EducationDatabase::class.java, "education_database")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideEducationDao(educationDatabase: EducationDatabase) = educationDatabase.getEducationDao()
}