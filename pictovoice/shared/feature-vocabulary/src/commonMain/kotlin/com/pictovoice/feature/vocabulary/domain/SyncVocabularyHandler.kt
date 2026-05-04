package com.pictovoice.feature.vocabulary.domain

import com.pictovoice.feature.vocabulary.data.network.NetworkMonitor

sealed interface SyncResult {
    data object SkippedOffline : SyncResult
    data object Synced : SyncResult
}

/**
 * Explicit sync command handler.
 *
 * Current behavior: no-op while offline to preserve local-first UX.
 */
class SyncVocabularyHandler(
    private val networkMonitor: NetworkMonitor,
) {
    suspend operator fun invoke(onSync: suspend () -> Unit): SyncResult {
        if (!networkMonitor.isOnline()) return SyncResult.SkippedOffline
        onSync()
        return SyncResult.Synced
    }
}
