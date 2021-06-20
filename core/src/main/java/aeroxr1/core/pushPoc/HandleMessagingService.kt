package aeroxr1.core.pushPoc

import aeroxr1.platform.PlatformMessagingService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class HandleMessagingService constructor(private val applicationContext:Context) : PlatformMessagingService {

    override fun onNewToken(token: String) {
        Log.d(TAG, "New Token: $token")
    }

    override fun onMessageReceived(data: Map<String, String>?) {

        data?.let{ data ->
            // Check if message contains a data payload.
            if (data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: ${data}")

                // do some stuff with core module's data and show notification

                showNotification()
            }

        }

    }

    private fun showNotification() {
        val NOTIFICATION_ID = Random().nextInt(1000)
        initChannels()

        val intent = Intent(applicationContext, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_ID, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setContentTitle("title")
            .setContentText("alert")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun initChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(NOTIFICATION_CHANNEL,
            "TEST",
            NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Admin portal notification"
        channel.setSound(null, null)
        notificationManager.createNotificationChannel(channel)

    }

    companion object {
        private const val PUSH_ACTION: String = "pushaction"
        private const val TAG = "HandleMessagingService"
        private val NOTIFICATION_CHANNEL = "AdminPortalNotification"
    }

}