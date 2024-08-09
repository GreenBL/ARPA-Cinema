package pwm.ar.arpacinema.tickets

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.R

class ViewTicketFragment : Fragment() {

    companion object {
        fun newInstance() = ViewTicketFragment()
    }

    private val viewModel: ViewTicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_view_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hide the bottom navigation bar
        val navBar = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // show the nav bar again
        val navBar = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

    }
}