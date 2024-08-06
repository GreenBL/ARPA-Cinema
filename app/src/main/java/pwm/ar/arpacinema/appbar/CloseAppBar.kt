package pwm.ar.arpacinema.appbar

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pwm.ar.arpacinema.R

class CloseAppBar : Fragment() {

    companion object {
        fun newInstance() = CloseAppBar()
    }

    private val viewModel: CloseAppBarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_close_app_bar, container, false)
    }
}