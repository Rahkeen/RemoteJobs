package com.remote.remotejobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteJobsViewModel: ViewModel() {

    val remoteJobsList: MutableLiveData<List<RemoteJob>> by lazy {
        MutableLiveData<List<RemoteJob>>().apply {
        }
    }
    private lateinit var remoteService: RemoteJobsService

    init{
        configureRetrofit()
    }

    fun getJobsAt(position: Int): RemoteJob? {
        remoteJobsList.value?.let {
            if (position > it.size || position < 0) {
                return null
            }
            return it[position]
        }
        return null
    }

    private fun configureRetrofit() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://remotive.io/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        remoteService = retrofit.create(RemoteJobsService::class.java)
    }

    // How do I get this to work without too much UI involvement?
    fun fetchJobs(query: String) {
        remoteService.getRemoteJobs(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            remoteJobsList.value = it.jobs
                        },
                        {
                            throw it
                        }
                )
    }

}