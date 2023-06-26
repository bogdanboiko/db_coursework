package air.com.db_coursework.ui.student.dialog

import air.com.db_coursework.databinding.DialogPaymentCreationBinding
import air.com.db_coursework.databinding.DialogStudentEnrollmentBinding
import air.com.db_coursework.ui.course.CourseSharedViewModel
import air.com.db_coursework.ui.student.StudentSharedViewModel
import air.com.db_coursework.utils.extension.setWidthPercent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels

class PaymentCreationDialog : DialogFragment() {
    private lateinit var binding: DialogPaymentCreationBinding
    private val viewModel: StudentSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPaymentCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setWidthPercent(90)

        configButtons()
    }

    private fun configButtons() {
        with(binding) {
            cancelButton.setOnClickListener {
                dismiss()
            }

            confirmButton.setOnClickListener {
                val amount = paymentAmountEdit.text

                if (!amount.isNullOrEmpty()) {
                    viewModel.createPayment(amount.toString())
                    dismiss()
                } else {
                    Toast.makeText(context, "One of fields is empty", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "payment_dialog"
    }
}