package com.blez.basic_dictionary.feature_dictionary.data.remote.dto

import com.blez.basic_dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.blez.basic_dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val license: License,
    val meanings: List<MeaningDto>,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            license = license,
            meanings = meanings.map { it.toMeaning() },
            sourceUrls = sourceUrls,
            word = word,
            phonetics = phonetics[0].text
        )
    }

    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            license = license.name,
            meanings = meanings.map { it.toMeaning() },
            word = word,
            phonetic = phonetics[0].text
        )
    }
}