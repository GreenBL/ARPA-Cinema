package pwm.ar.arpacinema.tickets

import android.app.Dialog
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentCodeBinding
import pwm.ar.arpacinema.databinding.FragmentViewTicketBinding
import pwm.ar.arpacinema.discovery.FiltersFragment
import pwm.ar.arpacinema.rewards.inventory.CodeViewModel

private val style = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered

class ViewTicketFragment : DialogFragment() {

    private val binding: FragmentViewTicketBinding by lazy {
        FragmentViewTicketBinding.inflate(layoutInflater) // by lazy because we dont need to attach
    }

    private val args : ViewTicketFragmentArgs by navArgs()

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private val viewModel: ViewTicketViewModel by activityViewModels()

    private val scope = lifecycleScope

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val ticket = args.ticketDetails

        binding.movieTitled.text = ticket.movieTitle
        binding.datemovie.text = ticket.movieDate
        binding.timemovie.text = ticket.movieTime
        binding.theatermovie.text = ticket.movieTheater
        binding.row.transitionName = ticket.seatString

        val shimmer = binding.shimmer

        shimmer.startShimmer()

        scope.launch {
            val imageUri = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=LOCUSTE"


            Glide.with(this@ViewTicketFragment)
                .load(imageUri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.iView)

            binding.iView.post{
                shimmer.stopShimmer()
                shimmer.hideShimmer()
            }


        }


        return MaterialAlertDialogBuilder(requireContext(), style)
            .setView(binding.root)
            .setTitle("Il tuo biglietto")
            .setBackgroundInsetTop(0)
            .setIcon(R.drawable.round_receipt_24)
            .setPositiveButton("Chiudi") { _, _ ->
                dismiss()
            }
            .create()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}