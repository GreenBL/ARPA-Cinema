package pwm.ar.arpacinema.auth

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    val appBarNav : NavController by lazy {
        requireActivity().supportFragmentManager.findFragmentById(R.id.appbarContainer)?.findNavController()!!
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel

        // TODO DISAPPEAR TOOLBAR AND NAVBAR
        //val appBarNav = requireActivity().supportFragmentManager.findFragmentById(R.id.appbarContainer)
        //val navController = appBarNav?.findNavController()
        appBarNav.navigate(R.id.closeAppBar)

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

        signUpButton.setOnClickListener {
            val sharedElementView = requireActivity().findViewById<View>(R.id.signinBtn)
            val cardHost = binding.authCardContainer
            val cardNavController = cardHost.findNavController()
            binding.signupFormBtn.visibility = View.GONE
            val extras = FragmentNavigatorExtras(sharedElementView to "shared_element_container")
            cardNavController.navigate(R.id.login_to_signup, null, null, extras)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        appBarNav.navigate(R.id.homeAppBar)
    }


}