package air.com.db_coursework.ui.student

import air.com.db_coursework.data.datasource.EducationDatasource
import air.com.db_coursework.data.model.PaymentEntity
import air.com.db_coursework.data.model.result.StudentDetails
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StudentSharedViewModel @Inject constructor(
    private val local: EducationDatasource.Local,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val studentId: String = savedStateHandle["studentId"] ?: ""
    val courseId: String = savedStateHandle["courseId"] ?: ""

    private val _student = MutableStateFlow<StudentDetails?>(null)
    val student = _student.asStateFlow()

    init {
        getStudentDetails()
    }

    private fun getStudentDetails() {
        viewModelScope.launch {
            local.getStudentDetails(studentId).collect {
                _student.emit(it)
            }
        }
    }

    fun getStudentPayments(): Flow<List<PaymentEntity>> {
        return local.getStudentCoursePayment(studentId, courseId)
    }

    fun createPayment(amount: String) {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.format(Calendar.getInstance().time)

            local.addPayment(PaymentEntity(UUID.randomUUID().toString(), studentId, courseId, amount, date.toString()))
        }
    }
}