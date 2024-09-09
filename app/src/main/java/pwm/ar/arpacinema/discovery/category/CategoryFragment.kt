package pwm.ar.arpacinema.discovery.category

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentCategoryBinding
import pwm.ar.arpacinema.dev.ShowingItem
import pwm.ar.arpacinema.discovery.LoadingScreenAdapter
import pwm.ar.arpacinema.discovery.ScreeningAdapter

class CategoryFragment : Fragment() {

    private val placeholders = List(5) {
        ShowingItem() // ???? TODO
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewModel: CategoryViewModel by viewModels()
    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    val scope = lifecycleScope

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // keep this fragment instance


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val recycler = binding.catMovieList

        // recycler view configuration
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        // get the safe arg category enum thing
        val category = args.category
        Log.d("CategoryFragment", "onViewCreated: ${category.category}")

        // set the title
        binding.topBarInclude.viewTitle.text = category.category

        // set up the placeholder adapter for the recycler view
        val placeholderAdapter = LoadingScreenAdapter(placeholders)
        recycler.adapter = placeholderAdapter // once stuff loads swap it with the good one

        scope.launch {
            viewModel.getMoviesByCategory(category.category)
//            Log.d("CategoryFragment", "onViewCreated: ${viewModel.movies.value}")
//            if (viewModel.movies.value != null) {
//                val adapter = ScreeningAdapter(viewModel.movies.value!!) {}
//                recycler.adapter = adapter
//            }

        }

        viewModel.movies.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = ScreeningAdapter(it) { movie ->
                    val action = CategoryFragmentDirections.actionGlobalMoviePageFragment(movie)
                    Log.d("CategoryFragment", "onViewCreated: $movie")
                    findNavController().navigate(action)
                }
                recycler.adapter = adapter
                binding.notfoundframe.visibility = View.GONE
            }

            if (it != null) {
                if (it.isEmpty()) {
                    binding.notfoundframe.visibility = View.VISIBLE
                }
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}