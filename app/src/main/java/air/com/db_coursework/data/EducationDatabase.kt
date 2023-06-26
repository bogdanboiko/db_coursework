package air.com.db_coursework.data

import air.com.db_coursework.data.model.*
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CourseEntity::class,
        EnrollmentEntity::class,
        PaymentEntity::class,
        StudentEntity::class,
        MentorEntity::class
    ], version = 4
)
abstract class EducationDatabase : RoomDatabase() {
    abstract fun getEducationDao(): EducationDao
}