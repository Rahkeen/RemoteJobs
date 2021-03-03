package com.remote.remotejobs

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobsService {
    @GET("remote-jobs?limit=20")
    fun getRemoteJobs(
        @Query("category")
        category: String
    ): Single<JobWrapper>
}