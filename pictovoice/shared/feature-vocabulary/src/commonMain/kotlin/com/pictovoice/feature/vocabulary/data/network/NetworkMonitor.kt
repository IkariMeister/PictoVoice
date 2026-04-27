package com.pictovoice.feature.vocabulary.data.network

/**
 * Minimal network monitor abstraction.
 *
 * Current usage: sync orchestration can consult this state and no-op when offline,
 * ensuring the communication UI remains responsive and local-first.
 */
interface NetworkMonitor {
    fun isOnline(): Boolean
}

object OfflineNetworkMonitor : NetworkMonitor {
    override fun isOnline(): Boolean = false
}
