package pwm.ar.arpacinema

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import pwm.ar.arpacinema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val menuItems = listOf(
        MenuItem(R.drawable.round_chevron_right_24, "Premi"),
        MenuItem(R.drawable.round_chevron_right_24, "Storico Acquisti"),
        MenuItem(R.drawable.round_chevron_right_24, "Roba"),
        MenuItem(R.drawable.round_chevron_right_24, "XL (500g)"),
        MenuItem(R.drawable.round_chevron_right_24, "L (350g)"),
        // Add more menu items here
    )
    private val menuItemLogout = listOf(MenuItem(R.drawable.round_chevron_right_24, "Logout"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val adapter = MenuAdapter(menuItems) { menuItem ->
            // Handle item click here
//            when (menuItem.title) {
//                "Logout" -> finish()
//            }
            Toast.makeText(this, "Clicked: ${menuItem.title}", Toast.LENGTH_SHORT).show()
        }
        val dividerItemDecoration = MaterialDividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val adapter2 = MenuAdapter(menuItemLogout) { menuItem ->
            // Handle item click here
            when (menuItem.title) {
                "Logout" -> finish()
            }
            Toast.makeText(this, "Clicked: ${menuItem.title}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView2.addItemDecoration(dividerItemDecoration)
        binding.recyclerView2.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = adapter2
    }
}
