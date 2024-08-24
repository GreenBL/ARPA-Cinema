package pwm.ar.arpacinema.discovery.movie

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentMoviePageBinding

class MoviePageFragment : Fragment() {

    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MoviePageFragment()
    }

    private val viewModel: MoviePageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}