package pwm.ar.arpacinema.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.Categories.*
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.databinding.FragmentHomeBinding
import pwm.ar.arpacinema.discovery.movie.MoviePageFragmentDirections
import pwm.ar.arpacinema.model.Categories
import pwm.ar.arpacinema.model.Movie
import pwm.ar.arpacinema.model.Promotion


class HomeFragment : Fragment() {

    // Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel : HomeViewModel by viewModels()

    // Categorie
    private val catItems = listOf(
        CategoryItem(R.drawable.guerra_icon, ACTION),
        CategoryItem(R.drawable.cartoon_icon, ANIMATION),
        CategoryItem(R.drawable.anime_icon, ANIME),
        CategoryItem(R.drawable.biografico_icon, BIOGRAPH),
        CategoryItem(R.drawable.commedia_icon, COMEDY),
        //CategoryItem(R.drawable.crime_icon, CRIME),
        CategoryItem(R.drawable.documentario_icon, DOC),
        CategoryItem(R.drawable.dramma_icon, DRAMA),
        //CategoryItem(R.drawable.fantasy_icon, FANTASY),
        CategoryItem(R.drawable.family_icon, FAMILY),
        CategoryItem(R.drawable.shifi, SCIFI),
        CategoryItem(R.drawable.war_icon, WAR),
        CategoryItem(R.drawable.horror_icon, HORROR),
        CategoryItem(R.drawable.musical_icon, MUSICAL),
        CategoryItem(R.drawable.romance_icon, ROMANCE),
        CategoryItem(R.drawable.storico_icon, STORICAL),
        CategoryItem(R.drawable.thriller_icon, THRILLER),
        //CategoryItem(R.drawable.western_icon, WESTERN)
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if(Session.user != null) {
            binding.tophome.titleStr.text = "Ciao ${Session.user!!.name}!"
            binding.tophome.textView.text = ""

            binding.tophome.badge.isEnabled = false
            binding.tophome.icon.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(requireContext())
                .load(R.drawable.banner_placeholder)
                .into(binding.tophome.icon)
        }


        binding.searchBar.setOnClickListener {
            val sharedElementView = binding.searchBar
            val nav = findNavController()

            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(showIme = true)

            val extras = FragmentNavigatorExtras(sharedElementView to "shared_search")
            //nav.navigate(R.id.searchFragment, args, null, extras)
            nav.navigate(action, extras)
        }

        val recyclerView = binding.categoryRV
        val catSnap = PagerSnapHelper()


        val catAdapter = CategoryAdapter(catItems) { catItem ->
            val cat : Categories = catItem.category
            val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(cat)
            Toast.makeText(requireContext(), catItem.category.toString(), Toast.LENGTH_SHORT).show()
            findNavController().navigate(action)
        }

        recyclerView.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            catSnap.attachToRecyclerView(this)

        }


        val dataset2 = listOf(
            Promotion("Poster di Ciccio", "Acquista ora e ricevi un poster omaggio!", "Si lunghissima", "https://picsum.photos/2100/900")
        )


        // add a item decoration
        val decorator = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL).apply {
            dividerThickness = 32
            dividerColor = 0x00FFFFFF
        }

        // bottom carousel
        val snapHelper = CarouselSnapHelper()
        val bottomDots = binding.bottomGear


        val carAdapter = CarouselAdapter(emptyList()) { movie ->

            val action = HomeFragmentDirections.actionGlobalMoviePageFragment(movie)
            findNavController().navigate(action)

        }
        val carousel = binding.carouselRV
        val carouselLayoutManager = CarouselLayoutManager(UncontainedCarouselStrategy()).apply {
            carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        }


        carousel.apply {
            adapter = carAdapter
            layoutManager = carouselLayoutManager
        }


        snapHelper.attachToRecyclerView(carousel)


        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies == null || movies.isEmpty()) {
                return@observe
            }
            movies.let {
                carAdapter.updateData(it)

                if (it.size > 2) {
                    carousel.scrollToPosition(1)
                }
                bottomDots.attachToRecyclerView(binding.carouselRV, snapHelper)
                carAdapter.registerAdapterDataObserver(bottomDots.adapterDataObserver)

            }
        }






        // popromotions
        val popAdapter = PromoAdapter(dataset2) {
            val action = HomeFragmentDirections.actionHomeFragmentToPromoFragment(it)
            findNavController().navigate(action)
        }
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

        val topDots = binding.topIndi
        topDots.attachToRecyclerView(binding.popRV, popsnapHelper)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
