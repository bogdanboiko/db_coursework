package air.com.db_coursework.data

import air.com.db_coursework.data.datasource.EducationDatasource
import air.com.db_coursework.data.model.*
import air.com.db_coursework.data.model.result.CourseWithMentor
import air.com.db_coursework.data.model.result.CourseWithParticipantQuantity
import air.com.db_coursework.data.model.result.StudentDetails
import air.com.db_coursework.data.model.result.StudentPreviewWithDebt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(private val educationDao: EducationDao) : EducationDatasource.Local {
    override suspend fun createCourse(course: CourseEntity) {
        educationDao.addCourse(course)
    }

    override suspend fun createStudent(student: StudentEntity) {
        educationDao.addStudent(student)
    }

    override suspend fun createEnrollment(enrollment: EnrollmentEntity) {
        educationDao.addEnrollment(enrollment)
    }

    override fun getCoursesWithEnrollmentCount() = educationDao.getCoursesWithEnrollmentCount()

    override fun getCoursesWithEnrollmentCountFilteredByPrice(minPrice: Double, maxPrice: Double): Flow<List<CourseWithParticipantQuantity>> {
        return educationDao.getCoursesByPriceRange(minPrice, maxPrice)
    }

    override fun getCoursesByPriceFrom(minPrice: Double) = educationDao.getCoursesByPriceFrom(minPrice)

    override fun getCoursesByPriceTo(maxPrice: Double) = educationDao.getCoursesByPriceTo(maxPrice)

    override fun getStudentsPreviewWithDebt(courseId: String): Flow<List<StudentPreviewWithDebt>> {
        return educationDao.getStudentsPreviewsWithDebt(courseId)
    }

    override fun getCourseDataWithMentor(courseId: String): Flow<CourseWithMentor> {
        return educationDao.getCourseDataWithMentor(courseId)
    }

    override fun getStudentDetails(studentId: String) : Flow<StudentDetails> = educationDao.getStudentDetails(studentId)

    override suspend fun addPayment(paymentEntity: PaymentEntity) = educationDao.addPayment(paymentEntity)

    override fun getStudentCoursePayment(studentId: String, courseId: String) = educationDao.getStudentCoursePayments(studentId, courseId)

    override suspend fun addMentor(mentor: MentorEntity) = educationDao.addMentor(mentor)

    override fun getCoursesStatistics() = educationDao.getStatistics()
}