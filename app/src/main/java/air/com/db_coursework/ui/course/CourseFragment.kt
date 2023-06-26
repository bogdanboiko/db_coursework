package air.com.db_coursework.ui.course

import air.com.db_coursework.R
import air.com.db_coursework.databinding.FragmentCourseBinding
import air.com.db_coursework.ui.course.adapter.StudentAdapter
import air.com.db_coursework.ui.course.dialog.StudentEnrollmentDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CourseFragment : Fragment() {
    private lateinit var binding: FragmentCourseBinding
    private val viewModel: CourseSharedViewModel by viewModels()
    private lateinit var studentsAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configView()
        configRecycler()
    }

    private fun configRecycler() {
        studentsAdapter = StudentAdapter {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToStudentFragment(viewModel.courseId, it))
        }

        binding.recyclerView.adapter = studentsAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getStudentsPreviewWithDebt().collect {
                studentsAdapter.submitList(it)
            }
        }
    }

    private fun configView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.course.collect{
                if (it != null) {
                    binding.courseName.text = it.name
                    binding.courseDescription.text = it.description
                    binding.coursePrice.text = getString(R.string.price, it.price.toString())
                    binding.mentorContacts.text = getString(R.string.mentor_contacts, it.contact)
                    binding.mentorName.text = getString(R.string.mentor_name, it.name)
                    binding.mentorExperience.text = getString(R.string.mentor_experience, it.experienceYears.toString())

                }
            }
        }

        binding.addStudentButton.setOnClickListener {
            StudentEnrollmentDialog().show(childFragmentManager, StudentEnrollmentDialog.TAG)
        }
    }
}