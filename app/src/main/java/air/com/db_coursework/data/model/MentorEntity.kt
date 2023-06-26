package air.com.db_coursework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Mentor",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class MentorEntity(
    @PrimaryKey
    val id: String,
    val courseId: String,
    val name: String,
    val contact: String,
    val experienceYears: Int
)