package air.com.db_coursework.data.model.result

data class StudentPreviewWithDebt(
    val id: String,
    val firstName: String,
    val lastName: String,
    val totalPayments: Double,
    val debt: Double
)