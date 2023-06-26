package air.com.db_coursework.ui.home

import air.com.db_coursework.data.datasource.EducationDatasource
import air.com.db_coursework.data.model.CourseEntity
import air.com.db_coursework.data.model.MentorEntity
import air.com.db_coursework.data.model.result.CourseWithParticipantQuantity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(private val localDatasource: EducationDatasource.Local) : ViewModel() {
    private val _courses = MutableStateFlow<List<CourseWithParticipantQuantity>>(emptyList())
    val courses = _courses.asStateFlow()

    private var collectorJob: Job? = null

    init {
        fetchCoursesInfo()
    }

    fun addCourse(course: CourseEntity, mentor: MentorEntity) = viewModelScope.launch {
            localDatasource.createCourse(course)
            localDatasource.addMentor(mentor)
        }

    fun fetchCoursesInfo() {
        collectorJob?.cancel()

        collectorJob = viewModelScope.launch {
            localDatasource.getCoursesWithEnrollmentCount().cancellable().collect { courses ->
                _courses.emit(courses)
            }
        }
    }

    fun fetchFilteredCourses(from: Double, to: Double) {
        collectorJob?.cancel()

        collectorJob = viewModelScope.launch(Dispatchers.IO) {
            localDatasource.getCoursesWithEnrollmentCountFilteredByPrice(from, to).cancellable().collect { courses ->
                _courses.emit(courses)
            }
        }
    }

    fun fetchFilteredFrom(from: Double) {
        collectorJob?.cancel()

        collectorJob = viewModelScope.launch(Dispatchers.IO) {
            localDatasource.getCoursesByPriceFrom(from).cancellable().collect { courses ->
                _courses.emit(courses)
            }
        }
    }

    fun fetchFilteredTo(to: Double) {
        collectorJob?.cancel()

        collectorJob = viewModelScope.launch(Dispatchers.IO) {
            localDatasource.getCoursesByPriceTo(to).cancellable().collect { courses ->
                _courses.emit(courses)
            }
        }
    }
}