package aeroxr1.platformhms

import aeroxr1.platform.PlatformMessagingService
import aeroxr1.platform.platform
import android.content.Intent
import android.util.Log
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.huawei.hms.push.SendException
import java.util.Arrays

class MyHmsMessageService : HmsMessageService() {

    private val platformMessagingService: PlatformMessagingService by lazy {
        platform().platformMessagingService
    }

    /**
     * When an app calls the getToken method to apply for a token from the server,
     * if the server does not return the token during current method calling, the server can return the token through this method later.
     * This method callback must be completed in 10 seconds. Otherwise, you need to start a new Job for callback processing.
     * @param token token
     */
    override fun onNewToken(token: String?) {
        Log.i(TAG, "received refresh token:$token")

        // send the token to your app server.
        if (token.isNullOrEmpty()) {
            val intent = Intent()
            intent.action = PUSH_ACTION
            intent.putExtra("method", "onNewToken")
            intent.putExtra("msg", "onNewToken called, token: $token")
            sendBroadcast(intent)
        } else {
            // This method callback must be completed in 10 seconds. Otherwise, you need to start a new Job for callback processing.
            refreshedTokenToServer(token)
            platformMessagingService.onNewToken(token)
        }
    }

    private fun refreshedTokenToServer(token: String) {
        Log.i(TAG, "sending token to server. token:$token")
        val intent = Intent()
        intent.action = PUSH_ACTION
        intent.putExtra("method", "refreshedTokenToServer")
        intent.putExtra("msg", "onNewToken called, token: $token")
        sendBroadcast(intent)
    }

    /**
     * This method is used to receive downstream data messages.
     * This method callback must be completed in 10 seconds. Otherwise, you need to start a new Job for callback processing.
     *
     * @param message RemoteMessage
     */
    override fun onMessageReceived(message: RemoteMessage?) {
        Log.i(TAG, "onMessageReceived")
        message?.let {
            platformMessagingService.onMessageReceived(it.dataOfMap)
        }
    }

    private fun startWorkManagerJob(message: RemoteMessage?) {
        Log.d(TAG, "Start new Job processing.")
    }

    private fun processWithin10s(message: RemoteMessage?) {
        Log.d(TAG, "Processing now.")
    }

    override fun onMessageSent(msgId: String?) {
        Log.i(TAG, "onMessageSent called, Message id:$msgId")
        val intent = Intent()
        intent.action = PUSH_ACTION
        intent.putExtra("method", "onMessageSent")
        intent.putExtra("msg", "onMessageSent called, Message id:$msgId")
        sendBroadcast(intent)
    }

    override fun onSendError(msgId: String?, exception: Exception?) {
        Log.i(
            TAG, "onSendError called, message id:$msgId, ErrCode:${(exception as SendException).errorCode}, " +
                    "description:${exception.message}"
        )
        val intent = Intent()
        intent.action = PUSH_ACTION
        intent.putExtra("method", "onSendError")
        intent.putExtra(
            "msg", "onSendError called, message id:$msgId, ErrCode:${exception.errorCode}, " +
                    "description:${exception.message}"
        )
        sendBroadcast(intent)
    }

    override fun onTokenError(e: Exception) {
        super.onTokenError(e)
    }

    companion object {
        private const val TAG: String = "PushDemoLog"
        private const val PUSH_ACTION: String = "pushaction"
    }
}
