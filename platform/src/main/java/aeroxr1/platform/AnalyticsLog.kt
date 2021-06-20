package aeroxr1.platform

interface AnalyticsLog  {

    fun disableCrashlytics()

    fun v(tag: String, msg: String)

    fun d(tag: String, msg: String)

    fun i(tag: String, msg: String)

    fun w(tag: String, msg: String)

    fun e(tr: Throwable?)

    fun log(msg: String?)

    fun setString(key: String?, value: String?)

}