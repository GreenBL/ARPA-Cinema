package pwm.ar.arpacinema.auth

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.MainActivity
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentAuthBinding
import pwm.ar.arpacinema.util.TextValidator

class AuthFragment() : Fragment() {

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


        val navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationBar.visibility = View.GONE
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

        val signUpButton = binding.signupFormBtn
        val emailFieldLayout = binding.cardContentLogin.emailFieldLayout
        val passwordFieldLayout = binding.cardContentLogin.pwdFieldLayout

        val emailField = binding.cardContentLogin.emailField
        val passwordField = binding.cardContentLogin.passwordField

        emailField.addTextChangedListener(TextValidator(emailFieldLayout, TextValidator.Companion::isValidEmail))
        passwordField.addTextChangedListener(TextValidator(passwordFieldLayout, TextValidator.Companion::isValidPassword))

        signUpButton.setOnClickListener {

            val sharedElementView = binding.cardContentLogin.root
            val nav = findNavController()

            val extras = FragmentNavigatorExtras(sharedElementView to "shared_element_container")
            nav.navigate(R.id.signupFragment, null, null, extras)
        }

        binding.topBarInclude.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}