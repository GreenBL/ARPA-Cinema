package pwm.ar.arpacinema.discovery

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.platform.MaterialContainerTransform
import pwm.ar.arpacinema.MainActivity

import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.FragmentSearchBinding
import pwm.ar.arpacinema.dev.ShowingItem

class SearchFragment : Fragment() {

    private val mockList = List(5) {
        ShowingItem()
    }

    private var showIme = true

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()


    private val args: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitions()

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val filterFAB = binding.filter

        val mainAdapter = ScreeningAdapter(emptyList()) {
            Toast.makeText(requireContext(), "Clicked something", Toast.LENGTH_SHORT).show()
            showIme = false
            findNavController().navigate(R.id.action_searchFragment_to_moviePageFragment)
        }



        val recyclerView = binding.resultsRV
//        recyclerView.adapter = ScreeningAdapter(aah) {
//            Toast.makeText(requireContext(), "Clicked something", Toast.LENGTH_SHORT).show()
//            showIme = false
//            findNavController().navigate(R.id.action_searchFragment_to_moviePageFragment)
//        }

        recyclerView.apply {

        }
        // material divider for recycler view
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            dividerThickness = 8
            dividerColor = 0x0DFFFFFF
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(divider)
            adapter = LoadingScreenAdapter(mockList)
        }
////////////////////////////////////////////////// TODO ///////////////////////////////// SWAP ADPAPTER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        val searchBarField = binding.searchBarLayout

        searchBarField.setStartIconOnClickListener {
            val windowInsetsController = view.windowInsetsController
            windowInsetsController?.hide(WindowInsets.Type.ime())
            findNavController().popBackStack()
        }

        // TODO: only if u are from home fragment

        if (showIme) {
            searchBarField.postDelayed({
                searchBarField.requestFocus()
                val windowInsetsController = view.windowInsetsController
                windowInsetsController?.show(WindowInsets.Type.ime())
            }, 200)
        }

        Log.d("SearchFragment", "onViewCreated: $showIme")


        binding.root.setOnApplyWindowInsetsListener { v, insets ->
            val imeVisible = insets.isVisible(WindowInsets.Type.ime())
            val params = binding.searchBarLayout.layoutParams as? AppBarLayout.LayoutParams
            if (params != null) {
                if (imeVisible) {
                    params.scrollFlags = 0
                } else {
                    params.scrollFlags = (
                            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                            )
                    searchBarField.clearFocus()
                }
                binding.searchBarLayout.layoutParams = params
            }
            v.onApplyWindowInsets(insets)
        }

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                recyclerView.adapter = ScreeningAdapter(movies) { movie ->
                    val action = SearchFragmentDirections.actionSearchFragmentToMoviePageFragment(movie)
                    showIme = false
                    findNavController().navigate(action)
                }
            }
        }

        // search bar input
        searchBarField.editText?.addTextChangedListener { text ->
            viewModel.searchMovies(text.toString())
        }

        // search results
        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            if (searchResults != null) {
                val adapter = recyclerView.adapter as? ScreeningAdapter
                adapter?.updateData(searchResults)
            }
        }


    }

    private fun setupTransitions() {
        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = true
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        this.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 600L
            isElevationShadowEnabled = true
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }
    }

}