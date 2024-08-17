package pwm.ar.arpacinema.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.MainActivity
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
        MenuItem(R.drawable.outline_security_24, "Sicurezza"),
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
        (activity as MainActivity).hideBottomNavigation()
        val menu = binding.accountMenu
        val menuAdapter = MenuAdapter(accountMenuItems) { menuItem ->
            // HANDLE THE ITEM BEING CLICKED
            Toast.makeText(requireContext(), "Clicked: ${menuItem.label}", Toast.LENGTH_SHORT).show()
        }

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        menu.apply {
            adapter = menuAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}