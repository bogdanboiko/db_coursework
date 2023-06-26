package air.com.db_coursework.ui.student

import air.com.db_coursework.R
import air.com.db_coursework.databinding.FragmentStudentBinding
import air.com.db_coursework.ui.student.adapter.PaymentAdapter
import air.com.db_coursework.ui.student.dialog.PaymentCreationDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentFragment : Fragment() {
    private lateinit var binding: FragmentStudentBinding
    private val viewModel: StudentSharedViewModel by viewModels()
    private lateinit var paymentAdapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configStudentData()
        configPaymentAddButton()
        configRecyclerView()
    }

    private fun configRecyclerView() {
        paymentAdapter = PaymentAdapter {  }
        binding.recyclerViewPayments.adapter = paymentAdapter
        binding.recyclerViewPayments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getStudentPayments().collect {
                paymentAdapter.submitList(it)
            }
        }
    }

    private fun configPaymentAddButton() {
        binding.addPaymentButton.setOnClickListener {
            PaymentCreationDialog().show(childFragmentManager, PaymentCreationDialog.TAG)
        }
    }

    private fun configStudentData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.student.collect {
                if (it != null) {
                    binding.textViewStudentName.text = it.firstName + it.lastName
                    binding.textViewEmail.text = it.email
                    binding.textViewEnrollmentDate.text = it.enrollmentDate
                    binding.debt.text = getString(R.string.price, it.debt.toString())
                }
            }
        }
    }
}