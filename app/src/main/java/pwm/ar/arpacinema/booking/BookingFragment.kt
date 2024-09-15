package pwm.ar.arpacinema.booking

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentBookingBinding
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.model.ScreeningTime
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.util.CustomSeatView
import pwm.ar.arpacinema.util.SeatInterpreter
import java.time.LocalDate
import java.time.LocalTime



class BookingFragment : Fragment() {

    private lateinit var seatBookView : CustomSeatView

    private var _binding : FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var seats = (
                    "/_AAA_AAA_" +
                    "/AAAA_AAAA" +
                    "/AAAA_AAAA" +
                    "/AAAA_AAAA" +
                    "/AAAA_AAAA" +
                    "/AAAA_AAAA" +
                    "/_AAA_AAA_"
            )

    private var titleArray = listOf(
        "/", "", "A1", "A2", "A3", "", "A4", "A5", "A6", "",
        "/", "B1", "B2", "B3", "B4", "", "B5", "B6", "B7", "B8",
        "/", "C1", "C2", "C3", "C4", "", "C5", "C6", "C7", "C8",
        "/", "D1", "D2", "D3", "D4", "", "D5", "D6", "D7", "D8",
        "/", "E1", "E2", "E3", "E4", "", "E5", "E6", "E7", "E8",
        "/", "F1", "F2", "F3", "F4", "", "F5", "F6", "F7", "F8",
        "/", "", "G1", "G2", "G3", "", "G4", "G5", "G6", "",
    )


    companion object {
        fun newInstance() = BookingFragment()
    }

    private val viewModel: BookingViewModel by activityViewModels()
    private val args: BookingFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.userId.postValue(Session.getUserId(requireContext()))
            viewModel.movieId.postValue(args.movie.id)
            viewModel.fetchUserLevelAndPoints()
        }
        val movie = args.movie
        lifecycleScope.launch {
            viewModel.movie.postValue(movie)
            viewModel.fetchDates(movie.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var dateAdapter : MovieDateAdapter
    private lateinit var timeAdapter : MovieTimeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.alpha = 0.0f
        binding.root.animate().alpha(1.0f).duration = 500

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // SEATS
        // SEAT SECTION

        val screenOrientation = resources.configuration.orientation

        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setSeatSize(if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) 600 else 220)
            .setCustomTitle(titleArray)
        seatBookView.show()
        setupNavigation()

        // DATE
        val dateSelect = binding.dateSelect


        dateAdapter = MovieDateAdapter(mutableListOf())
        { dateBlock ->
            // get the selections and reserve
            seatBookView.clearSelection()
            Log.d("TAG", "onViewCreated: ${viewModel.selectedSeats.value} and ${seatBookView.getSelectedIdList()}")
            viewModel.clearEverything() // clean the seats
            viewModel.datePosition = dateAdapter.selectedPosition // update the vm

            Log.d("TAG", "onViewCreated: ${viewModel.datePosition}")
            viewModel.selectedDate.value = (dateBlock.date) // update the selected date value
        }



        viewModel.dates.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) return@observe
            Log.d("DATE LIST", "DATE LIST: $it OBSERVED")
            dateAdapter.updateData(it)
            dateAdapter.selectFirstItem()
            Log.d("TAG", "Selected first position: ${viewModel.datePosition}")
            dateAdapter.notifyDataSetChanged()
//            // to ensure the first item is auto-selected
            viewModel.datePosition = 0
            viewModel.selectedDate.postValue(it[0].date)
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) {
            Log.d("selected date", "onViewCreated: $it OBSERVED")

            if (it == null) return@observe
            lifecycleScope.launch {
                viewModel.fetchTimes(args.movie.id)
            }

        }


        observeStatus()

        dateSelect.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        dateAdapter.setSelectionPosition(viewModel.datePosition)
        dateAdapter.notifyDataSetChanged()
        Log.d("TAG", "onViewCreated: ${viewModel.datePosition}")





        // TIMES

        val timeSelect = binding.timeSelect

        timeAdapter = MovieTimeAdapter(mutableListOf(ScreeningTime(LocalTime.MIDNIGHT, "0"))) // placeholder item
        {
            viewModel.selectedTime.value = (it)
            seatBookView.clearSelection()
            Log.d("TAG", "onViewCreated: ${viewModel.selectedTime.value}")
            viewModel.clearEverything()
            viewModel.timePosition = timeAdapter.selectedPosition

            // recap log
            Log.d("POWER OF FRIENDSHIP", "selected date: ${viewModel.selectedDate.value},\n" +
                    "selected time: ${viewModel.selectedTime.value?.time}\n," +
                    "selected theater: ${viewModel.selectedTime.value?.auditorium}\n")
        }

        timeSelect.apply {
            adapter = timeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.times.observe(viewLifecycleOwner) {
            Log.d("TIME LIST", "TIME LIST IMPORTANT IF IT IS NULL ERROR: $it OBSERVED")
            if (it.isNullOrEmpty()) return@observe
            Log.d("TIME LIST", "TIME LIST: $it OBSERVED")
            timeAdapter.updateData(it)
            timeAdapter.selectFirstItem()
            timeAdapter.notifyDataSetChanged()
            // to ensure the first item is auto-selected
//            it.forEach { screeningTime ->
//                Log.d("TIME LIST", "TIME LIST: $screeningTime OBSERVED")
//                }
////            viewModel.timePosition = 0
//            timeAdapter.selectFirstItem()
            viewModel.selectedTime.postValue(it[0])
            binding.auditString.text = "Sala ${it[0].auditorium}"

        }

//        viewModel.selectedDate.observe(viewLifecycleOwner) {
//            if (it == null) return@observe
//            viewModel.fetchTimes(args.movie.id)
//        }



        viewModel.selectedTime.observe(viewLifecycleOwner) {
            Log.d("IMPORTANT", "onViewCreated: $it OBSERVED")
            if (it == null) return@observe

            binding.auditString.text = "Sala ${it.auditorium}"
            seatBookView.clearSelection()
            seatBookView.clearRedSeats()
            viewModel.clearEverything()

            lifecycleScope.launch {
                viewModel.getRedSeats()
            }

        }

//        viewModel.selectedTime.observe(viewLifecycleOwner) {
//            if (it == null) return@observe
//        }
        // END OF CLUSTER____
        viewModel.redSeats.observe(viewLifecycleOwner) {
            Log.d("REDSEATS", "onViewCreated: $it OBSERVED")
            seatBookView.clearRedSeats()
            seatBookView.setBookedIdList(it)
        }


        //val testseats = SeatInterpreter.convertListToInteger(listOf("A1", "A2", "A3", "A4", "A5", "E1"))

        //seatBookView.setBookedIdList(testseats)

        seatBookView.setSeatClickListener(object : SeatClickListener {
            override fun onAvailableSeatClick(selectedIdList: List<Int>, view: View) {
                if(Session.loggedIn) {
                    viewModel.updateList(selectedIdList)
                    Log.d("SEATS", "onAvailableSeatClick: $selectedIdList and ${viewModel.selectedSeats.value}")
                } else {
                    showLoginRationale()
                    seatBookView.markAsAvailable(view as TextView)
                }
            }
            override fun onBookedSeatClick(view: View) {
            }
            override fun onReservedSeatClick(view: View) {
            }
        })

        // chips
        val chips = binding.chipGroup



        // CHECKOUT
        val checkoutButton = binding.checkoutButton
        viewModel.selectionObjects.observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: $it OBSERVED")
            checkoutButton.isEnabled = !it.isNullOrEmpty()
        }
        checkoutButton.setOnClickListener {
            val action = BookingFragmentDirections.actionBookingFragmentToCheckoutModal()
            findNavController().navigate(action)
        }

    }

    private fun showLoginRationale() {
        Dialog.showLoginDialog(requireContext()) {
            val action = BookingFragmentDirections.actionGlobalAuthFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                DTO.Stat.NETWORK_ERROR -> {
                    Dialog.showNetworkErrorDialog(requireContext())
                    viewModel.status.postValue(DTO.Stat.DEFAULT)
                    findNavController().popBackStack()
                }
                else -> {} // default included
            }
        }
    }

    private fun setupNavigation() {
        binding.inclusion.viewTitle.text = "Acquista Biglietto"
        binding.inclusion.navBack.setOnClickListener {
            // reset status
            viewModel.status.postValue(DTO.Stat.DEFAULT)
            findNavController().popBackStack()
        }

    }

    override fun onResume() {
        super.onResume()
        //requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onPause() {
        super.onPause()
        //requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.resetViewModel()
    }




}