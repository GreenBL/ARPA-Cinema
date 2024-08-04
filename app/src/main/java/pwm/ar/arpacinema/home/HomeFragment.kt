package pwm.ar.arpacinema.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    // nna binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val catItems = listOf(CategoryItem(R.drawable.baseline_person_24, "Giallo"),
        CategoryItem(R.drawable.baseline_person_24, "Avventura"),
        CategoryItem(R.drawable.baseline_person_24, "Fantasy"),
        CategoryItem(R.drawable.baseline_person_24, "Horror"),
        CategoryItem(R.drawable.baseline_person_24, "Sci-Fi"),
        CategoryItem(R.drawable.baseline_person_24, "Thriller")
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

        val recyclerView = binding.categoryRV

        val catAdapter = CategoryAdapter(catItems) { catItem ->
            // TODO: HANDLE CATEGORY CLICK
        }

        recyclerView.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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