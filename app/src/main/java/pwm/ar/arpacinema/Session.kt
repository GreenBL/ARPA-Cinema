package pwm.ar.arpacinema

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import pwm.ar.arpacinema.model.User

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("session")

/**
 * Implemented with *Jetpack DataStore* (might change to SharedPreferences idk)
 */

object Session {

    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private var _user: User? = null
    val user: User? get() = _user

    val userIdStr: String get() = _user?.id.toString()
    val userIdInt: Int? get() = _user?.id

    private var _userImageURL: String? = null
    val userImageURL: String?
        get() = _userImageURL


    val loggedIn : Boolean get() = _user != null

    fun setUserImageURL(url: String) {
        _userImageURL = url
    }

    // store the user ID [important: store it after logging in else it will be lost]
    suspend fun storeUser(context: Context, user: User) {
        val userId = user.id.toString()
        _user = user
        context.dataStore.edit { settings ->
            settings[USER_ID_KEY] = userId
        }
    }

    // get the stored user ID (if there is one kek, also it's a bad way to check if the user is logged)
    suspend fun getUserId(context: Context): String? {
        val preferences = context.dataStore.data.first()
        return preferences[USER_ID_KEY]
    }

    // invalidate the stored user ID (logout)
    suspend fun invalidateUser(context: Context) {
        _user = null
        context.dataStore.edit { settings ->
            settings.remove(USER_ID_KEY)
        }
    }


    // DEBUG: print the stored user ID to the console [CHECK LOGCAT!]
    suspend fun printUserId(context: Context) {
        val userId = getUserId(context)
        if (userId != null) {
            Log.d("Session", "Stored user ID: $userId")
        } else {
            Log.e("Session", "No user ID found")
        }
    }

    suspend fun saveUserImageURL(url: String) {
        _userImageURL = url
    }
}