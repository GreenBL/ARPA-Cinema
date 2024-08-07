package pwm.ar.arpacinema.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.android.material.carousel.UncontainedCarouselStrategy
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.ActivityMainBinding
import pwm.ar.arpacinema.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    // nna binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // TODO
    private val catItems = listOf(CategoryItem(R.drawable.baseline_person_24, "Azione"),
        CategoryItem(R.drawable.baseline_person_24, "Avventura"),
        CategoryItem(R.drawable.outline_theater_comedy_24, "Commedia"),
        CategoryItem(R.drawable.baseline_person_24, "Dramma"),
        CategoryItem(R.drawable.baseline_person_24, "Horror"),
        CategoryItem(R.drawable.baseline_person_24, "Sci-Fi"),
        CategoryItem(R.drawable.baseline_person_24, "Fantasy"),
        CategoryItem(R.drawable.baseline_person_24, "Romantico"),
        CategoryItem(R.drawable.baseline_person_24, "Thriller"),
        CategoryItem(R.drawable.baseline_person_24, "Mistero"),
        CategoryItem(R.drawable.baseline_person_24, "Documentario"),
        CategoryItem(R.drawable.baseline_person_24, "Animazione"),
        CategoryItem(R.drawable.baseline_person_24, "Musical"),
        CategoryItem(R.drawable.baseline_person_24, "Storico"),
        CategoryItem(R.drawable.baseline_person_24, "Biografico"),
        CategoryItem(R.drawable.baseline_person_24, "Crime"),
        CategoryItem(R.drawable.baseline_person_24, "Western"),
        CategoryItem(R.drawable.baseline_person_24, "Family"),
        CategoryItem(R.drawable.baseline_person_24, "Noir")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // might need arguments? keep it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // IMPORTANT : do stuff in the onViewCreated cuz google
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE

        val recyclerView = binding.categoryRV

        val catAdapter = CategoryAdapter(catItems) { catItem ->
            // TODO handle category click, probably a custom view separate from the search one
        }

        recyclerView.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // TODO: EXAMPLE OF HOW TO USE THE CAROUSEL
        val dataset = listOf(CarouselItem("Spider-Man 2", "https://i.ebayimg.com/images/g/3h0AAOSwwNpkAO7-/s-l1600.jpg"),
            CarouselItem("Avengers Endgame", "https://pad.mymovies.it/filmclub/2018/12/029/locandina.jpg"),
            CarouselItem("Deadpool & Wolverine", "https://cdn.marvel.com/content/1x/dp3_1sht_digital_srgb_ka_swords_v5_resized.jpg"),
            CarouselItem("Il Signore Degli Anelli: Il Ritorno Del Re", "https://static.posters.cz/image/1300/poster/il-signore-degli-anelli-il-ritorno-del-re-i104633.jpg"),
            CarouselItem("Spider-Man 6", "https://i.ebayimg.com/images/g/3h0AAOSwwNpkAO7-/s-l1600.jpg"),
            CarouselItem("Spider-Man 6", "https://i.ebayimg.com/images/g/3h0AAOSwwNpkAO7-/s-l1600.jpg"),
            CarouselItem("Spider-Man 6", "https://i.ebayimg.com/images/g/3h0AAOSwwNpkAO7-/s-l1600.jpg"))

        val carAdapter = CarouselAdapter(dataset)
        val carousel = binding.carouselRV
        val carouselLayoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        carouselLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        carousel.apply {
            adapter = carAdapter
            layoutManager = carouselLayoutManager
        }

        carousel.scrollToPosition(2)

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(carousel)

        // handle badge click
        binding.tophome.badge.setOnClickListener {
             val sharedElementView = binding.tophome.badge

             val cardNavController = findNavController()

            val extras = FragmentNavigatorExtras(sharedElementView to "shared_card")
            cardNavController.navigate(R.id.authFragment, null, null, extras)
            //findNavController().navigate(R.id.authFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // null the binding to avoid memory leaks [gg]
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}