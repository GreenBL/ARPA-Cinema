package pwm.ar.arpacinema.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        if(Session.user != null) {
            binding.tophome.titleStr.text = "Bentornato/a, ${Session.user!!.name}"
            binding.tophome.textView.visibility = View.GONE
            binding.tophome.badge.isEnabled = false
            binding.tophome.icon.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(requireContext())
                .load(R.drawable.banner_placeholder)
                .into(binding.tophome.icon)
        }


        binding.searchBar.setOnClickListener {
            val sharedElementView = binding.searchBar
            val nav = findNavController()

//            val args = Bundle().apply {
//                putBoolean("showIme", true)
//            }

            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(showIme = true)

            val extras = FragmentNavigatorExtras(sharedElementView to "shared_search")
            //nav.navigate(R.id.searchFragment, args, null, extras)
            nav.navigate(action, extras)
        }

        val recyclerView = binding.categoryRV
        val catSnap = PagerSnapHelper()


        val catAdapter = CategoryAdapter(catItems) { catItem ->
            // TODO handle category click, probably a custom view separate from the search one
        }

        recyclerView.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            catSnap.attachToRecyclerView(this)

        }

        val dataset = listOf(
            CarouselItem("Avengers: Endgame", "https://pad.mymovies.it/filmclub/2018/12/029/locandina.jpg"),
            CarouselItem("Deadpool & Wolverine", "https://cdn.marvel.com/content/1x/dp3_1sht_digital_srgb_ka_swords_v5_resized.jpg"),
            //CarouselItem("Il Signore Degli Anelli: Il Ritorno Del Re", "https://static.posters.cz/image/1300/poster/il-signore-degli-anelli-il-ritorno-del-re-i104633.jpg"),
            //CarouselItem("Il regno del pianeta delle scimmie", "https://www.cinecentrum.it/uploads/images/pianeta%20scimmie%202%202024.jpg"),
            CarouselItem("Interstellar", "https://mir-s3-cdn-cf.behance.net/project_modules/hd/8d8f28105415493.619ded067937d.jpg"),
            CarouselItem("Trap", "https://www.trapthefilm.com/assets/images/fullbanner.jpg"),
            CarouselItem("Twisters", "https://images.justwatch.com/poster/315736719/s718/twisters.jpg")

            )

        val dataset2 = listOf(
            CarouselItem("Il Signore Degli Anelli: Il Ritorno Del Re", "https://static.posters.cz/image/1300/poster/il-signore-degli-anelli-il-ritorno-del-re-i104633.jpg"),
            CarouselItem("Il regno del pianeta delle scimmie", "https://www.cinecentrum.it/uploads/images/pianeta%20scimmie%202%202024.jpg"),
            CarouselItem("Trap", "https://www.trapthefilm.com/assets/images/fullbanner.jpg"),
            CarouselItem("Twisters", "https://images.justwatch.com/poster/315736719/s718/twisters.jpg")
        )


        // add a item decoration
        val decorator = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL).apply {
            dividerThickness = 32
            dividerColor = 0x00FFFFFF
        }

        val carAdapter = CarouselAdapter(dataset)
        val carousel = binding.carouselRV
        val carouselLayoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        carouselLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        carousel.apply {
            adapter = carAdapter
            layoutManager = carouselLayoutManager
            //addItemDecoration(decorator)
        }

        if(carAdapter.itemCount > 2) {
            carousel.scrollToPosition(1)
        }

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(carousel)


        // popular movies
        val popAdapter = PopularAdapter(dataset2) {}
        val popRV = binding.popRV
        val popLayoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        popLayoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        popRV.apply {
            adapter = popAdapter
            layoutManager = popLayoutManager
        }

        val popsnapHelper = CarouselSnapHelper()
        popsnapHelper.attachToRecyclerView(binding.popRV)

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
