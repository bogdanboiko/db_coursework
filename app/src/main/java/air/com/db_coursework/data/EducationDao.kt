package air.com.db_coursework.data

import air.com.db_coursework.data.model.*
import air.com.db_coursework.data.model.result.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EducationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addCourse(course: CourseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addMentor(course: MentorEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addStudent(student: StudentEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addEnrollment(course: EnrollmentEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addPayment(course: PaymentEntity)

    @Query("SELECT Course.id, Course.name, Course.price, COUNT(Enrollment.studentId) AS enrollments FROM Course LEFT JOIN Enrollment ON Course.id = Enrollment.courseId WHERE price BETWEEN :minPrice AND :maxPrice GROUP BY Course.name")
    abstract fun getCoursesByPriceRange(
        minPrice: Double,
        maxPrice: Double
    ): Flow<List<CourseWithParticipantQuantity>>

    @Query("SELECT Course.id, Course.name, Course.price, COUNT(Enrollment.studentId) AS enrollments FROM Course LEFT JOIN Enrollment ON Course.id = Enrollment.courseId WHERE price >= :minPrice GROUP BY Course.name")
    abstract fun getCoursesByPriceFrom(minPrice: Double): Flow<List<CourseWithParticipantQuantity>>

    @Query("SELECT Course.id, Course.name, Course.price, COUNT(Enrollment.studentId) AS enrollments FROM Course LEFT JOIN Enrollment ON Course.id = Enrollment.courseId WHERE price <= :maxPrice GROUP BY Course.name")
    abstract fun getCoursesByPriceTo(maxPrice: Double): Flow<List<CourseWithParticipantQuantity>>

    @Query("SELECT Course.id AS courseId, Course.name, Course.description, Course.price," +
            " Mentor.name as mentorName, Mentor.contact, Mentor.experienceYears" +
            "  FROM Mentor INNER JOIN Course ON Mentor.courseId = Course.id WHERE courseId = :courseId")
    abstract fun getCourseDataWithMentor(courseId: String): Flow<CourseWithMentor>

    @Query(
        "SELECT Student.id, Student.firstName, Student.lastName, COALESCE(SUM(Payment.amount), 0) AS totalPayments, (Course.price - COALESCE(SUM(Payment.amount), 0)) AS debt " +
                "FROM Student " +
                "INNER JOIN Enrollment ON Student.id = Enrollment.studentId " +
                "INNER JOIN Course ON Enrollment.courseId = Course.id " +
                "LEFT JOIN Payment ON Student.id = Payment.studentId " +
                "WHERE Course.id = :courseId " +
                "GROUP BY Student.id, Course.id"
    )
    abstract fun getStudentsPreviewsWithDebt(courseId: String): Flow<List<StudentPreviewWithDebt>>

    @Query("SELECT courseId, courseName, SUM(totalPayments) as totalPayments, SUM(debt) as debt " +
            "FROM (SELECT Course.id AS courseId, Course.name as courseName, COALESCE(SUM(Payment.amount), 0) AS totalPayments, (Course.price - COALESCE(SUM(Payment.amount), 0)) AS debt FROM Payment INNER JOIN Course ON Payment.courseId = Course.id GROUP BY Payment.studentId) " +
            " GROUP BY courseId ORDER BY debt DESC")
    abstract fun getStatistics(): Flow<List<CourseWithDebtAndIncome>>

    @Query(
        "SELECT Student.id, Student.firstName, Student.lastName, Student.email, Enrollment.enrollmentDate, (Course.price - COALESCE(SUM(Payment.amount), 0)) AS debt " +
                "FROM Student " +
                "INNER JOIN Enrollment ON Student.id = Enrollment.studentId " +
                "INNER JOIN Course ON Enrollment.courseId = Course.id " +
                "LEFT JOIN Payment ON Student.id = Payment.studentId " +
                "WHERE Student.id = :studentId " +
                "GROUP BY Student.id, Course.id"
    )
    abstract fun getStudentDetails(studentId: String): Flow<StudentDetails>

    @Query("SELECT Course.id, Course.name, Course.price, COUNT(Enrollment.studentId) AS enrollments FROM Course LEFT JOIN Enrollment ON Course.id = Enrollment.courseId GROUP BY Course.name")
    abstract fun getCoursesWithEnrollmentCount(): Flow<List<CourseWithParticipantQuantity>>

    @Query("SELECT * FROM Payment WHERE studentId = :studentId AND courseId = :courseId")
    abstract fun getStudentCoursePayments(
        studentId: String,
        courseId: String
    ): Flow<List<PaymentEntity>>
}