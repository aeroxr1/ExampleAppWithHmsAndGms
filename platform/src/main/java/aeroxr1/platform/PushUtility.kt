package aeroxr1.platform

import android.content.Context


interface PushUtility {
    fun getId(context: Context, result: (String) -> Unit)
    fun getIdSync(context: Context):String
    fun getToken(context: Context, result: (String) -> Unit,  error: (String) -> Unit)
}
