package pwm.ar.arpacinema.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.UncontainedCarouselStrategy
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    // Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Categorie
    private val catItems = listOf(
        CategoryItem(R.drawable.guerra_icon, "Azione"),
        CategoryItem(R.drawable.cartoon_icon, "Animazione"),
        CategoryItem(R.drawable.anime_icon, "Anime"),
        CategoryItem(R.drawable.biografico_icon, "Biografico"),
        CategoryItem(R.drawable.commedia_icon, "Commedia"),
        CategoryItem(R.drawable.crime_icon, "Crime"),
        CategoryItem(R.drawable.documentario_icon, "Documentario"),
        CategoryItem(R.drawable.dramma_icon, "Dramma"),
        CategoryItem(R.drawable.fantasy_icon, "Fantasy"),
        CategoryItem(R.drawable.family_icon, "Family"),
        CategoryItem(R.drawable.shifi, "Sci-Fi"),
        CategoryItem(R.drawable.war_icon, "Guerra"),
        CategoryItem(R.drawable.horror_icon, "Horror"),
        CategoryItem(R.drawable.musical_icon, "Musical"),
        CategoryItem(R.drawable.romance_icon, "Romantico"),
        CategoryItem(R.drawable.storico_icon, "Storico"),
        CategoryItem(R.drawable.thriller_icon, "Thriller"),
        CategoryItem(R.drawable.western_icon, "Western")
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
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

        val dataset = listOf(
            CarouselItem("Spider-Man 2", "https://i.ebayimg.com/images/g/3h0AAOSwwNpkAO7-/s-l1600.jpg"),
            CarouselItem("Avengers: Endgame", "https://pad.mymovies.it/filmclub/2018/12/029/locandina.jpg"),
            CarouselItem("Deadpool & Wolverine", "https://cdn.marvel.com/content/1x/dp3_1sht_digital_srgb_ka_swords_v5_resized.jpg"),
            CarouselItem("Il Signore Degli Anelli: Il Ritorno Del Re", "https://static.posters.cz/image/1300/poster/il-signore-degli-anelli-il-ritorno-del-re-i104633.jpg"),
            CarouselItem("Il regno del pianeta delle scimmie", "https://www.cinecentrum.it/uploads/images/pianeta%20scimmie%202%202024.jpg"),
            CarouselItem("Interstellar", "https://m.media-amazon.com/images/I/71thymE6lwL._AC_UF1000,1000_QL80_.jpg"),
            CarouselItem("Trap", "https://www.trapthefilm.com/assets/images/fullbanner.jpg"),
            CarouselItem("Twisters", "https://images.justwatch.com/poster/315736719/s718/twisters.jpg")

            )


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
