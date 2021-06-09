package aeroxr1

import aeroxr1.platform.PushUtility
import aeroxr1.platformgms.GmsPushUtility

object ServiceLocator : ServiceLocatorInterface {

   override fun providePushUtility():PushUtility{
      return GmsPushUtility()
   }
}