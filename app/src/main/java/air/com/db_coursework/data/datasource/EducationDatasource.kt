package air.com.db_coursework.data.datasource

import air.com.db_coursework.data.model.*
import air.com.db_coursework.data.model.result.*
import kotlinx.coroutines.flow.Flow

interface EducationDatasource {
    interface Local {
        suspend fun createCourse(course: CourseEntity)
        fun getCoursesWithEnrollmentCount(): Flow<List<CourseWithParticipantQuantity>>
        fun getCoursesWithEnrollmentCountFilteredByPrice(minPrice: Double, maxPrice: Double): Flow<List<CourseWithParticipantQuantity>>
        fun getStudentsPreviewWithDebt(courseId: String): Flow<List<StudentPreviewWithDebt>>
        suspend fun createStudent(student: StudentEntity)
        suspend fun createEnrollment(enrollment: EnrollmentEntity)
        fun getCourseDataWithMentor(courseId: String): Flow<CourseWithMentor>
        fun getStudentDetails(studentId: String): Flow<StudentDetails>
        suspend fun addPayment(paymentEntity: PaymentEntity)
        fun getStudentCoursePayment(studentId: String, courseId: String): Flow<List<PaymentEntity>>
        suspend fun addMentor(mentor: MentorEntity)
        fun getCoursesStatistics(): Flow<List<CourseWithDebtAndIncome>>
        fun getCoursesByPriceFrom(minPrice: Double): Flow<List<CourseWithParticipantQuantity>>
        fun getCoursesByPriceTo(maxPrice: Double): Flow<List<CourseWithParticipantQuantity>>
    }
}