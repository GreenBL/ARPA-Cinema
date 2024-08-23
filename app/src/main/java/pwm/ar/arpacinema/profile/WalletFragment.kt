package pwm.ar.arpacinema.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentWalletBinding
import retrofit2.HttpException
import java.io.IOException

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

        // Access the back button from the included layout
        val appBarLayout = binding.root.findViewById<View>(R.id.appbar) as? com.google.android.material.appbar.AppBarLayout
        val backButton = appBarLayout?.findViewById<MaterialButton>(R.id.navBack)

        // Load user data and balance
        lifecycleScope.launch {
            val userId = Session.getUserId(requireContext())
            val user = Session.user
            if (userId != null && user != null) {
                try {
                    // Display user name and surname from the session
                    binding.cardView.findViewById<TextView>(R.id.name).text = "${user.name} ${user.surname}"
                    loadBalance(userId)
                } catch (e: Exception) {
                    Snackbar.make(requireView(), "Errore durante il caricamento", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                binding.cardView.findViewById<TextView>(R.id.name).text = "Titolare Carta"
                binding.dynamicCurrency.text = "0.00€"
            }
        }

        increaseLayout.editText?.addTextChangedListener {
            fadeIn(confirmButton)
        }

        confirmButton.setOnClickListener {
            lifecycleScope.launch {
                val userId = Session.getUserId(requireContext())
                if (userId != null) {
                    val amount = increaseLayout.editText?.text.toString().toFloatOrNull()
                    if (amount != null) {
                        try {
                            updateBalance(userId, amount)
                        } catch (e: Exception) {
                            Snackbar.make(requireView(), "Accedi per aggiornare il saldo", Snackbar.LENGTH_SHORT).show()
                        }
                    } else {
                        Snackbar.make(requireView(), "Importo non valido", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(requireView(), "Accedi per ricaricare", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        backButton?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private suspend fun loadBalance(userId: String) {
        try {
            val balance = viewModel.getBalance(userId)
            binding.dynamicCurrency.text = "${balance}€"
        } catch (e: Exception) {
            Snackbar.make(requireView(), "Errore durante il caricamento del saldo", Snackbar.LENGTH_SHORT).show()
        }
    }

    private suspend fun updateBalance(userId: String, amount: Float) {
        try {
            val response = viewModel.updateBalance(userId, amount)
            if (response.isSuccessful) {
                Snackbar.make(requireView(), "Saldo aggiornato", Snackbar.LENGTH_SHORT).show()
                loadBalance(userId) // Refresh balance after update
            } else {
                Snackbar.make(requireView(), "Errore durante l'aggiornamento del saldo", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: HttpException) {
            Snackbar.make(requireView(), "Errore di rete: ${e.message}", Snackbar.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Snackbar.make(requireView(), "Errore di comunicazione: ${e.message}", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun fadeIn(button: Button) {
        button.visibility = View.VISIBLE
        button.alpha = 1f
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
