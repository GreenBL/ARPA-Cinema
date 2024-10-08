package pwm.ar.arpacinema.common

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.text.HtmlCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.R
import pwm.ar.arpacinema.model.Reward


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
        <p>Hai effettuato l'acquisto con successo e hai guadagnato <span style="color: #FFCC66"><b>${points} Stars</b></span>.</p>
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
        <p>Hai riscattato un <span style="color: #FFCC66">biglietto gratuito!</span></b></p>
        <p><i>Continua a fare acquisti per guadagnare ancora più punti e ottenere ancora più sconti!</i></p>
        """.trimIndent()

        builder.setMessage(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY))

        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()

    }

    fun showLevelUpRationale(context: Context, level : Int, onClickConfirm: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Salire di livello?")

        val htmlMessage = """
        <p>Hai raggiunto i punti necessari per passare al <span style="color: #FFCC66"><b>livello ${level + 1}</span>!</b> </p>
        <p>Se scegli di passare al prossimo livello <span style="color: #FF3333"><b>i tuoi punti saranno azzerati.</b></span></p>
        <p>Ma non temere! Aumentando di livello godrai di un guadagno di <span><em>Stars</em></span> maggiorato!</b></p>
        </b>Vuoi continuare?</b>
        """

        builder.setMessage(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY))
        builder.setIcon(R.drawable.star_half_glow)
        builder.setPositiveButton("Si") {
                dialog, _ ->
            onClickConfirm()
            dialog.dismiss()
            }
        builder.setNegativeButton("No") {
                dialog, _ -> dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    fun showCreditIncreasedDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Saldo ricaricato")
        builder.setMessage("Il tuo credito è stato ricaricato con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

    }

    fun show2FAuthenticationConfirmation(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Domanda impostata")
        builder.setMessage("La tua domanda di sicurezza è stata impostata con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showEditPasswordDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Password modificata")
        builder.setMessage("La tua password è stata modificata con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showEditEmailDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("E-mail modificata")
        builder.setMessage("La tua e-mail è stata modificata con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") {
                dialog, _ -> dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun showMaxPointsDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Acquisto effettuato")

        val htmlMessage = """
        <p>Hai effettuato l'acquisto con successo. Ti avvisiamo che hai raggiunto il tetto massimo di <span style="color: #FFCC66"><b>1000 Stars</b></span>.</p>
        <i>Per continuare a guadagnare punti sali di livello o riscatta ricompense!</i>
        """.trimIndent()

        builder.setMessage(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY))

        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }

    fun showRedeemRationaleDialog(context: Context, reward: Reward, onClickConfirm: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Riscattare ${reward.category}?")

        val message = SpannableString("${reward.size}\n\n${reward.points} Stars\n\nConfermi di voler riscattare questo premio?")

        message.setSpan(
            RelativeSizeSpan(1.3f),
            0, reward.description.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val optionStartIndex = 0
        val optionEndIndex = optionStartIndex + reward.size.length

        val costStartIndex = reward.size.length + 2
        val costEndIndex = costStartIndex + reward.points.toString().length + 6

        message.setSpan(
            RelativeSizeSpan(1.25f),
            optionStartIndex, optionEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        message.setSpan(
            ForegroundColorSpan(Color.parseColor("#FFCC66")),
            costStartIndex, costEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        message.setSpan(
            RelativeSizeSpan(2f),
            costStartIndex, costEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.setMessage(message)
        builder.setIcon(R.drawable.baseline_redeem_24)
        builder.setPositiveButton("Si") { dialog, _ ->
            onClickConfirm()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }

    fun showNotEnoughPointsDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Saldo insufficiente")
        builder.setMessage("Non hai abbastanza punti per riscattare questo premio.")
        builder.setIcon(R.drawable.baseline_redeem_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    fun showRedeemSuccessDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context, centered)
        builder.setTitle("Riscatto effettuato")
        builder.setMessage("Il premio è stato riscatto con successo.")
        builder.setIcon(R.drawable.baseline_check_circle_outline_24)
        builder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }
}