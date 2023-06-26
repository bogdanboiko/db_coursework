package air.com.db_coursework.data.model.result

import androidx.room.ColumnInfo

data class CourseWithMentor(
    val courseId: String,
    val name: String,
    val description: String,
    val price: Double,
    val mentorName: String,
    val contact: String,
    val experienceYears: Int
)