package aeroxr1.pocHybridHmsGms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aeroxr1.pocHybridHmsGms.databinding.ActivityMainBinding
import aeroxr1.core.mapsPoc.MapsActivity
import aeroxr1.core.pushPoc.PushActivity
import aeroxr1.core.qrCodePoc.QrCodeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.pushPoc.setOnClickListener {
            launchPushPoc()
        }

        binding.MapsPoc.setOnClickListener {
            launchMapsPoc()
        }

        binding.qrCodePoc.setOnClickListener {
            launchQrCodePoc()
        }

        "App: ${PocHelpers().getAppName()}".also { binding.appName.text = it }
    }

    private fun launchPushPoc(){
        val intent = Intent(this, PushActivity::class.java)
        startActivity(intent)
    }

    private fun launchMapsPoc(){
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun launchQrCodePoc(){
        val intent = Intent(this, QrCodeActivity::class.java)
        startActivity(intent)
    }

}