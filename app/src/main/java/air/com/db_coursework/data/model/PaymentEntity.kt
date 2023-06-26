package air.com.db_coursework.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Payment",
    foreignKeys = [ForeignKey(
        entity = StudentEntity::class,
        parentColumns = ["id"],
        childColumns = ["studentId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = CourseEntity::class,
        parentColumns = ["id"],
        childColumns = ["courseId"]
    )]
)
data class PaymentEntity(
    @PrimaryKey
    val id: String,
    val studentId: String,
    val courseId: String,
    val amount: String,
    val date: String
)