package air.com.db_coursework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Course")
data class CourseEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double
)