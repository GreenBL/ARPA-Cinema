package pwm.ar.arpacinema

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
import androidx.core.view.get
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
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.RetrofitClient
import pwm.ar.arpacinema.repository.Sentinel.NetStat.OFFLINE

private const val DEBUG_MODE = false

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        setupWindowDecorations()
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
        navigationBar.visibility = View.VISIBLE

        makeItemsVisible()



        if(DEBUG_MODE) {
            val user = User(1, 1, "Riccardo", "Parisi", "riccardo@mail.it", "3334445566", 2, 3)
            lifecycleScope.launch {
                Session.storeUser(this@MainActivity, user)
            }
        }

        // SE NON C'E' CONNESSIONE AL SERVER O A INTERNET
        // todo

        setupBottomBarBehavior(navController)

        runBlocking {
            retrofit.checkConnection()
            if (Session.getUserId(this@MainActivity) == null) {
                // hide bottom nav
                Log.i("MainActivity", "User NOT FOUND, SKIPPING autoLOGIN")

                return@runBlocking
            }

            Log.i("MainActivity", "User FOUND, autoLOGIN in progress with ID: ${Session.getUserId(this@MainActivity)}")

            makeItemsVisible()
            //navigationBar.visibility = View.VISIBLE
            //autologin // TODO API REQUEST!!!!
            //Session.storeUser(this@MainActivity, User(2, 1, "Riccardo", "Parisi", "riccardo@mail.it", "3334445566", 2, 3))

            try {
                val userId = Session.getUserId(this@MainActivity)!!
                val response = retrofit.service.autoLogin(DTO.IdPost(userId))

                if (response.isSuccessful) {

                    if (response.body()?.status != DTO.Stat.SUCCESS) {
                        throw Exception("Autologin failed, status: ${response.body()?.status}")
                    }
                    val user = response.body()?.user
                    if (user != null) {
                        // dato che il server non invia la risposta che volevo...
                        val autoUser = User(
                            userId.toInt(),
                            0, // MANCA IMMAGINE
                            user.name,
                            user.surname,
                            user.email,
                            user.phone,
                            0, // MANCA LIVELLO
                            0 // MANCANO I PUNTI
                        )
                        Session.storeUser(this@MainActivity, autoUser)
                        // print details

                    }
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}", e)
                Session.invalidateUser(this@MainActivity)
                makeItemsInvisible()
            }

            delay(200L) // slight delay
        }

        lifecycleScope.launch {
            delay(1000L)
            Session.printUserId(this@MainActivity)
            if (Session.getUserId(this@MainActivity) == null) {
                Toast.makeText(this@MainActivity, "No user ID found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun makeItemsVisible() {
        binding.loggedoutnavigation.visibility = View.GONE
//        binding.bottomNavigationView.menu.getItem(1).isVisible = true
//        binding.bottomNavigationView.menu.getItem(2).isVisible = true

    }

    private fun makeItemsInvisible() {
        binding.loggedoutnavigation.visibility = View.VISIBLE
//        binding.bottomNavigationView.menu.getItem(1).isVisible = false
//        binding.bottomNavigationView.menu.getItem(2).isVisible = false
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
            Log.d("MainActivity", "Destination changed: ${destination.label}")


            val handler = Handler(Looper.getMainLooper())
            handler.post{ // avoid calling it before the fragment is created and shown
                if(Session.user == null) {
                    // cause we dont want the bar appearing again
                    hideBottomNavigation()

                    //return@addOnDestinationChangedListener
                } else {
                    showBottomNavigation()
                }

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
                    R.id.viewTicketFragment -> {
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
        RetrofitClient.interloper.networkStatus.removeObservers(this)
    }

    fun hideBottomNavigation() {
        // hide bottom nav with animation
            binding.loggedoutnavigation.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        // show bottom nav
        if (Session.user == null) {
            binding.bottomNavigationView.visibility = View.INVISIBLE
            binding.loggedoutnavigation.visibility = View.VISIBLE
        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }

    private fun observeStatus(owner: LifecycleOwner, navController: NavController) {
        val interloper = RetrofitClient.interloper
        // check if there is already an observer
//        interloper.status.observe(owner) { status ->
//            if(status) {
//                Log.i("MainActivity", "Status: $status")
//            } else {
//                Dialog.showNetworkErrorDialog(this)
//                Log.e("MainActivity", "Status: $status")
//            }
//        }
        interloper.networkStatus.observe(owner) { netStatus ->
            if(netStatus == OFFLINE) {
                Log.e("MainActivity", "Connection lost, Status: $netStatus")
                //interloper.globalStatus.removeObservers(owner)
                interloper.networkStatus.removeObservers(owner)// important if we are restarting
                navController.navigate(R.id.action_global_networkErrorFragment)
                //Dialog.showNetworkErrorDialog(this)
            }
        }
    }



}