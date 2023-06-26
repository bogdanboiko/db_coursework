package air.com.db_coursework.ui.home

import air.com.db_coursework.databinding.FragmentHomeBinding
import air.com.db_coursework.ui.home.adapter.CoursesAdapter
import air.com.db_coursework.ui.home.dialog.CourseCreationDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeSharedViewModel by viewModels()
    private lateinit var courseAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configView()
        configRecycler()
    }

    private fun configRecycler() {
        courseAdapter = CoursesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCourseFragment(it))
        }
        binding.courseList.adapter = courseAdapter
        binding.courseList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collect {
                courseAdapter.submitList(it)
            }
        }
    }

    private fun configView() {
        binding.addCourseButton.setOnClickListener {
            CourseCreationDialog().show(childFragmentManager, CourseCreationDialog.TAG)
        }

        val from = binding.fromEdit.text
        val to = binding.toEdit.text

        binding.filterButton.setOnClickListener {
            if (!from.isNullOrEmpty() && !to.isNullOrEmpty()) {
                viewModel.fetchFilteredCourses(
                    binding.fromEdit.text.toString().toDouble(),
                    binding.toEdit.text.toString().toDouble()
                )
            } else if (from.isNullOrEmpty() && to.isNullOrEmpty()) {
                viewModel.fetchCoursesInfo()
            } else if (from.isNullOrEmpty()) {
                viewModel.fetchFilteredTo(
                    binding.toEdit.text.toString().toDouble()
                )
            } else if (to.isNullOrEmpty()) {
                viewModel.fetchFilteredFrom(
                    binding.fromEdit.text.toString().toDouble()
                )
            }
        }

        binding.statisticsButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStatisticsFragment())
        }
    }
}