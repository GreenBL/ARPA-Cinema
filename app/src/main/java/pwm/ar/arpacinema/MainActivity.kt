package pwm.ar.arpacinema

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.google.android.material.appbar.MaterialToolbar
import pwm.ar.arpacinema.databinding.ActivityMainBinding


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

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toolbarCustom = layoutInflater.inflate(R.layout.dynamic_toolbar, null)


        toolbar.setNavigationOnClickListener{
            Toast.makeText(this, "Navigation icon clicked", Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.apply {
            title = "Benvenuto!"
            subtitle = "Accedi o crea un account."
            customView = toolbarCustom
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
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
                   // val navController2 = findNavController(R.id.fragmentContainerViewToolbar)
                   //  navController2.navigate(R.id.action_toolbarIcon_to_toolbarTitle)
                    true
                }

                R.id.profile -> {
                    val navController = findNavController(R.id.fragmentContainerView)
                    navController.navigate(R.id.profileViewAction)
                    findNavController(R.id.fragmentContainerViewToolbar).navigate(R.id.action1)
                    true
                }

                else -> { false }
            }

        }

    }
}