package com.remote.remotejobs

import io.reactivex.Single
import retrofit2.http.GET

interface RemoteJobsService {
    @GET("remote-jobs?limit=20")
    fun getRemoteJobs(): Single<JobWrapper>
}