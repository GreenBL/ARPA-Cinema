package pwm.ar.arpacinema.tickets

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentViewTicketBinding

class ViewTicketFragment : Fragment() {

    private var _binding: FragmentViewTicketBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ViewTicketFragment()
    }

    private val viewModel: ViewTicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 400L
            isElevationShadowEnabled = true
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            scrimColor = Color.BLACK
        }

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transitionName = arguments?.getString("transition")
        Log.d("Transizione passata?", "Ricevuata: $transitionName")

        val whole = binding.root
        ViewCompat.setTransitionName(whole, transitionName)

        // hide the bottom navigation bar
        val navBar = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.GONE



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // show the nav bar again
        val navBar = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

    }
}