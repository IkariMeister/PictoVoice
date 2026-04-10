package com.pictovoice.domain.command

sealed interface DomainCommand {
    data class AddPictogram(val id: String) : DomainCommand
    data class RemovePictogramAt(val index: Int) : DomainCommand
    data object ClearSentence : DomainCommand
    data object SpeakSentence : DomainCommand
    data object SyncVocabulary : DomainCommand
}
