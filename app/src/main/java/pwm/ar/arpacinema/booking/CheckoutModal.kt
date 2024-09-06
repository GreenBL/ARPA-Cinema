package pwm.ar.arpacinema.booking


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.ModalCheckoutBinding
import pwm.ar.arpacinema.dev.Selection
import java.util.Locale


class CheckoutModal : BottomSheetDialogFragment() {

    private var _binding : ModalCheckoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalCheckoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "checkout_modal"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        val selections = viewModel.selectionObjects.value!!

        val recycler = binding.recyclerView

        val total = binding.total

        lifecycleScope.launch {
            val totale = selections.sumOf { it.price.toDouble() }
            val string = String.format(Locale.ITALY,"%.2f", totale)
            total.text = "${string}€"
        }



        //recycler.adapter = CheckoutAdapter(viewModel.selections)

        val adapter = CheckoutAdapter(selections)
        val decoration = MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL).apply {
            isLastItemDecorated = true
            dividerThickness = 64
            dividerColor = requireContext().getColor(android.R.color.transparent)
        }
        recycler.adapter = adapter
        recycler.addItemDecoration(decoration)
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}