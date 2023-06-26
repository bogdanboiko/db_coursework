package air.com.db_coursework.data.model.result

data class StudentDetails(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enrollmentDate: String,
    val debt: Double
)