package aeroxr1

import aeroxr1.core.pushPoc.HandleMessagingService
import aeroxr1.platform.AnalyticsLog
import aeroxr1.platform.PlatformComponent
import aeroxr1.platform.PlatformComponent.Companion.instance
import aeroxr1.platform.PlatformMessagingService
import aeroxr1.platform.PushUtility
import aeroxr1.platformhms.HiCrashlytics
import aeroxr1.platformhms.HmsPushUtility
import android.content.Context

object PlatformInit{

    fun initPlatform(appContext:Context){
        val messagingListenerService = HandleMessagingService(appContext)
        val pushUtility = HmsPushUtility()
        val crashlyticsLog = HiCrashlytics()
        val platformComponent: PlatformComponent = object : PlatformComponent {
            override val platformMessagingService: PlatformMessagingService
                get() = messagingListenerService
            override val pushUtility: PushUtility
                get() = pushUtility
            override val analyticsLog: AnalyticsLog
                get() = crashlyticsLog
        }

        instance = platformComponent
    }

}