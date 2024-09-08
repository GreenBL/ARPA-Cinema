package pwm.ar.arpacinema.auth


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import pwm.ar.arpacinema.repository.DTO.Stat.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentAuthBinding
import pwm.ar.arpacinema.util.TextValidator

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel

        // TODO DISAPPEAR TOOLBAR AND NAVBAR
        //val appBarNav = requireActivity().supportFragmentManager.findFragmentById(R.id.appbarContainer)
        //val navController = appBarNav?.findNavController()
        //val toolbar = requireActivity().actionBar?.customView
        // val closeButton = toolbar?.findViewById<Button>(R.id.close_button)

        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = true
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }


//        val navigationBar =
//            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        navigationBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val scrollView = binding.nestedScrollView


        val loadingBar = binding.cardContentLogin.loadingBar
        val loginButton = binding.cardContentLogin.signinBtn
        val recoverPwd = binding.cardContentLogin.passwordResetBtn
        val signUpButton = binding.cardContentLogin.signUpBtn
        val emailFieldLayout = binding.cardContentLogin.emailFieldLayout
        val passwordFieldLayout = binding.cardContentLogin.pwdFieldLayout
        val emailField = binding.cardContentLogin.emailField
        val passwordField = binding.cardContentLogin.passwordField

        // clears errors on text change

        emailField.addTextChangedListener(TextValidator(emailFieldLayout, TextValidator.Companion::silent))
        passwordField.addTextChangedListener(TextValidator(passwordFieldLayout, TextValidator.Companion::silent))

        signUpButton.setOnClickListener {
            // force ime close altrimenti breaks
            val windowInsetsController = view.windowInsetsController
            windowInsetsController?.hide(WindowInsets.Type.ime())

            val sharedElementView = binding.cardContentLogin.signUpBtn
            val nav = findNavController()

            val extras = FragmentNavigatorExtras(sharedElementView to "shared_element_container")
            nav.navigate(R.id.signupFragment, null, null, extras)
        }

        binding.topBarInclude.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topBarInclude.viewTitle.text = "Accedi"

//        view.setOnApplyWindowInsetsListener { v, insets ->
//            val imeVisible = insets.isVisible(WindowInsets.Type.ime())
//
//            if (imeVisible) {
//                scrollView.post {
//                    scrollView.fullScroll(View.FOCUS_DOWN)
//                }
//                image.visibility = View.GONE
//            } else {
//                image.visibility = View.VISIBLE
//            }
//            v.onApplyWindowInsets(insets)
//        }

        // Password recovery action
        recoverPwd.setOnClickListener {
            findNavController().navigate(R.id.action_global_passwordRecoveryFragment)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                SUCCESS -> {}
                USER_NOT_REGISTERED -> {
                    emailFieldLayout.error = "Utente non registrato"
                    emailFieldLayout.isErrorEnabled = true
                }
                PSW_ERROR -> {
                    passwordFieldLayout.error = "Password errata"
                    passwordFieldLayout.isErrorEnabled = true
                }
                else -> {
                    Snackbar.make(view, "Errore sconosciuto", Snackbar.LENGTH_SHORT).show()
                }

            }

        }

        // Login action
        loginButton.setOnClickListener {
            // are the text fields empty?
            if (emailField.text.isNullOrBlank()) {
                emailFieldLayout.error = "Campo obbligatorio"
                emailFieldLayout.isErrorEnabled = true
            }
            if (passwordField.text.isNullOrBlank()) {
                passwordFieldLayout.error = "Campo obbligatorio"
                passwordFieldLayout.isErrorEnabled = true
            }

            if (emailFieldLayout.isErrorEnabled || passwordFieldLayout.isErrorEnabled) {
                return@setOnClickListener
            }

            // todo validate


            // close the ime
            requireActivity().currentFocus?.clearFocus()
            val windowInsetsController = view.windowInsetsController
            windowInsetsController?.hide(WindowInsets.Type.ime())

            lifecycleScope.launch(Dispatchers.Main) {
                loadingBar.visibility = View.VISIBLE
                loginButton.apply {
                    text = "Accesso in corso..."
                    isClickable = false
                }
                val result = withContext(Dispatchers.IO) {
                    viewModel.execLogin()
                }
                if(result != null) {
                    Session.storeUser(requireContext(), result)

                }
                loadingBar.visibility = View.GONE
                loginButton.apply {
                    text = "Accedi"
                    isClickable = true
                }

                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

