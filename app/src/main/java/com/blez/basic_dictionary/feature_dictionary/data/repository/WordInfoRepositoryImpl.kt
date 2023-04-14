package com.blez.basic_dictionary.feature_dictionary.data.repository

import com.blez.basic_dictionary.core.util.Resource
import com.blez.basic_dictionary.feature_dictionary.data.local.WordInfoDao
import com.blez.basic_dictionary.feature_dictionary.data.remote.DictionaryApi
import com.blez.basic_dictionary.feature_dictionary.domain.model.WordInfo
import com.blez.basic_dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class WordInfoRepositoryImpl(private val api: DictionaryApi, private val dao: WordInfoDao) :
    WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfo = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfo.map { it.word })
            dao.insertWordInfos(remoteWordInfo.map { it.toWordInfoEntity()})
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something went wrong", data = wordInfos))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Could not reach server, check your internet connection", data = wordInfos))
        } catch (e: Exception) {

            emit(Resource.Error(message = "Something went wrong. Please try later", data = wordInfos))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))

    }
}