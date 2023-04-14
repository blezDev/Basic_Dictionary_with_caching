package com.blez.basic_dictionary.feature_dictionary.domain.model

import com.blez.basic_dictionary.feature_dictionary.data.remote.dto.License
import com.blez.basic_dictionary.feature_dictionary.data.remote.dto.PhoneticDto

data class WordInfo(
    val license: License,
    val meanings: List<Meaning>,
    val sourceUrls: List<String>,
    val phonetics: String,
    val word: String
)
