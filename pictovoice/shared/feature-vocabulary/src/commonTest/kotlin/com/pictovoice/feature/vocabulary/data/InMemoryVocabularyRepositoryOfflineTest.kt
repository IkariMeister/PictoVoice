package com.pictovoice.feature.vocabulary.data

import com.pictovoice.feature.vocabulary.data.network.OfflineNetworkMonitor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlinx.coroutines.test.runTest

class InMemoryVocabularyRepositoryOfflineTest {
    @Test
    fun repository_provides_catalog_without_network() = runTest {
        assertFalse(OfflineNetworkMonitor.isOnline())

        val repository = InMemoryVocabularyRepository()
        val pictograms = repository.listPictograms()

        assertEquals(4, pictograms.size)
        assertEquals(listOf("yes", "no", "water", "help"), pictograms.map { it.id })
    }

    @Test
    fun repository_can_lookup_pictogram_offline() = runTest {
        assertFalse(OfflineNetworkMonitor.isOnline())

        val repository = InMemoryVocabularyRepository()
        val pictogram = repository.getPictogramById("water")

        assertNotNull(pictogram)
        assertEquals("Water", pictogram.label)
        assertEquals("Water", pictogram.spokenText)
    }
}
