package pwm.ar.arpacinema.profile.account

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.MenuAdapter
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.MenuItem
import pwm.ar.arpacinema.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val accountMenuItems = listOf(
        MenuItem(R.drawable.outline_email_24, "Modifica e-mail"),
        MenuItem(R.drawable.outline_lock_24, "Modifica password"),
        MenuItem(R.drawable.outline_security_24, "Sicurezza")
    )

    private val otherItems = listOf(
        MenuItem(R.drawable.outline_help_outline_24, "Ho bisogno di aiuto", false),
        MenuItem(R.drawable.outline_delete_forever_24, "Cancella account", false)
    )


    companion object {
        fun newInstance() = AccountFragment()
    }

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val menu = binding.accountMenu
        val bottomMenu = binding.otherMenu
        val back = binding.include.navBack.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.include.viewTitle.text = "Account"
        setupMenus(menu, bottomMenu)
        viewModel.init()

    }

    private fun setupMenus(
        menu: RecyclerView,
        bottomMenu: RecyclerView
    ) {
        val menuAdapter = MenuAdapter(accountMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
            when (menuItem.label) {
                "Modifica e-mail" -> {
                    findNavController().navigate(R.id.action_accountFragment_to_editEmailFragment)
                }
                "Modifica password" -> {
                    findNavController().navigate(R.id.action_accountFragment_to_editPasswordFragment)
                }
                "Sicurezza" -> {
                    findNavController().navigate(R.id.action_accountFragment_to_securityFragment)
                }
            }
        }

        /*val bottomMenuAdapter = MenuAdapter(otherItems) { menuItem ->
            // handle the item being clicked
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT)
                .show()
        }*/
        val bottomMenuAdapter = MenuAdapter(otherItems) { menuItem ->
            when (menuItem.label) {
                "Cancella account" -> {
                    // Call the function to delete the user account
                    deleteUserAccount()
                }

                else -> {
                    // help thing
                    Toast.makeText(
                        requireContext(),
                        "Clicked: ${menuItem.label}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        menu.apply {
            adapter = menuAdapter
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun deleteUserAccount() {
        MaterialAlertDialogBuilder(requireContext(), com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
            .setTitle("Conferma cancellazione")
            .setMessage("Sei sicuro di voler cancellare il tuo account?")
            .setPositiveButton("Si") { _, _ ->
                Log.d("AccountFragment", "User confirmed account deletion")
                lifecycleScope.launch {
                    viewModel.deleteUserAccount(requireContext(), findNavController())
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

}