package air.com.db_coursework.ui.course.dialog

import air.com.db_coursework.data.model.CourseEntity
import air.com.db_coursework.databinding.DialogCourseCreationBinding
import air.com.db_coursework.databinding.DialogStudentEnrollmentBinding
import air.com.db_coursework.ui.course.CourseSharedViewModel
import air.com.db_coursework.ui.home.HomeSharedViewModel
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
import java.util.*

class StudentEnrollmentDialog : DialogFragment() {
    private lateinit var binding: DialogStudentEnrollmentBinding
    private val viewModel: CourseSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogStudentEnrollmentBinding.inflate(inflater, container, false)
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

            submitButton.setOnClickListener {
                val firstName = firstNameEdit.text
                val lastName = lastNameEdit.text
                val email = emailEdit.text

                if (!firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() && !email.isNullOrEmpty()) {
                    viewModel.enrollStudent(firstName.toString(), lastName.toString(), email.toString())
                    dismiss()
                } else {
                    Toast.makeText(context, "One of fields is empty", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "student_enrollment_dialog"
    }
}