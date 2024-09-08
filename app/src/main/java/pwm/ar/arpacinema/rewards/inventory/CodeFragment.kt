package pwm.ar.arpacinema.rewards.inventory

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentCodeBinding
import pwm.ar.arpacinema.databinding.FragmentFiltersBinding
import pwm.ar.arpacinema.discovery.FiltersFragment
import pwm.ar.arpacinema.discovery.FiltersViewModel

private val style = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered

class CodeFragment : DialogFragment() {

    private val binding: FragmentCodeBinding by lazy {
        FragmentCodeBinding.inflate(layoutInflater) // by lazy because we dont need to attach
    }

    private val args : CodeFragmentArgs by navArgs()

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private val viewModel: CodeViewModel by activityViewModels()

    private val scope = lifecycleScope

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val rewardDetails = args.rewardDetails

        binding.rewardTitle.text = rewardDetails.rewardCategory
        binding.catText.text = rewardDetails.rewardOption

        val shimmer = binding.shimmer

        shimmer.startShimmer()

        scope.launch {
            val imageUri = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=LOCUSTE"


            Glide.with(this@CodeFragment)
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
            .setTitle("Il tuo premio")
            .setBackgroundInsetTop(0)
            .setIcon(R.drawable.round_receipt_24)
            .setPositiveButton("Chiudi") { _, _ ->
                dismiss()
            }
            .create()




    }
}