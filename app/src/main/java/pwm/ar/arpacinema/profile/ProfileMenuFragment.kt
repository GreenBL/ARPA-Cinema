package pwm.ar.arpacinema.profile

import android.app.ActionBar
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentHomeBinding
import pwm.ar.arpacinema.databinding.FragmentProfileMenuBinding
import pwm.ar.arpacinema.util.PlaceholderDrawable

class ProfileMenuFragment : Fragment() {

    private var _binding: FragmentProfileMenuBinding? = null
    private val binding get() = _binding!!

    private val topMenuItems = listOf(
        MenuItem(R.drawable.outline_fastfood_24, "Premi"),
        MenuItem(R.drawable.round_history_24, "Cronologia")
        // TODO
    )

    private val centerMenuItems = listOf(
        MenuItem(R.drawable.outline_person_24, "Profilo"),
        MenuItem(R.drawable.outline_manage_accounts_24, "Gestione Account"),
        MenuItem(R.drawable.outline_account_balance_wallet_24, "Portafoglio")
    )

    private val logoutItem = listOf(
        MenuItem(R.drawable.outline_logout_24, "Logout", false)
    )

    companion object {
        fun newInstance() = ProfileMenuFragment()
    }

    private val viewModel: ProfileMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Session.user?.let { viewModel.setUser(it) }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val topMenu = binding.topMenu
        val centerMenu = binding.centerMenu
        val bottomMenu = binding.bottomMenu
        val image = binding.profileimage
        val levelChip = binding.chip

        Glide.with(requireContext())
            .load(Session.userImageURL)
            .placeholder(PlaceholderDrawable.getPlaceholderDrawable())
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.outline_cloud_off_24)
            .into(image)

        val topMenuAdapter = MenuAdapter(topMenuItems) { menuItem ->
            when (menuItem.label) {
                "Premi" -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_rewardsFragment)
                }
                "Cronologia" -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_historyFragment)
                }
            }
        }

        viewModel.userLevel.observe(viewLifecycleOwner) {
            levelChip.text = "Livello ${viewModel.userLevel.value}"
        }

        viewModel.userPoints.observe(viewLifecycleOwner) {
            if (it == 1000) {
                levelChip.setOnClickListener {
                    Dialog.showLevelUpRationale(requireContext(), viewModel.userLevel.value!!) {
                        lifecycleScope.launch {
                            viewModel.levelUp()
                            levelChip.text = "Livello ${viewModel.userLevel.value}"
                        }
                    }
                }
                levelChip.text = "Livello ${viewModel.userLevel.value} ⟫ Livello ${viewModel.userLevel.value?.plus(
                    1
                )}"
                levelChip.isClickable = true
                levelChip.setChipStrokeColorResource(R.color.colorGoldContainer_highContrast)
                levelChip.setChipBackgroundColorResource(R.color.colorGoldContainer)
                levelChip.chipStrokeWidth = 8F
            } else {
                levelChip.text = "Livello ${viewModel.userLevel.value}"
                levelChip.setChipStrokeColorResource(com.google.android.material.R.color.m3_chip_stroke_color)
                levelChip.setChipBackgroundColorResource(com.google.android.material.R.color.m3_chip_background_color)
                levelChip.chipStrokeWidth = 2F
                levelChip.isClickable = false
            }
        }


        val centerMenuAdapter = MenuAdapter(centerMenuItems) { menuItem ->
            when (menuItem.label) {
                "Profilo" -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_infoFragment)
                }
                "Gestione Account" -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_accountFragment)
                }
                "Portafoglio" -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_walletFragment)
                }
            }

        }

        val bottomMenuAdapter = MenuAdapter(logoutItem) { menuItem ->
            when (menuItem.label) {
                "Logout" -> {
                    Dialog.showLogoutConfirmationDialog(requireContext()) {
                        Session.invalidateUser(requireContext())
                        viewModel.clearUser()
                        findNavController().navigate(R.id.homeFromProfile)
                        findNavController().popBackStack()
                    }
                }
            }
        }

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                .apply { isLastItemDecorated = false }

        centerMenu.apply {
            adapter = centerMenuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }

        topMenu.apply {
            adapter = topMenuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }

        bottomMenu.apply {
            adapter = bottomMenuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
