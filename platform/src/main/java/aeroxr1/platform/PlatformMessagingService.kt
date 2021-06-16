package aeroxr1.platform

interface PlatformMessagingService {
    fun onNewToken(token: String)
    fun onMessageReceived(data: Map<String, String>?)
}