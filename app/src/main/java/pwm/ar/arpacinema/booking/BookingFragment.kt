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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentBookingBinding
import pwm.ar.arpacinema.model.ScreeningDate
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var dateAdapter : MovieDateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // SEATS

        val screenOrientation = resources.configuration.orientation

        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setSeatSize(if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) 600 else 220)
            .setCustomTitle(titleArray)

        setupNavigation()

        // DATE
        val dateSelect = binding.dateSelect




        dateAdapter = MovieDateAdapter(
            (List(20) {
                ScreeningDate(LocalDate.now(), LocalTime.now())
            }) ){


            // get the selections and reserve
            seatBookView.clearSelection()
            Log.d("TAG", "onViewCreated: ${viewModel.selectedSeats.value} and ${seatBookView.getSelectedIdList()}")
            viewModel.clearEverything()
            viewModel.datePosition = dateAdapter.selectedPosition
            Toast.makeText(requireContext(), it.date.toString(), Toast.LENGTH_SHORT).show()
            Log.d("TAG", "onViewCreated: ${viewModel.datePosition}")
            viewModel.selectedDate.postValue(it.date)
        }

        dateSelect.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        dateAdapter.setSelectionPosition(viewModel.datePosition)
        dateAdapter.notifyDataSetChanged()
        Log.d("TAG", "onViewCreated: ${viewModel.datePosition}")





        // TIMES

        val timeSelect = binding.timeSelect

        val timeAdapter = MovieTimeAdapter(
            (List(20) {
                ScreeningDate(LocalDate.now(), LocalTime.now())
            }) ){
            viewModel.selectedDate.postValue(it.date)
            Toast.makeText(requireContext(), it.date.toString(), Toast.LENGTH_SHORT).show()
        }

        timeSelect.apply {
            adapter = timeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }








        seatBookView.show()



        //val testseats = SeatInterpreter.convertListToInteger(listOf("A1", "A2", "A3", "A4", "A5", "E1"))

        //seatBookView.setBookedIdList(testseats)

        seatBookView.setSeatClickListener(object : SeatClickListener {
            override fun onAvailableSeatClick(selectedIdList: List<Int>, view: View) {




                viewModel.updateList(selectedIdList)
                Log.d("SEATS", "onAvailableSeatClick: $selectedIdList and ${viewModel.selectedSeats.value}")

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

    private fun setupNavigation() {
        binding.inclusion.viewTitle.text = "Acquista Biglietto"
        binding.inclusion.navBack.setOnClickListener {
            findNavController().navigateUp()
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
    }




}