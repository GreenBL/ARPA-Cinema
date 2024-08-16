package pwm.ar.arpacinema.auth

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {

    companion object {
        fun newInstance() = PasswordRecoveryFragment()
    }

    private var _binding : FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRecoveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailField = binding.emailReccoverLayout
        val sendButton = binding.sendEmail
        val answerField = binding.answerLayout
        val passwordField = binding.newPasswordLayout
        val confirmationButton = binding.confirmBtn
        val title = binding.domanda
        val text = binding.domandaText

        sendButton.setOnClickListener {
            title.alpha = 0f
            title.visibility = View.VISIBLE
            title.animate()
                .alpha(1f)
                .setDuration(500L)
                .start()

            // Fai apparire il testo della domanda con una animazione fade-in
            text.alpha = 0f
            text.visibility = View.VISIBLE
            text.animate()
                .alpha(1f)
                .setDuration(500L)
                .start()

            // Nascondi il campo dell'email con un effetto fade-out
            emailField.animate()
                .alpha(0f)
                .setDuration(500L)
                .withEndAction {
                    emailField.visibility = View.GONE
                }
                .start()

            // Mostra il campo della risposta con un effetto fade-in
            answerField.alpha = 0f
            answerField.visibility = View.VISIBLE
            answerField.animate()
                .alpha(1f)
                .setDuration(500L)
                .start()

            confirmationButton.alpha = 0f
            confirmationButton.visibility = View.VISIBLE
            confirmationButton.animate()
                .alpha(1f)
                .setDuration(500L)
                .start()

            passwordField.alpha = 0f
            passwordField.visibility = View.VISIBLE
            passwordField.animate()
                .alpha(1f)
                .setDuration(500L)
                .start()

            sendButton.animate()
                .alpha(0f)
                .setDuration(500L)
                .withEndAction {
                    sendButton.visibility = View.GONE
                }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}