package pwm.ar.arpacinema.common

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pwm.ar.arpacinema.MainActivity
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentNetworkErrorBinding
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.Sentinel
import retrofit2.Response

class NetworkErrorFragment : Fragment() {

    private var _binding: FragmentNetworkErrorBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = NetworkErrorFragment()
    }

    private val viewModel: NetworkErrorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetworkErrorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val retryButton = binding.button2
        val spinner = binding.spinner
        val texty = binding.loading
        val intent = Intent(activity, MainActivity::class.java)
        val retrofit = RetrofitClient

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        retryButton.setOnClickListener {
            // show spinny and texty
            spinner.visibility = View.VISIBLE
            texty.visibility = View.VISIBLE
            retryButton.isEnabled = false
            // TODO
            // TODO
            // TODO
            var weConnected: Boolean = false
            lifecycleScope.launch {
                weConnected = retrofit.checkConnection()
                if (weConnected) {
                    RetrofitClient.interloper.networkStatus.postValue(Sentinel.NetStat.ONLINE)
                    (activity as MainActivity).finish()
                    startActivity(intent)

                } else {
                    Dialog.showNetworkErrorDialog(requireContext())
                }
                spinner.visibility = View.GONE
                texty.visibility = View.GONE
                retryButton.isEnabled = true
            }




        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}