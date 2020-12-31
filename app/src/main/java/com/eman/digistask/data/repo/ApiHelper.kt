package com.eman.digistask.data.repo

import com.eman.digistask.domain.model.DigisAll
import com.eman.digistask.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getDigis(): Flow<Resource<DigisAll>>

}