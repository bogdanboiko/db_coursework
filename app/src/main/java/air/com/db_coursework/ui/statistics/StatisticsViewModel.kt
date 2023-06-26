package air.com.db_coursework.ui.statistics

import air.com.db_coursework.data.datasource.EducationDatasource
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val local: EducationDatasource.Local) : ViewModel() {
    fun getStatistics() = local.getCoursesStatistics()
}