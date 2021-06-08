package aeroxr1.platformhms

import android.content.Context
import android.text.TextUtils
import android.util.Log
import aeroxr1.platform.PushUtility
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException

/**
 * Hms implementation of [PushUtility].
 */
class HmsPushUtility: PushUtility {

    override fun getId(context: Context, result: (String) -> Unit) {
        val idResult = HmsInstanceId.getInstance(context).aaid
        idResult.addOnSuccessListener { aaidResult ->
            val aaId = aaidResult.id
            Log.i(TAG, "getAAID success:$aaId")
            result.invoke("getAAID success:$aaId")
        }.addOnFailureListener { e ->
            Log.e(TAG, "getAAID failed:$e")
            result.invoke("getAAID failed.$e")
        }
    }

    override fun getIdSync(context: Context): String {
        return HmsInstanceId.getInstance(context).id
    }

    override fun getToken(context: Context, result: (String) -> Unit, error: (String) -> Unit) {
        object : Thread() {
            override fun run() {
                try {
                    // read from agconnect-services.json
                    val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")
                    val token = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                    Log.i(TAG, "get token:$token")
                    if (!TextUtils.isEmpty(token)) {
                        result.invoke(token)
                    } else {
                        error.invoke("empty token")
                    }
                } catch (e: ApiException) {
                    Log.e(TAG, "get token failed, $e")
                    error.invoke("get token failed, $e")
                }
            }
        }.start()
    }

    companion object{
        const val TAG:String = "PushImp"
    }
}
