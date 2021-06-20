package aeroxr1.platform

interface PlatformMessagingService {
    fun onNewToken(token: String)
    fun onMessageReceived(messageData: Map<String, String>?)
}