package aeroxr1.core.pushPoc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import aeroxr1.platform.PushUtility
import aeroxr1.core.databinding.ActivityPushBinding

class PushActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushBinding
    private lateinit var pushUtility: PushUtility // DI service should get you the right push utility impl.
    private var receiver: MyReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.getToken.setOnClickListener {
            getToken()
        }
        binding.GetId.setOnClickListener {
            getId()
        }
        binding.getIdSync.setOnClickListener {
            getIdSync()
        }

        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(PUSH_ACTION)
        registerReceiver(receiver, filter)

    }

    /**
     * MyReceiver
     */
    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            if (bundle?.getString("msg") != null) {
                val content = bundle.getString("msg")
                showLog(content)
            }
        }
    }

    private fun getId() {
        showLog("getId:begin")
        pushUtility.getId(this) {
            showLog(it)
        }
    }

    private fun getIdSync() {
        val idResult = pushUtility.getIdSync(this)
        showLog("getIdSync success:$idResult")
    }

    private fun getToken() {
        showLog("getToken:begin")
        pushUtility.getToken(this, {
            sendRegTokenToServer(it)
        }, {
            showLog(it)
        })
    }

    private fun sendRegTokenToServer(token: String?) {
        Log.i(TAG, "sending token to server. token:$token")
        showLog("get token:$token")
    }

    fun showLog(log: String?) {
        runOnUiThread {
            log?.let {
                binding.blackBoard.text = it
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    companion object {
        private const val TAG: String = "PushDemoLog"
        private const val PUSH_ACTION: String = "pushaction"
    }
}
