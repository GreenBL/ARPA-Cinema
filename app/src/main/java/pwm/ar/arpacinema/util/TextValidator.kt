package pwm.ar.arpacinema.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

/**
 * Ritorna un [TextWatcher] personalizzato che valida l'input dell'utente in base alla funzione [validatorFunction] passata come parametro
 */

open class TextValidator(
    private val textInputLayout: TextInputLayout,
    private val validatorFunction: (String) -> String?
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
        if (errorMessage == null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
        else {
            textInputLayout.error = errorMessage
            textInputLayout.isErrorEnabled = true
        }

        // may have some uses
        if (s.toString().isEmpty()) {
            textInputLayout.error = "empty"
            textInputLayout.isErrorEnabled = false
        }


    }

    companion object {
        fun isValidEmail(email: String): String? {
            return if (email.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    null
                } else {
                    "L'e-mail inserita non è valida"
                }
            } else {
                null
            }
        }

        fun isValidPassword(password: String): String? {
            return when {
                password.isEmpty() -> null
                password.contains(" ") -> "La password non può contenere spazi"
                !password.matches(".*[A-Z].*".toRegex()) -> "La password deve contenere almeno una lettera maiuscola"
                !password.matches(".*[a-z].*".toRegex()) -> "La password deve contenere almeno una lettera minuscola"
                !password.matches(".*[@#!\$%^&+=].*".toRegex()) -> "La password deve contenere almeno un carattere speciale"
                !password.matches(".*[0-9].*".toRegex()) -> "La password deve contenere almeno un numero"
                password.length < 8 -> "La password deve essere lunga almeno 8 caratteri"
                else -> null
            }
        }

        fun isValidName(name: String): String? {
            return when {
                name.isEmpty() -> null
                name.contains("  ") -> "Nome non valido"
                !name.matches("^[a-zA-Z]+(([' ][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()) -> "Nome non valido"
                else -> null
            }
        }

        fun isValidPhone(phone: String): String? {
            return when {
                phone.isEmpty() -> null
                phone.contains(" ") -> "Il numero di telefono non può contenere spazi"
                !phone.matches("^[0-9]{10}\$".toRegex()) -> "Il numero di telefono deve essere di 10 cifre"
                else -> null
            }
        }

        fun silent(string: String): String? {
            return null
        }

        fun B_isValidEmail(email: String): Boolean {
            return if (email.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        fun B_isValidPassword(password: String): Boolean {
            return when {
                password.isEmpty() -> false
                password.contains(" ") -> false
                !password.matches(".*[A-Z].*".toRegex()) -> false
                !password.matches(".*[a-z].*".toRegex()) -> false
                !password.matches(".*[@#!\$%^&+=].*".toRegex()) -> false
                !password.matches(".*[0-9].*".toRegex()) -> false
                password.length < 8 -> false
                else -> true
            }
        }

        fun B_isValidPhone(phone: String): Boolean {
            return when {
                phone.isEmpty() -> false
                phone.contains(" ") -> false
                !phone.matches("^[0-9]{10}\$".toRegex()) -> false
                else -> true
            }
        }

        fun B_isValidName(name: String): Boolean {
            return when {
                name.isEmpty() -> false
                !name.matches("^[a-zA-Z]+(([' ][a-zA-Z ])?[a-zA-Z]*)*\$".toRegex()) -> false
                else -> true
            }
        }

    }
}