package pwm.ar.arpacinema.profile

import android.app.ActionBar
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentHomeBinding
import pwm.ar.arpacinema.databinding.FragmentProfileMenuBinding
import pwm.ar.arpacinema.model.User

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
        //if (binding == null) {
            Log.d(
                "Nah", "Nope!"
            )
        //}
        //binding.lifecycleOwner = viewLifecycleOwner
        //binding.viewModel = viewModel

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

        val profileImage : ImageView = binding.profileImg
        Glide.with(this)
            .load(viewModel.loggedUser.value?.profileImageUri)
            .into(profileImage)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val toolbar = (activity as? AppCompatActivity)?.supportActionBar



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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //(activity as? AppCompatActivity)?.supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE
    }
}