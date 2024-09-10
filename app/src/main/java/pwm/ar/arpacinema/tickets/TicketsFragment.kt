package pwm.ar.arpacinema.tickets

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentTicketsBinding
import pwm.ar.arpacinema.dev.TicketItem
import pwm.ar.arpacinema.parcels.TicketDetailsParcel

class TicketsFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    // TEMP DATA SET
//    private val tickets = listOf(TicketItem("Ciao", "06/04/2000", "16:30", "nan"),
//        TicketItem("Deadpool & Wolverine", "06/04/2000", "16:30", "nan"),
//        TicketItem("Il Signore Degli Anelli: Il Ritorno Del Re", "06/04/2000", "16:30", "nan"),
//        TicketItem("Star Wars: The Force Awakens", "06/04/2000", "16:30", "nan"),
//        TicketItem("Star Wars: 2", "06/04/2000", "16:30", "nan"),
//        TicketItem("Star Wars: The Force Awakens 4", "06/04/2000", "16:30", "nan"),
//        TicketItem("Star Wars: The Force Awakens 6", "06/04/2000", "16:30", "nan"))



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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner




        // we must ensure this otherwise it may look jarring when popping from ticket view
        //
        //navBar.visibility = View.VISIBLE



        val adapter = TicketAdapter(mutableListOf()) { ticket ->

            // HANDLE THE ITEM BEING CLICKED
            //ViewCompat.setTransitionName(image, "shared_poster_${ticket.title}")
            // setup the parcel
            val ticketParcel = TicketDetailsParcel(
                ticket.ticketId,
                ticket.filmTitle,
                ticket.screeningDate,
                ticket.screeningTime,
                ticket.screeningTheater,
                ticket.seatNumber,
            )
            val action = TicketsFragmentDirections.actionGlobalViewTicketFragment(ticketParcel)
            navController.navigate(action)
        }



        //val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.ticketsRV.apply {
            this.adapter = adapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            //addItemDecoration(dividerItemDecoration)
        }
        // add snap helper to horizontal recycler linear layout view
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.ticketsRV)


        val dots = binding.dotsy
        dots.attachToRecyclerView(binding.ticketsRV, snapHelper)
        adapter.registerAdapterDataObserver(dots.adapterDataObserver)

        viewModel.tickets.observe(viewLifecycleOwner) {
            Log.d("TicketsFragment", "Tickets UPDATE: $it")
            if (it == null) {
                return@observe
            } else {
                adapter.updateTickets(it)
                adapter.notifyDataSetChanged()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}