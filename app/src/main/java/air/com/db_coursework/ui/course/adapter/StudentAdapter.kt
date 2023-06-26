package air.com.db_coursework.ui.course.adapter

import air.com.db_coursework.R
import air.com.db_coursework.data.model.result.CourseWithParticipantQuantity
import air.com.db_coursework.data.model.result.StudentPreviewWithDebt
import air.com.db_coursework.databinding.ItemCoursePreviewBinding
import air.com.db_coursework.databinding.ItemStudentPreviewBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val onItemClick: (studentId: String) -> Unit) : ListAdapter<StudentPreviewWithDebt, StudentAdapter.StudentViewHolder>(diff) {
    inner class StudentViewHolder(private val binding: ItemStudentPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: StudentPreviewWithDebt) {
            itemView.setOnClickListener {
                onItemClick(model.id)
            }

            binding.studentName.text = model.firstName + " " + model.lastName
            binding.totalPaid.text = itemView.context.getString(
                R.string.price, model.totalPayments.toString()
            )
            binding.debt.text = itemView.context.getString(R.string.price, model.debt.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            ItemStudentPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<StudentPreviewWithDebt>() {
            override fun areItemsTheSame(
                oldItem: StudentPreviewWithDebt,
                newItem: StudentPreviewWithDebt
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StudentPreviewWithDebt,
                newItem: StudentPreviewWithDebt
            ): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.firstName == newItem.firstName &&
                        oldItem.lastName == newItem.lastName &&
                        oldItem.totalPayments == newItem.totalPayments &&
                        oldItem.debt == newItem.debt
            }
        }
    }
}