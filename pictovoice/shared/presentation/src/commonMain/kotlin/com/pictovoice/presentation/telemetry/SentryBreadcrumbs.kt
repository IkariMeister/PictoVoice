package com.pictovoice.presentation.telemetry

import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.protocol.Breadcrumb

object SentryBreadcrumbs {
    fun record(eventName: String, data: Map<String, String>) {
        runCatching {
            val crumb =
                Breadcrumb().apply {
                    category = "pictovoice.ui"
                    message = eventName
                    data.forEach { (k, v) -> setData(k, v) }
                }
            Sentry.addBreadcrumb(crumb)
        }
    }
}
