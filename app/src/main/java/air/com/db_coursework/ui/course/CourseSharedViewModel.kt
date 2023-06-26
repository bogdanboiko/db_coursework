package air.com.db_coursework.ui.course

import air.com.db_coursework.data.datasource.EducationDatasource
import air.com.db_coursework.data.model.CourseEntity
import air.com.db_coursework.data.model.EnrollmentEntity
import air.com.db_coursework.data.model.StudentEntity
import air.com.db_coursework.data.model.result.CourseWithMentor
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CourseSharedViewModel @Inject constructor(
    private val localDatasource: EducationDatasource.Local,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val courseId: String = savedStateHandle["courseId"] ?: ""

    private val _course = MutableStateFlow<CourseWithMentor?>(null)
    val course = _course.asStateFlow()

    init {
        getCourseData()
    }


    private fun getCourseData() {
        viewModelScope.launch {
            localDatasource.getCourseDataWithMentor(courseId).collect {
                _course.emit(it)
            }
        }
    }

    fun getStudentsPreviewWithDebt() =
        localDatasource.getStudentsPreviewWithDebt(courseId)

    fun enrollStudent(firstName: String, lastName: String, email: String) =
        viewModelScope.launch {
            val studentId = UUID.randomUUID().toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.format(Calendar.getInstance().time)

            localDatasource.createStudent(StudentEntity(studentId, firstName, lastName, email))
            localDatasource.createEnrollment(
                EnrollmentEntity(
                    UUID.randomUUID().toString(),
                    studentId,
                    courseId,
                    date
                )
            )
        }
}