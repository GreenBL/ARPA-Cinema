package pwm.ar.arpacinema.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

/**
 * Ritorna un [TextWatcher] personalizzato che valida l'input dell'utente in base alla funzione [validatorFunction] passata come parametro
 */

open class TextValidator(
    private val textInputLayout: TextInputLayout,
    private val validatorFunction: (String) -> String
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        // DO NOTHING
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // DO NOTHING
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // take the input and validate it
        val errorMessage = validatorFunction(s.toString())
        textInputLayout.error = errorMessage.ifEmpty { null }
    }

    companion object {
        fun isValidEmail(email: String): String {
            return if (email.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ""
                } else {
                    "L'E-mail non è valida"
                }
            } else {
                ""
            }
        }

        fun isValidPassword(password: String): String {
            return when {
                password.isEmpty() -> ""
                password.length < 8 -> "La password deve essere lunga almeno 8 caratteri"
                !password.matches(".*[A-Z].*".toRegex()) -> "La password deve contenere almeno una lettera maiuscola"
                !password.matches(".*[a-z].*".toRegex()) -> "La password deve contenere almeno una lettera minuscola"
                !password.matches(".*[@#!\$%^&+=].*".toRegex()) -> "La password deve contenere almeno un carattere speciale"
                !password.matches(".*[0-9].*".toRegex()) -> "La password deve contenere almeno un numero"
                else -> ""
            }
        }
    }
}