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
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import pwm.ar.arpacinema.databinding.FragmentBookingBinding
import pwm.ar.arpacinema.util.SeatInterpreter

class BookingFragment : Fragment() {

    private var _binding : FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var seats = (
                    "/_AAA_AAA_" +
                    "/AAAA_AAAA" +
                    "/AAAA_AUAA" +
                    "/AUUA_AAAA" +
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

    private lateinit var seatBookView: SeatBookView

    companion object {
        fun newInstance() = BookingFragment()
    }

    private val viewModel: BookingViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // DATE
        val dateSelect = binding.dateSelect

        val dateAdapter = MovieDateAdapter(
            (List(20) {
                MovieDateAdapter.ScreeningDate("a")
            }) ){

        }

        dateSelect.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }





        // TIMES

        val timeSelect = binding.timeSelect

        val timeAdapter = MovieTimeAdapter(
            (List(20) {
                MovieTimeAdapter.ScreeningTime("a")
            }) ){

        }

        timeSelect.apply {
            adapter = timeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }





        // SEATS

        val screenOrientation = resources.configuration.orientation

        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setSeatSize(if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) 600 else 220)
            .setCustomTitle(titleArray)




        seatBookView.show()

        val testseats = SeatInterpreter.convertListToInteger(listOf("A1", "A2", "A3", "A4", "A5", "E1"))

        seatBookView.setBookedIdList(testseats)

        seatBookView.setSeatClickListener(object : SeatClickListener {
            override fun onAvailableSeatClick(selectedIdList: List<Int>, view: View) {
                if (selectedIdList.isNotEmpty()) {
                    Log.d("TAG", "onAvailableSeatClick: $selectedIdList")
                }
            }
            override fun onBookedSeatClick(view: View) {
            }
            override fun onReservedSeatClick(view: View) {
            }
        })

        // selection recap

        val selectionList = binding.seatsSelected
        val selectionAdapter = SelectionAdapter(listOf("A1", "A2", "A3", "A4", "A5", "E1")) // test data
        selectionList.apply {
            adapter = selectionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}