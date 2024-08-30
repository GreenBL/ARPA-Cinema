package pwm.ar.arpacinema.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R



object Dialog {

    private val centered = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
    private val alert = com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog

    fun showErrorDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Errore")
        builder.setMessage("Si è verificato un errore imprevisto. Riprova più tardi.")
        builder.setIcon(R.drawable.baseline_error_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    fun showNetworkErrorDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Impossibile connettersi")
        builder.setMessage("Impossibile connettersi al server.")
        builder.setIcon(R.drawable.baseline_error_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    fun showSignupSuccessDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Account creato")
        builder.setMessage("Il tuo account è stato creato con successo ed è pronto all'uso.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun showLogoutConfirmationDialog(context: Context, onClickConfirm: suspend () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context, alert)
        builder.setTitle("Logout")
        builder.setMessage("Sei sicuro di voler effettuare il logout?")
        builder.setIcon(R.drawable.round_logout_24)
        builder.setPositiveButton("Si") {
                dialog, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                onClickConfirm()
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("No") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    fun showFilterDialog(context: Context, onClickConfirm: (String) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context, alert)
        builder.setTitle("Filtri e ordinamento")
        builder.setIcon(R.drawable.round_filter_alt_24)
        builder.setView(R.layout.dialog_filter)
        builder.setPositiveButton("Applica") {
                dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("Annulla") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}