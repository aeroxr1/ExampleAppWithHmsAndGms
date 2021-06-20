package aeroxr1.pocHybridHmsGms

import aeroxr1.PlatformInit
import android.app.Application

class PocApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PlatformInit.initPlatform(applicationContext)
    }
}