package air.com.db_coursework.ui.statistics.adapter

import air.com.db_coursework.R
import air.com.db_coursework.data.model.result.CourseWithDebtAndIncome
import air.com.db_coursework.databinding.ItemCourseStatisticsBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StatisticsAdapter : ListAdapter<CourseWithDebtAndIncome, StatisticsAdapter.StatisticsViewHolder>(diff) {
    inner class StatisticsViewHolder(private val binding: ItemCourseStatisticsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseWithDebtAndIncome) {
            binding.textCourseName.text = item.courseName
            binding.textEnrollments.text = itemView.context.getString(R.string.income, item.totalPayments.toString())
            binding.textDebt.text = itemView.context.getString(R.string.debt, item.debt.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        return StatisticsViewHolder(
            ItemCourseStatisticsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<CourseWithDebtAndIncome>() {
            override fun areItemsTheSame(
                oldItem: CourseWithDebtAndIncome,
                newItem: CourseWithDebtAndIncome
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CourseWithDebtAndIncome,
                newItem: CourseWithDebtAndIncome
            ): Boolean {
                return oldItem.courseId == newItem.courseId && oldItem.courseName == newItem.courseName
                        && oldItem.totalPayments == newItem.totalPayments && oldItem.debt == newItem.debt
            }

        }
    }
}