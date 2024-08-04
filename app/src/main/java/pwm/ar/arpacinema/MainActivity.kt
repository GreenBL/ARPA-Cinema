package pwm.ar.arpacinema

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import net.glxn.qrgen.android.QRCode
import pwm.ar.arpacinema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageQR = binding.imageView

        val projectionID = 52612
        val seatIndex = 1

        val projectionIDHex = projectionID.toHexString(HexFormat.UpperCase)
        val ticketIDHex = seatIndex.toHexString(HexFormat.UpperCase)

        val ticketToken = StringBuilder(projectionIDHex + ticketIDHex)

        binding.imageView.setOnClickListener { _ ->
            Toast.makeText(this, ticketToken, Toast.LENGTH_SHORT).show()
        }


        val qrBitmap = QRCode.from(ticketToken.toString()).bitmap()

        Glide.with(this)
            .load(qrBitmap)
            .into(imageQR)

    }
}