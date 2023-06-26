package air.com.db_coursework.data.model.result

data class CourseWithDebtAndIncome(
    val courseId: String,
    val courseName: String,
    val totalPayments: Double,
    val debt: Double
)