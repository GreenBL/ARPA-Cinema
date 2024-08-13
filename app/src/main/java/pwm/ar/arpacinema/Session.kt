package pwm.ar.arpacinema

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import pwm.ar.arpacinema.model.User

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session")

object Session {

    private val USER_KEY = stringPreferencesKey("user")
    private val gson = Gson()

    // store the user [important: store it after logging in else it will be lost]
    suspend fun storeUser(context: Context, user: User) {
        val userJson = gson.toJson(user)
        context.dataStore.edit { settings ->
            settings[USER_KEY] = userJson
        }
    }

    // get the stored user (if there is one kek, also its a good way to check if the user is logged)
    suspend fun getUser(context: Context): User? {
        val preferences = context.dataStore.data.first()
        val userJson = preferences[USER_KEY]
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    // invalidate the stored user (logout)
    suspend fun invalidateUser(context: Context) {
        context.dataStore.edit { settings ->
            settings.remove(USER_KEY)
        }
    }

    // DEBUG: print the stored user to the console [CHECK LOGCAT!]
    suspend fun printUser(context: Context) {
        val user = getUser(context)
        if (user != null) {
            Log.d("Session", "Stored user: ${user.id}, ${user.name} ${user.surname}")
        } else {
            Log.e("Session", "No user found")
        }
    }

}