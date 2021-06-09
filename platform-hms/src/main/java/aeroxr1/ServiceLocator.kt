package aeroxr1

import aeroxr1.platform.PushUtility
import aeroxr1.platformhms.HmsPushUtility

object ServiceLocator : ServiceLocatorInterface {

    override fun providePushUtility(): PushUtility {
        return HmsPushUtility()
    }
}