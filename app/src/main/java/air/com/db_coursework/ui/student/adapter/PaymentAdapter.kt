package air.com.db_coursework.ui.student.adapter

import air.com.db_coursework.R
import air.com.db_coursework.data.model.PaymentEntity
import air.com.db_coursework.databinding.ItemPaymentBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PaymentAdapter(private val onItemClick: (studentId: String) -> Unit) : ListAdapter<PaymentEntity, PaymentAdapter.PaymentViewHolder>(diff) {
    inner class PaymentViewHolder(private val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PaymentEntity) {
            itemView.setOnClickListener {
                onItemClick(model.id)
            }

            binding.amountTextView.text = itemView.context.getString(R.string.price, model.amount)
            binding.dateTextView.text = model.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            ItemPaymentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<PaymentEntity>() {
            override fun areItemsTheSame(
                oldItem: PaymentEntity,
                newItem: PaymentEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PaymentEntity,
                newItem: PaymentEntity
            ): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.studentId == newItem.studentId &&
                        oldItem.courseId == newItem.courseId &&
                        oldItem.amount == newItem.amount &&
                        oldItem.date == newItem.date
            }
        }
    }
}