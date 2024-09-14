package pwm.ar.arpacinema.common

import android.content.Context
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
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

    fun showUserNotFound(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Utente non trovato")
        builder.setMessage("L'email inserita non è registrata nel nostro database. Controlla l'email e riprova.")
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

    fun showEditedPasswordSuccessDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Password modificata")
        builder.setMessage("La password è stata modificata con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showPurchaseSuccessDialog(context: Context, points: Int) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Acquisto effettuato")

        val htmlMessage = """
        <p>Hai effettuato l'acquisto con successo e hai guadagnato <span style="color: yellow"><b>${points} stars</b></span>.</p>
        <p><i>Continua a fare acquisti per guadagnare ancora più punti!</i></p>
        """.trimIndent()

        builder.setMessage(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY))

        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }

    fun showImpossibleRecoveryDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Impossibile recuperare la password")
        builder.setMessage(
            HtmlCompat.fromHtml(
            "Il tuo account non ha una domanda di sicurezza impostata, contatta il supporto tecnico all'e-mail <i>supporto@arpa.it</i>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ))
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

    fun showHelpDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Supporto")
        builder.setMessage("Per maggiori informazioni contatta il supporto tecnico all'indirizzo supporto@arpacinema.it")
        builder.setIcon(R.drawable.outline_help_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showLoginDialog(requireContext: Context, onClickLogin: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(requireContext, centered)
        builder.setTitle("Accedi per continuare")
        builder.setMessage("Vuoi acquistare i biglietti per questo spettacolo? Per prima cosa devi effettuare il login.")
        builder.setIcon(R.drawable.round_login_24)
        builder.setPositiveButton("Accedi") { dialog, _ ->
            onClickLogin()
            dialog.dismiss()
        }
        builder.setNegativeButton("Annulla") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()

    }

    fun showPurchaseFailDialog(requireContext: Context) {
        val builder = MaterialAlertDialogBuilder(requireContext, centered)
        builder.setTitle("Saldo insufficiente")
        builder.setMessage("Non hai abbastanza credito per effettuare l'acquisto. Ricarica il tuo saldo.")
        builder.setIcon(R.drawable.round_euro_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()



    }

    fun showPurchaseSuccessDialogFree(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Biglietto riscattato")

        val htmlMessage = """
        <p>Hai riscattato un <span style="color: yellow">biglietto gratuito!</span></b></p>
        <p><i>Continua a fare acquisti per guadagnare ancora più punti e ottenere ancora più sconti!</i></p>
        """.trimIndent()

        builder.setMessage(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY))

        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()

    }
}