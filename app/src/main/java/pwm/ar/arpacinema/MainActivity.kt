package pwm.ar.arpacinema

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.databinding.ActivityMainBinding
import pwm.ar.arpacinema.model.User


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            v.updatePadding(bottom = if (imeVisible) imeHeight else 0)
            Log.d("MainActivity", "${systemBars.bottom}")
            insets
        }


        val navigationBar = binding.bottomNavigationView

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // disable screen orientation [globally]
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // forcing dark theme, because who uses light theme?
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // TODO - login stuff
        val user = User(1, 1, "Riccardo", "Parisi", "riccardo@mail.it", 2, 452)
        lifecycleScope.launch {
            Session.storeUser(this@MainActivity, user)
        }
        lifecycleScope.launch {
            delay(1000L)
            Session.printUserId(this@MainActivity)
            if (Session.getUserId(this@MainActivity) == null) {
                Toast.makeText(this@MainActivity, "No user ID found", Toast.LENGTH_SHORT).show()
            }
        }

        // TODO TEST




    }

    fun hideBottomNavigation() {
        // hide bottom nav
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        // show bottom nav
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

}