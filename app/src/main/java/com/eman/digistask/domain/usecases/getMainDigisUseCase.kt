package com.eman.digistask.domain.usecases

import com.eman.digistask.data.repo.ApiHelper
import javax.inject.Inject

open class getMainDigisUseCase @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getDigis() =
        apiHelper.getDigis()


}