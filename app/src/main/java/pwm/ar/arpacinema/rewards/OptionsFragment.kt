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
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentOptionsBinding
import pwm.ar.arpacinema.model.Reward
import pwm.ar.arpacinema.repository.DTO

class OptionsFragment : Fragment() {

    // no endpoint was created for the fetch? G R E A T
    private val listOfOptions_popcorn = listOf(
        Reward(4, "Popcorn", "XL (500g)", 400, ""),
        Reward(3, "Popcorn", "L (300g)", 350, ""),
        Reward(2, "Popcorn", "M (200g)", 300, ""),
        Reward(1, "Popcorn", "S (100g)", 200, ""),
    )

    private val listOfOptions_drinks = listOf(
        Reward(4, "Drinks", "XL(0,75 L)", 400, ""),
        Reward(3, "Drinks", "L(0,5 L)", 350, ""),
        Reward(2, "Drinks", "M(0,4 L)", 300, ""),
        Reward(1, "Drinks", "S(0,25 L)", 200, "")
    )

    private val listOfOptions_combo = listOf(
        Reward(3, "Combo", "Menu ExtraLarge", 850, ""),
        Reward(2, "Combo", "Menu Large", 600, ""),
        Reward(1, "Combo", "Menu Medium", 500, "")
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
        viewModel.wakeUp()
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

        var callback : (Reward) -> Unit = {}

        // Please don't look past this point or your eyes will melt

        when (title) {
            "Popcorn" -> callback = { it ->
                viewModel.redeemPopcorn(it.id)
            }
            "Bibite" -> callback = { it ->
                viewModel.redeemDrink(it.id)
            }
            "Combo" -> callback = { it ->
                viewModel.redeemCombo(it.id)
            }
        }

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

        // diff for options type


        val dataSet = when (args.type) {
            "Popcorn" -> listOfOptions_popcorn
            "Bibite" -> listOfOptions_drinks
            "Combo" -> listOfOptions_combo
            else -> emptyList()
        }
        val optionsMenu = binding.optionsMenu
        val optionsAdapter = OptionsAdapter(dataSet) { reward ->
            if (viewModel.userPointsAndLevel.value!!.points!! >= reward.points) {
                Dialog.showRedeemRationaleDialog(requireContext(), reward) {
                    callback(reward)
                }
            } else {
                Dialog.showNotEnoughPointsDialog(requireContext())
            }

        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                DTO.Stat.DEFAULT -> {}
                DTO.Stat.NETWORK_ERROR -> {
                    Dialog.showNetworkErrorDialog(requireContext())
                    viewModel.resetStatus()
                }
                DTO.Stat.SUCCESS -> {
                    Dialog.showRedeemSuccessDialog(requireContext())
                    viewModel.resetStatus()
                }
                else -> {
                    Dialog.showNetworkErrorDialog(requireContext())
                    viewModel.resetStatus()
                }
            }
        }

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