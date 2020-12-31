package com.eman.digistask.data.api

import com.eman.digistask.domain.model.DigisAll
import retrofit2.http.GET

interface ApiService {
    @GET("random")
    suspend fun getDigis(): DigisAll

}