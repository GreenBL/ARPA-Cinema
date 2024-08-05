package pwm.ar.arpacinema.profile

import android.app.ActionBar
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentHomeBinding
import pwm.ar.arpacinema.databinding.FragmentProfileMenuBinding

class ProfileMenuFragment : Fragment() {

    private var _binding: FragmentProfileMenuBinding? = null
    private val binding get() = _binding!!

    private val topMenuItems = listOf(
        MenuItem(R.drawable.outline_fastfood_24, "Premi"),
        MenuItem(R.drawable.round_history_24, "Storico Acquisti")
        // TODO
    )

    private val centerMenuItems = listOf(
        MenuItem(R.drawable.outline_person_24, "Informazioni Profilo"),
        MenuItem(R.drawable.outline_manage_accounts_24, "Gestione Account"),
        MenuItem(R.drawable.outline_account_balance_wallet_24, "Portafoglio")
    )

    private val logoutItem = listOf(
        MenuItem(R.drawable.outline_logout_24, "Logout")
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

        val toolbar = (activity as? AppCompatActivity)?.supportActionBar

        //(activity as? AppCompatActivity)?.supportActionBar?.hide()
        // CUSTOM TOOLBAR VIEW
        //val profileToolbar = layoutInflater.inflate(R.layout.dynamic_toolbar, null)
        //toolbar?.customView = profileToolbar
        //toolbar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM


        val topMenu = binding.topMenu
        val centerMenu = binding.centerMenu
        val bottomMenu = binding.bottomMenu

        val topMenuAdapter = MenuAdapter(topMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
//            when (menuItem.title) {
//                "Logout" -> finish()
//            }
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT).show()
        }

        val centerMenuAdapter = MenuAdapter(centerMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT).show()
        }

        val bottomMenuAdapter = MenuAdapter(logoutItem) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT).show()
        }

        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

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

        //val mainNav = requireActivity().findNavController(R.id.fragmentContainerView2)
        //mainNav.navigate(R.id.action_toolbarIcon_to_toolbarTitle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
       // (activity as? AppCompatActivity)?.supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE
    }
}