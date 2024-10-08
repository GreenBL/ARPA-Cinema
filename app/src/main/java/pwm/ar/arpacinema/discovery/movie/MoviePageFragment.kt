package pwm.ar.arpacinema.discovery.movie

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentMoviePageBinding
import pwm.ar.arpacinema.util.PlaceholderDrawable

class MoviePageFragment : Fragment() {

    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!

    private val args : MoviePageFragmentArgs by navArgs()

    companion object {
        fun newInstance() = MoviePageFragment()
    }

    private val viewModel: MoviePageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val appbar = binding.appbar
        val textTitle = binding.include.viewTitle
        val navback = binding.include.navBack.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("showIme" to false))
            findNavController().popBackStack()
        }
        val movie = args.movie
        binding.movie = movie
        textTitle.text = "Scheda Film"
        textTitle.visibility = View.GONE
        appbar.addLiftOnScrollListener{
                _, _ ->
            // on lift on scroll
            if(appbar.isLifted) {
                textTitle.visibility = View.VISIBLE
            } else {
                textTitle.visibility = View.GONE
            }
        }

        val imageview = binding.imageView

        val placeholder = PlaceholderDrawable.getPlaceholderDrawable()

        Glide.with(this)
            .load(movie.posterUrl)
            .placeholder(placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageview)

        binding.buyButton.setOnClickListener {
            val action = MoviePageFragmentDirections.actionMoviePageFragmentToBookingFragment(movie)
            findNavController().navigate(action)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}