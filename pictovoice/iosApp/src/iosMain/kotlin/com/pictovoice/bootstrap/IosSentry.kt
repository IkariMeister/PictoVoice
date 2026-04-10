package com.pictovoice.bootstrap

import io.sentry.kotlin.multiplatform.Sentry

/** iOS entry bootstrap (T021): call from Compose iOS host before UI. */
object IosSentry {
    fun init(dsn: String) {
        if (dsn.isNotBlank()) {
            Sentry.init { options ->
                options.dsn = dsn
            }
        }
    }
}
