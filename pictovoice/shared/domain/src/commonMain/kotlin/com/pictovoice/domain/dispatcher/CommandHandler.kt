package com.pictovoice.domain.dispatcher

import com.pictovoice.domain.command.DomainCommand
import com.pictovoice.domain.model.Sentence

interface CommandHandler {
    suspend fun handle(command: DomainCommand, sentence: Sentence): Sentence
}
