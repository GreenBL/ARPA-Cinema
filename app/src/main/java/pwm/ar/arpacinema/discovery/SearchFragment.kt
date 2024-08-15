package pwm.ar.arpacinema.discovery

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransform

import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentAuthBinding
import pwm.ar.arpacinema.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 300L
            isElevationShadowEnabled = true
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        this.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 600L
            isElevationShadowEnabled = true
            scrimColor = Color.TRANSPARENT
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(
            R.id.bottomNavigationView
        ).visibility = View.GONE

        val searchBarField = binding.searchBarLayout

        searchBarField.setStartIconOnClickListener {
            val windowInsetsController = view.windowInsetsController
            windowInsetsController?.hide(WindowInsets.Type.ime())
            findNavController().popBackStack()
        }

        // TODO: only if u are from home fragment

        searchBarField.postDelayed({
            val windowInsetsController = view.windowInsetsController
            windowInsetsController?.show(WindowInsets.Type.ime())
        }, 200)

        binding.root.setOnApplyWindowInsetsListener { v, insets ->
            val imeVisible = insets.isVisible(WindowInsets.Type.ime())
            val params = binding.searchBarLayout.layoutParams as? AppBarLayout.LayoutParams
            if (params != null) {
                if (imeVisible) {
                    params.scrollFlags = 0
                } else {
                    params.scrollFlags = (
                            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS or
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                            )
                    searchBarField.clearFocus()
                }
                binding.searchBarLayout.layoutParams = params
            }
            v.onApplyWindowInsets(insets)
        }


    }
}