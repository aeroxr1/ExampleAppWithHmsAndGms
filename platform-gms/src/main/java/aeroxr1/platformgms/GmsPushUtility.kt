package aeroxr1.platformgms

import android.content.Context
import android.util.Log
import aeroxr1.platform.PushUtility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.CancellationException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Gsm implementation of [PushUtility].
 */

class GmsPushUtility @Inject constructor(): PushUtility {

    override fun getId(context: Context, result: (String) -> Unit) {
        val executor: Executor = Executors.newSingleThreadExecutor()
        FirebaseInstallations.getInstance().id.addOnCompleteListener(executor) {
            result.invoke(it.result ?: "ERROR")
        }
    }

    override fun getIdSync(context: Context): String {
        return awaitTaskAllowOnMainThread(FirebaseInstallations.getInstance().id) ?: "ERROR"
    }

    override fun getToken(context: Context, result: (String) -> Unit, error: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                error("Fetching FCM registration token failed")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            result(token!!)
        })
    }

    companion object{
        const val TAG:String = "PushImp"
    }



    @Throws(InterruptedException::class)
    private fun <T> awaitTaskAllowOnMainThread(task: Task<T>): T? {
        val executor: Executor = Executors.newSingleThreadExecutor()
        val latch = CountDownLatch(1)
        task.addOnCompleteListener(executor) { latch.countDown() }
        latch.await(30000L, TimeUnit.MILLISECONDS)
        return getResultOrThrowException(task)
    }

    private fun <T> getResultOrThrowException(task: Task<T>): T? {
        return when {
            task.isSuccessful -> task.result
            task.isCanceled -> throw CancellationException("Task is already canceled")
            task.isComplete -> throw IllegalStateException(task.exception)
            else -> throw IllegalThreadStateException("Firebase Installations getId Task has timed out.")
        }
    }
}
