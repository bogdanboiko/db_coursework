package air.com.db_coursework.ui.home.dialog

import air.com.db_coursework.data.model.CourseEntity
import air.com.db_coursework.data.model.MentorEntity
import air.com.db_coursework.databinding.DialogCourseCreationBinding
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
import java.util.UUID

class CourseCreationDialog : DialogFragment() {
    private lateinit var binding: DialogCourseCreationBinding
    private val viewModel: HomeSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCourseCreationBinding.inflate(inflater, container, false)
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
                val name = courseNameEdit.text
                val description = courseDescriptionEdit.text
                val price = coursePriceEdit.text
                val mentorName = mentorNameEdit.text
                val mentorContact = mentorContactEdit.text
                val mentorExperience = mentorExperienceEdit.text

                if (!name.isNullOrEmpty() && !description.isNullOrEmpty() && !price.isNullOrEmpty()) {
                    val courseId = UUID.randomUUID().toString()
                    viewModel.addCourse(
                        CourseEntity(
                            courseId,
                            name.toString(),
                            description.toString(),
                            price.toString().toDouble()
                        ),
                        MentorEntity(
                            UUID.randomUUID().toString(),
                            courseId,
                            mentorName.toString(),
                            mentorContact.toString(),
                            mentorExperience.toString().toInt()
                        )
                    )
                    dismiss()
                } else {
                    Toast.makeText(context, "One of fields is empty", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "course_creation_dialog"
    }
}