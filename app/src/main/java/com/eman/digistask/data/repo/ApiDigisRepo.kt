package com.eman.digistask.data.repo

import com.eman.digistask.data.api.ApiService
import com.eman.digistask.domain.model.DigisAll
import com.eman.digistask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDigisRepo @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getDigis(): Flow<Resource<DigisAll>> {
        return flow {
            emit(Resource.loading(null))
            val resource = try {
                val digis = apiService.getDigis()
                Resource.success(digis)
            } catch (e: Throwable) {
                Resource.error(e.toString(), null)
            }
            emit(resource)
        }
    }
}