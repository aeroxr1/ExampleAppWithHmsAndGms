package aeroxr1.platformgms

import aeroxr1.platform.AnalyticsLog
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsLog: AnalyticsLog {

    private var CRASHLYTICS_ENABLED = true

    override fun disableCrashlytics() {
        CRASHLYTICS_ENABLED = false
    }

    override fun v(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().log("$tag: $msg")
        }
    }

    override fun d(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().log("$tag: $msg")
        }
    }

    override fun i(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().log("$tag: $msg")
        }
    }

    override fun w(tag: String, msg: String) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().log("$tag: $msg")
        }
    }

    override fun e(tr: Throwable?) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().recordException(tr!!)
        }
    }

    override fun log(msg: String?) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().log(msg!!)
        }
    }

    override fun setString(key: String?, value: String?) {
        if (CRASHLYTICS_ENABLED) {
            FirebaseCrashlytics.getInstance().setCustomKey(key!!, value!!)
        }
    }
}