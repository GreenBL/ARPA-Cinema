package pwm.ar.arpacinema.tickets

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentTicketsBinding
import pwm.ar.arpacinema.dev.TicketItem

class TicketsFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    // TEMP DATA SET
    private val tickets = listOf(TicketItem("Ciao", "06/04/2000", "16:30", "nan"),
        TicketItem("Deadpool & Wolverine", "06/04/2000", "16:30", "nan"),
        TicketItem("Il Signore Degli Anelli: Il Ritorno Del Re", "06/04/2000", "16:30", "nan"),
        TicketItem("Star Wars: The Force Awakens", "06/04/2000", "16:30", "nan"),
        TicketItem("Star Wars: 2", "06/04/2000", "16:30", "nan"),
        TicketItem("Star Wars: The Force Awakens 4", "06/04/2000", "16:30", "nan"),
        TicketItem("Star Wars: The Force Awakens 6", "06/04/2000", "16:30", "nan"))



    private var _binding : FragmentTicketsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: TicketsViewModel by viewModels()

    companion object {
        fun newInstance() = TicketsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // we must ensure this otherwise it may look jarring when popping from ticket view
        val navBar = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE



        val adapter = TicketAdapter(tickets) { ticket, image ->

            // HANDLE THE ITEM BEING CLICKED
            ViewCompat.setTransitionName(image, "shared_poster_${ticket.title}")
            val action = TicketsFragmentDirections.actionGlobalViewTicketFragment("shared_poster_${ticket.title}")
            //val destiny = R.id.viewTicketFragment
            val extras = FragmentNavigatorExtras(
                image to "shared_poster_${ticket.title}"
            )
            navController.navigate(action, extras)
            Toast.makeText(requireContext(), "Clicked: ${image.transitionName}", Toast.LENGTH_SHORT).show()
        }

        //val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.ticketsRV.apply {
            this.adapter = adapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            //addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}