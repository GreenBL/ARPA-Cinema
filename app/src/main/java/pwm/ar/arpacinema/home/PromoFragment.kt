package pwm.ar.arpacinema.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.databinding.FragmentPromoBinding
import pwm.ar.arpacinema.util.PlaceholderDrawable

class PromoFragment : Fragment() {

    private var _binding : FragmentPromoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PromoFragment()
    }

    private val args: PromoFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNav()
        val promo = args.promo
        binding.promo = promo

        Glide.with(this)
            .load(promo.imageUri)
            .placeholder(PlaceholderDrawable.getPlaceholderDrawable())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageView16)
    }

    private fun setupNav() {
        binding.inclusion.viewTitle.text = "Promozione"
        binding.inclusion.navBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}