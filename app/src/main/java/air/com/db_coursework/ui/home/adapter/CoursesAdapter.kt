package air.com.db_coursework.ui.home.adapter

import air.com.db_coursework.R
import air.com.db_coursework.data.model.result.CourseWithParticipantQuantity
import air.com.db_coursework.databinding.ItemCoursePreviewBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CoursesAdapter(private val onItemClick: (courseId: String) -> Unit) :
    ListAdapter<CourseWithParticipantQuantity, CoursesAdapter.CategoryViewHolder>(diff) {
    inner class CategoryViewHolder(private val binding: ItemCoursePreviewBinding) :
        ViewHolder(binding.root) {
        fun bind(model: CourseWithParticipantQuantity) {
            itemView.setOnClickListener {
                onItemClick(model.id)
            }

            binding.courseName.text = model.name
            binding.enrollmentQuantity.text = itemView.context.getString(
                R.string.participants, model.enrollments.toInt().toString()
            )
            binding.coursePrice.text = itemView.context.getString(R.string.price, model.price.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCoursePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<CourseWithParticipantQuantity>() {
            override fun areItemsTheSame(
                oldItem: CourseWithParticipantQuantity,
                newItem: CourseWithParticipantQuantity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CourseWithParticipantQuantity,
                newItem: CourseWithParticipantQuantity
            ): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.price == newItem.price &&
                        oldItem.enrollments == newItem.enrollments
            }

        }
    }
}