package com.pictovoice.domain.dispatcher

import com.pictovoice.domain.command.DomainCommand
import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.usecase.AddPictogramHandler
import com.pictovoice.domain.usecase.SpeakSentenceHandler

class DefaultCommandHandler(
    private val addPictogram: AddPictogramHandler,
    private val speakSentence: SpeakSentenceHandler,
) : CommandHandler {
    override suspend fun handle(command: DomainCommand, sentence: Sentence): Sentence =
        when (command) {
            is DomainCommand.AddPictogram -> addPictogram(command.id, sentence)
            is DomainCommand.RemovePictogramAt -> {
                val next = sentence.items.toMutableList()
                if (command.index in next.indices) next.removeAt(command.index)
                sentence.copy(items = next, revision = sentence.revision + 1)
            }
            DomainCommand.ClearSentence -> Sentence(emptyList(), sentence.revision + 1)
            DomainCommand.SpeakSentence -> {
                speakSentence(sentence)
                sentence
            }
            DomainCommand.SyncVocabulary -> sentence
        }
}
