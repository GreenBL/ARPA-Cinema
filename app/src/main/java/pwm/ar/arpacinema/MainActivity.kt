package pwm.ar.arpacinema

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pwm.ar.arpacinema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val menuItems = listOf(
        MenuItem(R.drawable.round_chevron_right_24, "Menu Item 1"),
        MenuItem(R.drawable.round_chevron_right_24, "Menu Item 2"),
        MenuItem(R.drawable.round_chevron_right_24, "Menu Item 3")
        // Add more menu items here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MenuAdapter(menuItems) { menuItem ->
            // Handle item click here
            Toast.makeText(this, "Clicked: ${menuItem.title}", Toast.LENGTH_SHORT).show()
        }
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
