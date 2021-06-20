package aeroxr1.platform

fun platform() = PlatformComponent.instance

interface PlatformComponent {

    val platformMessagingService: PlatformMessagingService
    val pushUtility: PushUtility
    val analyticsLog: AnalyticsLog

    companion object {
        lateinit var instance: PlatformComponent
    }

}