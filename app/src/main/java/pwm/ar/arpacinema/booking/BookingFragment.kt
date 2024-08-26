package pwm.ar.arpacinema.booking

import android.content.pm.ActivityInfo
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

class BookingFragment : Fragment() {

    private var _binding : FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var seats = (
                    "/__A_AAAAA_A__" +
                            "/_____________" +
                    "/_AA_AAAAA_AA_" +
                            "/_____________" +
                    "/_AA_AAAAA_AA_" +
                            "/_____________" +
                    "/AAA_AAAAA_AAA" +
                    "/_____________" +
                    "/AAA_AAAAA_AAA" +
                            "/_____________" +
                    "/AAA_AAAAA_AAA" +
                    "/_____________" +
                    "/AAA_AAAAA_AAA" +
                            "/_____________" +
                    "/AAA_AAAAA_AAA"
            )

    private var title = listOf(
        "/", "", "F1P1", "F1P2", "F1P3", "F1P4", "F1P5", "F1P6", "F1P7", "F1P8", "",
        "/", "F2P1", "F2P2", "F2P3", "F2P4", "F2P5", "F2P6", "F2P7", "F2P8", "F2P9", "F2P10",
        "/", "F3P1", "F3P2", "F3P3", "F3P4", "F3P5", "F3P6", "F3P7", "F3P8", "F3P9", "F3P10",
        "/", "F4P1", "F4P2", "F4P3", "F4P4", "F4P5", "F4P6", "F4P7", "F4P8", "F4P9", "F4P10",
        "/", "F5P1", "F5P2", "F5P3", "F5P4", "F5P5", "F5P6", "F5P7", "F5P8", "F5P9", "F5P10",
        "/", "F6P1", "F6P2", "F6P3", "F6P4", "F6P5", "F6P6", "F6P7", "F6P8", "F6P9", "F6P10",
        "/", "F7P1", "F7P2", "F7P3", "F7P4", "F7P5", "F7P6", "F7P7", "F7P8", "F7P9", "F7P10",
        "/", "F8P1", "F8P2", "F8P3", "F8P4", "F8P5", "F8P6", "F8P7", "F8P8", "F8P9", "F8P10",
        "/", "F9P1", "F9P2", "F9P3", "F9P4", "F9P5", "F9P6", "F9P7", "F9P8", "F9P9", "F9P10",
        "/", "F10P1", "F10P2", "F10P3", "F10P4", "F10P5", "F10P6", "F10P7", "F10P8", "F10P9", "F10P10",
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

        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(false)
            //.setCustomTitle(title)
            //.setSeatLayoutPadding(2)
            //.setSeatSize(164)
            .setSeatSizeBySeatsColumnAndLayoutWidth(13, -1)


        seatBookView.show()

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