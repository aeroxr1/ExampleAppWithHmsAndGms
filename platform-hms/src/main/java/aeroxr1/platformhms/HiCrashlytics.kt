package aeroxr1.platformhms

import aeroxr1.platform.AnalyticsLog
import com.huawei.agconnect.crash.AGConnectCrash

class HiCrashlytics: AnalyticsLog {

    private var CRASHLYTICS_ENABLED = true

    override fun disableCrashlytics() {
        CRASHLYTICS_ENABLED = false
    }

    override fun v(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().log("$tag: $msg")
        }
    }

    override fun d(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().log("$tag: $msg")
        }
    }

    override fun i(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().log("$tag: $msg")
        }
    }

    override fun w(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().log("$tag: $msg")
        }
    }

    override fun e(tr: Throwable?) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().recordException(tr!!)
        }
    }

    override fun log(msg: String?) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().log(msg!!)
        }
    }

    override fun setString(key: String?, value: String?) {
        if (CRASHLYTICS_ENABLED) {
            AGConnectCrash.getInstance().setCustomKey(key!!, value!!)
        }
    }
}
