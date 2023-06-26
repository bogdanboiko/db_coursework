package air.com.db_coursework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
data class StudentEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)