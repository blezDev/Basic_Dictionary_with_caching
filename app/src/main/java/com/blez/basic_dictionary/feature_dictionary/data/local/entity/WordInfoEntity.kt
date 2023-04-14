package com.blez.basic_dictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blez.basic_dictionary.feature_dictionary.data.remote.dto.License
import com.blez.basic_dictionary.feature_dictionary.domain.model.Meaning
import com.blez.basic_dictionary.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    val word : String,
    val phonetic : String,
    val license : String,
    val meanings : List<Meaning>,
    @PrimaryKey val id : Int? = null
){
    fun toWordInfo() : WordInfo{
        return WordInfo(
            meanings = meanings,
            word = word,
            license = License(name = license,""),
            sourceUrls = listOf(),
            phonetics = phonetic
        )
    }
}
