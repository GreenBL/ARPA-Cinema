package pwm.ar.arpacinema

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pwm.ar.arpacinema.common.Dialog
import pwm.ar.arpacinema.databinding.ActivityMainBinding
import pwm.ar.arpacinema.model.User
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.Status

private const val DEBUG_MODE = false

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            v.updatePadding(bottom = if (imeVisible) imeHeight else 0)
            insets
        }

        val navController = getNavController()

        observeStatus(this, navController)



        val retrofit = RetrofitClient

        val navigationBar = binding.bottomNavigationView

        setupWindowDecorations()

        if(DEBUG_MODE) {
            val user = User(1, 1, "Riccardo", "Parisi", "riccardo@mail.it", "3334445566", 2, 3)
            lifecycleScope.launch {
                Session.storeUser(this@MainActivity, user)
            }
        }

        // SE NON C'E' CONNESSIONE AL SERVER O A INTERNET
        // todo
        runBlocking {
            delay(200L) // slight delay
            //retrofit.checkConnection()

            if(Session.user != null) {
                // hide bottom nav
                Log.i("MainActivity", "User FOUND")
                navigationBar.visibility = View.VISIBLE

            }
            setupBottomBarBehavior(navController)
        }


        // TODO - login stuff
        //val user = User(1, 1, "Riccardo", "Parisi", "riccardo@mail.it", 2, 452)
       // lifecycleScope.launch {
        //    Session.storeUser(this@MainActivity, user)
       // }
        lifecycleScope.launch {
            delay(1000L)
            Session.printUserId(this@MainActivity)
            if (Session.getUserId(this@MainActivity) == null) {
                Toast.makeText(this@MainActivity, "No user ID found", Toast.LENGTH_SHORT).show()
            }
        }

        // TODO TEST




    }

    private fun setupWindowDecorations() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // disable screen orientation [globally]
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // forcing dark theme, because who uses light theme?
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        return navController
    }

    private fun setupBottomBarBehavior(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(Session.user == null) {
                // cause we dont want the bar appearing again
                hideBottomNavigation()
                return@addOnDestinationChangedListener
            }
            val handler = Handler(Looper.getMainLooper())
            handler.post{ // avoid calling it before the fragment is created and shown
                when (destination.id) {
                    R.id.homeFragment -> {
                        showBottomNavigation()
                    }

                    R.id.profileMenuFragment -> {
                        showBottomNavigation()
                    }

                    R.id.ticketsFragment -> {
                        showBottomNavigation()
                    }

                    else -> {
                        hideBottomNavigation()
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RetrofitClient.interloper.status.removeObservers(this)
    }

    fun hideBottomNavigation() {
        // hide bottom nav
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        // show bottom nav
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun observeStatus(owner: LifecycleOwner, navController: NavController) {
        val interloper = RetrofitClient.interloper
        // check if there is already an observer
        interloper.status.observe(owner) { status ->
            if(status) {
                Log.i("MainActivity", "Status: $status")
            } else {
                Dialog.showNetworkErrorDialog(this)
                Log.e("MainActivity", "Status: $status")
            }
        }
        interloper.globalStatus.observe(owner) { globalStatus ->
            if(!globalStatus) {
                Log.e("MainActivity", "Connection lost, Status: $globalStatus")
                interloper.globalStatus.removeObservers(owner)
                interloper.status.removeObservers(owner)// important if we are restarting
                navController.navigate(R.id.action_global_networkErrorFragment)
            }
        }
    }



}