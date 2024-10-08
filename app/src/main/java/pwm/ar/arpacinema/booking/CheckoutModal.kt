package pwm.ar.arpacinema.booking


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.ModalCheckoutBinding
import pwm.ar.arpacinema.dev.Selection
import pwm.ar.arpacinema.model.TransactionType
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.util.PlaceholderDrawable
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val STANDARD_PRICE = 8.0

class CheckoutModal : BottomSheetDialogFragment() {

    private var _binding: ModalCheckoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalCheckoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "checkout_modal"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        val buyButton = binding.buy

        val selections = viewModel.selectedSeats.value
        val image = binding.imageView3
        val imageURI = viewModel.movie.value?.posterUrl
        if (imageURI != null) {
            Glide.with(this)
                .load(imageURI)
                .placeholder(PlaceholderDrawable.getPlaceholderDrawable())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.outline_cloud_off_24)
                .into(image)
        }

        val bonusDescriptor = binding.bonusCompat
        val recycler = binding.recyclerView
        val totalStarPoints = binding.totalStars
        val total = binding.total
        val userLevel = viewModel.level.value
        val seatsCountString = binding.dynamicPosts

        val movie = viewModel.movie.value

        binding.textView18.text = movie?.title
        binding.dataIco.text = viewModel.selectedDate.value?.format(
            DateTimeFormatter.ofPattern(
                "dd MMMM",
                Locale.ITALIAN
            )
        )
        binding.timeoIco.text = viewModel.selectedTime.value?.formattedTime
        binding.saleIco.text = viewModel.selectedTime.value?.formattedAuditorium

        val discountChip = binding.discountedChip
        val freeChip = binding.freeChip
        bonusDescriptor.tooltipText =
            "È possibile applicare un solo tipo di sconto per ogni transazione."
        bonusDescriptor.setOnClickListener {
            it.performLongClick()
        }

        seatsCountString.text =
            if (selections?.size!! > 1) "${selections.size} Posti Selezionati" else "Posto Selezionato"

        var totaleCalc = 0.0
        lifecycleScope.launch {
            viewModel.fetchRewardCounts()
            totaleCalc = selections.size.times(STANDARD_PRICE)
            val string = String.format(Locale.ITALY, "%.2f", totaleCalc)
            total.text = string
            val totalStars = totaleCalc.times(10).plus(viewModel.level.value!!).toInt()
            totalStarPoints.text = totalStars.toString()

        }

        viewModel.free.observe(viewLifecycleOwner) {
            freeChip.isEnabled = it
        }

        viewModel.discounts.observe(viewLifecycleOwner) {
            discountChip.isEnabled = it
        }

        freeChip.setOnClickListener {
            if (freeChip.isChecked) {
                viewModel.applyFree()
            } else {
                viewModel.removeRewards()
            }
        }

        discountChip.setOnClickListener {
            if (discountChip.isChecked) {
                viewModel.applyDiscount()
            } else {
                viewModel.removeRewards()
            }
        }

        buyButton.setOnClickListener {
            viewModel.buyTickets()
            //dismiss()
            //findNavController().popBackStack(R.id.bookingFragment, true)
            //Dialog.showPurchaseSuccessDialog(requireContext(), 10) // temp
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                DTO.Stat.NETWORK_ERROR -> {
                    Dialog.showNetworkErrorDialog(requireContext())
                    viewModel.status.postValue(DTO.Stat.DEFAULT)
                }

                DTO.Stat.PURCHASE_COMPLETE -> {
                    dismiss()
                    findNavController().popBackStack(R.id.bookingFragment, true)
                    if (viewModel.transactionType.value == TransactionType.FREE) {
                        Dialog.showPurchaseSuccessDialogFree(requireContext()) // temp
                    } else {
                        if (viewModel.serverResponse.value?.newPoints == 1000) {
                            Dialog.showMaxPointsDialog(requireContext())
                        } else {
                            Dialog.showPurchaseSuccessDialog(
                                requireContext(),
                                viewModel.serverResponse.value?.pointsEarned!!
                            )
                        }
                    }

                    viewModel.status.postValue(DTO.Stat.DEFAULT)
                }

                DTO.Stat.PURCHASE_FAIL -> {
                    Dialog.showPurchaseFailDialog(requireContext())
                    viewModel.status.postValue(DTO.Stat.DEFAULT)
                }

                else -> {}
            }
        }

        viewModel.transactionType.observe(viewLifecycleOwner) {
            val level = viewModel.level.value!! // points = euros x 10 + level
            when (it!!) {
                TransactionType.FREE -> {
                    // remove 8 euros
                    Log.d("CheckoutModal", "Free selected")
                    val new = totaleCalc.minus(8)
                    val string = String.format(Locale.ITALY, "%.2f", new)
                    val totalStars = new.times(10).plus(level).toInt()
                    totalStarPoints.text = totalStars.toString()
                    total.text = string
                    buyButton.text = "Riscatta Biglietto Gratuito"
                }

                TransactionType.DISCOUNT -> {
                    Log.d("CheckoutModal", "Discount selected")
                    val new = totaleCalc.minus(4)
                    val string = String.format(Locale.ITALY, "%.2f", new)
                    val totalStars = new.times(10).plus(level).toInt()
                    totalStarPoints.text = totalStars.toString()
                    total.text = string
                    buyButton.text = "Conferma e Paga"
                }

                TransactionType.STANDARD -> {
                    Log.d("CheckoutModal", "Standard selected")
                    val string = String.format(Locale.ITALY, "%.2f", totaleCalc)
                    total.text = string
                    val totalStars = totaleCalc.times(10).plus(level).toInt()
                    totalStarPoints.text = totalStars.toString()
                    buyButton.text = "Conferma e Paga"
                }
            }
        }


        //recycler.adapter = CheckoutAdapter(viewModel.selections)

        if (selections.isNullOrEmpty()) {
            buyButton.isEnabled = false
            throw Exception("selections is null or empty, which should not happen!!!")
        }


        val adapter = CheckoutAdapter(selections)

        recycler.adapter = adapter

        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


    }

    override fun onStart() {
        super.onStart()

        val bottomSheet =
            dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 400
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}