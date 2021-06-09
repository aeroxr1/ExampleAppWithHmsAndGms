package aeroxr1

import aeroxr1.platform.PushUtility

interface ServiceLocatorInterface {
    fun providePushUtility(): PushUtility
}