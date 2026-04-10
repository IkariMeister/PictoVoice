package com.pictovoice

import android.app.Application
import com.pictovoice.BuildConfig
import io.sentry.kotlin.multiplatform.Sentry

class PictoVoiceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val dsn = BuildConfig.SENTRY_DSN
        if (dsn.isNotBlank()) {
            Sentry.init { options ->
                options.dsn = dsn
            }
        }
    }
}
