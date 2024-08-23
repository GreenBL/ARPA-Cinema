package pwm.ar.arpacinema.profile.wallet

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import pwm.ar.arpacinema.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmButton = binding.confirmButton
        val increaseLayout = binding.increaseLayout

        var initialized = false

        view.post {
            initialized = true
        }

        // Mostra il confirmButton quando l'utente inizia a digitare
        increaseLayout.editText?.addTextChangedListener {
            if (initialized) {
                fadeIn(confirmButton)
                initialized = false // avoid playing the animation again
            }
            if (it.isNullOrBlank()) {
                fadeOut(confirmButton)
                initialized = true
            }
        }

        confirmButton.setOnClickListener {
            fadeOut(confirmButton)
            Snackbar.make(requireView(), "Saldo caricato", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun fadeIn(button: Button) {
        button.visibility = View.VISIBLE
        button.alpha = 0f
        button.post {
            button.animate().alpha(1f).setDuration(350)
        }
    }

    private fun fadeOut(button: Button) {
        button.post {
            button.animate().alpha(0f).setDuration(350).withEndAction {
                button.visibility = View.GONE
            }.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
