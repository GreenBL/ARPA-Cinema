package pwm.ar.arpacinema.booking


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pwm.ar.arpacinema.databinding.ModalCheckoutBinding


/**
 *  Modale di registrazione pagamento implementato con BottomSheetDialogFragment
 */
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}