package pwm.ar.arpacinema.rewards

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentOptionsBinding
import pwm.ar.arpacinema.model.Reward

class OptionsFragment : Fragment() {

    private val listOfOptions = listOf(
        Reward("Popcorn", "XL (500g)", 335, "", ""),
        Reward("Popcorn", "L (350g)", 275, "", ""),
        Reward("Popcorn", "M (250g)", 215, "", ""),
        Reward("Popcorn", "S (150g)", 155, "", ""),
    )

    private var _binding : FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = OptionsFragment()
    }

    private val viewModel: OptionsViewModel by viewModels()
    private val args : OptionsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // navbar
        binding.include.viewTitle.text = "Premi Bar"
        binding.include.navBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val description = binding.stringResDescription



        val title = args.type
        val textview = binding.textView34
        textview.text = title
        val image = binding.imageView15

        when (title) {
            "Popcorn" -> description.text = getString(R.string.popcorn_desc)
            "Bibite" -> description.text = getString(R.string.bibite_desc)
            "Combo" -> description.text = getString(R.string.combo_desc)
        }
        when (title) {
            "Popcorn" -> image.setImageResource(R.drawable.popcorn_buckets__dark_background)
            "Bibite" -> image.setImageResource(R.drawable.tall_drink_cups__dark_background)
            "Combo" -> image.setImageResource(R.drawable.popcorn_buckets_and_then_some_drink_cups_in_front_)
        }


        val optionsMenu = binding.optionsMenu
        val optionsAdapter = OptionsAdapter(listOfOptions) {}

        val materialDivider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            .apply {
                isLastItemDecorated = false
            }
        optionsMenu.apply {
            addItemDecoration(materialDivider)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = optionsAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}