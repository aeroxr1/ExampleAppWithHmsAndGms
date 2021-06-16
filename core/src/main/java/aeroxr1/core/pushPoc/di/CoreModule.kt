package aeroxr1.core.pushPoc.di

import aeroxr1.core.pushPoc.HandleMessagingService
import aeroxr1.platform.PlatformMessagingService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CoreModule {

    @Binds
    abstract fun bindHandleMessagingService(impl:HandleMessagingService): PlatformMessagingService

}