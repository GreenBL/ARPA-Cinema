package pwm.ar.arpacinema.parcels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RewardDetailsParcel (
    val rewardId: String,
    val rewardCategory: String,
    val rewardOption: String
    ) : Parcelable
