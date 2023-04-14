package com.blez.basic_dictionary.feature_dictionary.domain.use_case

import com.blez.basic_dictionary.core.util.Resource
import com.blez.basic_dictionary.feature_dictionary.domain.model.WordInfo
import com.blez.basic_dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(private val repository: WordInfoRepository) {
    operator fun invoke(word : String) : Flow<Resource<List<WordInfo>>>{
      if (word.isBlank()){
          return flow {  }
      }
        return repository.getWordInfo(word = word)
    }
}