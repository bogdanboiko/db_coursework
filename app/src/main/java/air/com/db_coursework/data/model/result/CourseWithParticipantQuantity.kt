package air.com.db_coursework.data.model.result

import androidx.room.Entity

@Entity
data class CourseWithParticipantQuantity(
    val id: String,
    val name: String,
    val price: Double,
    val enrollments: Double
)