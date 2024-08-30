package pwm.ar.arpacinema.discovery.category

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentCategoryBinding
import pwm.ar.arpacinema.dev.ShowingItem
import pwm.ar.arpacinema.discovery.LoadingScreenAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.catMovieList

        // recycler view configuration
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        // get the safe arg category enum thing
        val category = args.category

        // set the title
        binding.topBarInclude.viewTitle.text = category.category

        // set up the placeholder adapter for the recycler view
        val placeholderAdapter = LoadingScreenAdapter(placeholders)
        recycler.adapter = placeholderAdapter // once stuff loads swap it with the good one


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}