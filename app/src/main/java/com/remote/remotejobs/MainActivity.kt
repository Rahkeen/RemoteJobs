package com.remote.remotejobs

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private lateinit var remoteJobsList: RecyclerView
    private lateinit var remoteService: RemoteJobsService
    private var remoteJobs: List<RemoteJob> = emptyList()

    private lateinit var editText: EditText
    private lateinit var searchIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        configureRecyclerView()
        configureRetrofit()
    }

    private fun initializeViews() {
        editText = findViewById(R.id.ersatz_search_bar)
        searchIcon = findViewById<ImageView>(R.id.search_bar_icon).apply {
            setOnClickListener {
                fetchJobs()
            }
        }
    }

    private fun configureRecyclerView() {
        remoteJobsList = findViewById<RecyclerView>(R.id.remote_jobs_recycler_view).apply {
            // Requires two things
            // Adapter -> iOS equivalent of a DataSource (perhaps a delegate as well?)
            // Layout Manager
            layoutManager = LinearLayoutManager(context).apply {
                // This is the default value for orientation, but I wanted to practice using apply.
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = RemoteJobsAdapter(context = context, remoteJobs = remoteJobs)
        }
    }

    // Configure retrofit
    private fun configureRetrofit() {
        var retrofit = Retrofit.Builder()
                .baseUrl("https://remotive.io/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Converts retrofit call, into any RxJava return type (single, observable, completable, flowable...)
                .addConverterFactory(MoshiConverterFactory.create()) // Converts JSON into Model and vise versa
                .build()
        remoteService = retrofit.create(RemoteJobsService::class.java)
    }

    private fun fetchJobs() {
        remoteService.getRemoteJobs().subscribe({
            (remoteJobsList.adapter as RemoteJobsAdapter).jobs = it.jobs
           remoteJobsList.adapter?.notifyDataSetChanged()
        },{
            Toast.makeText(this, R.string.generic_error_text, Toast.LENGTH_SHORT)
        })
    }
}