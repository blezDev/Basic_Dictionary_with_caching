package com.blez.basic_dictionary.feature_dictionary.domain.repository

import com.blez.basic_dictionary.core.util.Resource
import com.blez.basic_dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {


    fun getWordInfo(word : String) : Flow<Resource<List<WordInfo>>>



}