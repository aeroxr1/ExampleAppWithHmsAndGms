package aeroxr1.pocHybridHmsGms

import aeroxr1.PlatformApplication
import aeroxr1.ServiceLocator
import aeroxr1.platform.PushUtility
import android.app.Application

class PocApplication : Application(), PlatformApplication {

    override val pushUtility: PushUtility
        get() = ServiceLocator.providePushUtility()

    override fun onCreate() {
        super.onCreate()
    }
}