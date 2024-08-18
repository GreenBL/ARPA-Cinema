package pwm.ar.arpacinema.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pwm.ar.arpacinema.R

object Dialog {

    fun showErrorDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
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
        val builder = MaterialAlertDialogBuilder(context, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
        builder.setTitle("Impossibile connettersi")
        builder.setMessage("Impossibile connettersi al server.")
        builder.setIcon(R.drawable.baseline_error_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }
}