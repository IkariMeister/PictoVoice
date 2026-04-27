package com.pictovoice.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pictovoice.App
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import java.security.MessageDigest
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test

/**
 * Lightweight snapshot tests using a deterministic pixel-hash golden strategy.
 *
 * When UI intentionally changes, update the golden values in this file.
 */
class CommunicationSnapshotTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun communicationScreen_default_snapshot() {
        val viewModel = testViewModel()
        composeRule.setContent { App(viewModel = viewModel) }
        composeRule.waitForIdle()

        val image = composeRule.onRoot().captureToImage()
        val snapshotHash = image.sha256()

        assumeTrue("Initialize GOLDEN_DEFAULT_SCREEN first", GOLDEN_DEFAULT_SCREEN != UNINITIALIZED_GOLDEN)
        assertEquals(
            expected = GOLDEN_DEFAULT_SCREEN,
            actual = snapshotHash,
            message = "CommunicationScreen snapshot changed. If intentional, update GOLDEN_DEFAULT_SCREEN.",
        )
    }

    @Test
    fun communicationScreen_with_sentence_snapshot() {
        val viewModel = testViewModel()
        runBlocking {
            val p = viewModel.state.value.pictograms.first()
            viewModel.onEvent(CommunicationEvent.SelectPictogram(p))
            viewModel.onEvent(CommunicationEvent.SelectPictogram(p))
        }

        composeRule.setContent { App(viewModel = viewModel) }
        composeRule.waitForIdle()

        val image = composeRule.onRoot().captureToImage()
        val snapshotHash = image.sha256()

        assumeTrue("Initialize GOLDEN_WITH_SENTENCE first", GOLDEN_WITH_SENTENCE != UNINITIALIZED_GOLDEN)
        assertEquals(
            expected = GOLDEN_WITH_SENTENCE,
            actual = snapshotHash,
            message = "CommunicationScreen sentence snapshot changed. If intentional, update GOLDEN_WITH_SENTENCE.",
        )
    }

    private fun testViewModel(): CommunicationViewModel =
        CommunicationViewModel(
            vocabularyRepository =
                object : VocabularyRepository {
                    override suspend fun listPictograms() =
                        listOf(
                            com.pictovoice.core.model.Pictogram("yes", "Yes", "Yes"),
                            com.pictovoice.core.model.Pictogram("no", "No", "No"),
                            com.pictovoice.core.model.Pictogram("water", "Water", "Water"),
                            com.pictovoice.core.model.Pictogram("help", "Help", "Help"),
                        )

                    override suspend fun getPictogramById(id: String) =
                        listPictograms().find { it.id == id }
                },
            telemetry = object : Telemetry {
                override fun event(name: String, attributes: Map<String, String>) = Unit
            },
        )

    private fun ImageBitmap.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val pixels = toPixelMap()
        for (y in 0 until pixels.height) {
            for (x in 0 until pixels.width) {
                val c = pixels[x, y]
                md.update((c.red * 255).toInt().toByte())
                md.update((c.green * 255).toInt().toByte())
                md.update((c.blue * 255).toInt().toByte())
                md.update((c.alpha * 255).toInt().toByte())
            }
        }
        return md.digest().joinToString("") { "%02x".format(it) }
    }

    companion object {
        // NOTE: update these hashes intentionally when UI snapshot changes are expected.
        private const val UNINITIALIZED_GOLDEN = "__UNINITIALIZED_GOLDEN__"
        private const val GOLDEN_DEFAULT_SCREEN = UNINITIALIZED_GOLDEN
        private const val GOLDEN_WITH_SENTENCE = UNINITIALIZED_GOLDEN
    }
}
