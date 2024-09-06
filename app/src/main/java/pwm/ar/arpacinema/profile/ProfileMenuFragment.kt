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




        // TODO: Use the ViewModel
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
        // force show nav bar
        //requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val topMenu = binding.topMenu
        val centerMenu = binding.centerMenu
        val bottomMenu = binding.bottomMenu

        val topMenuAdapter = MenuAdapter(topMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
                when (menuItem.label) {
               "Premi" -> {
                   findNavController().navigate(R.id.action_profileMenuFragment_to_rewardsFragment)
               }
               "Cronologia" -> {
             //      findNavController().navigate(R.id.action_profileMenuFragment_to_historyFragment) todo
               }
            }

        }

        val centerMenuAdapter = MenuAdapter(centerMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
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
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT)
                .show()
        }

        val bottomMenuAdapter = MenuAdapter(logoutItem) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
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
//            lifecycleScope.launch {
//                if (Session.getUserId(requireContext()) != null) {
//                    Session.invalidateUser(requireContext())
//                    Session.printUserId(requireContext())
//                    viewModel.clearUser()
//                }
//            }
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
        //(activity as? AppCompatActivity)?.supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE
    }
}