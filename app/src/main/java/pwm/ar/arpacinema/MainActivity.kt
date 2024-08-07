package pwm.ar.arpacinema

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import pwm.ar.arpacinema.databinding.ActivityMainBinding
import pwm.ar.arpacinema.util.AndroidBug5497Workaround


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        AndroidBug5497Workaround.assistActivity(this)


        // disable screen orientation [globally]
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


        //supportActionBar?.customView = customToolbar
       // supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
        //supportActionBar?.setDisplayShowCustomEnabled(true)

        supportActionBar?.apply {
            title = ""
            // TODO
        }

        // TODO - login stuff

        //binding.bottomNavigationView.visibility = View.GONE

        val navigationBar = binding.bottomNavigationView

        navigationBar.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    val navController = findNavController(R.id.fragmentContainerView)
                    navController.navigate(R.id.homeFromProfile)
                    true
                }

                R.id.profile -> {
                    val navController = findNavController(R.id.fragmentContainerView)
                    navController.navigate(R.id.profileViewAction)
                    true
                }

                else -> { false }
            }

        }

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//
//            val currentDestinationId = navController.currentDestination?.id ?: return@addOnDestinationChangedListener
//            val navHostAppBar = supportFragmentManager.findFragmentById(R.id.appbarContainer) as? NavHostFragment
//            val appBarNavController = navHostAppBar?.navController
//
//            if (appBarNavController == null) {
//                Log.e("NavigationError", "appBarNavController is null. Check if the appbarContainer is a NavHostFragment.")
//                return@addOnDestinationChangedListener
//            }
//
//            Log.d("NavigationDebug", "Current destination: $currentDestinationId, New destination: ${destination.id}")
//
//            // Prevent redundant navigations
//            when (destination.id) {
//                R.id.homeFragment -> {
//                    if (currentDestinationId != R.id.homeAppBar && activityInitialized) {
//                        Log.d("NavigationDebug", "Navigating to homeAppBar")
//                        appBarNavController.navigate(R.id.action_blankAppBar_to_homeAppBar)
//                    }
//                    activityInitialized = true
//                }
//                R.id.profileMenuFragment -> {
//                    if (currentDestinationId != R.id.blankAppBar) {
//                        Log.d("NavigationDebug", "Navigating to blankAppBar")
//                        appBarNavController.navigate(R.id.action_homeAppBar_to_blankAppBar)
//                    }
//                }
//            }
    //       }
    }
}