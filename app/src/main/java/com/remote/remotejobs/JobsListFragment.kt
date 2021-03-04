package com.remote.remotejobs

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class JobsListFragment : Fragment() {

    private var remoteJobs: List<RemoteJob> = emptyList()
    private lateinit var recyclerAdapter: RemoteJobsAdapter
    private lateinit var remoteJobsList: RecyclerView
    private lateinit var remoteService: RemoteJobsService

    private lateinit var editText: EditText
    private lateinit var searchIcon: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.jobs_list_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        configureRecyclerView(view)
        configureRetrofit()
        // findNavController() // Used to find an instance of navigation controller, can be used with views, activities, or fragments.
    }

    private fun initializeViews(view: View) {
        editText = view.findViewById<EditText>(R.id.ersatz_search_bar).apply {
            setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus) {
                    editText.setText(R.string.empty)
                } else {
                    // Hides keyboard if focus is relinquished.
                    (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                        hideSoftInputFromWindow(v.windowToken,0)
                    }
                }
            }
        }
        progressBar = view.findViewById(R.id.progress_bar)
        searchIcon = view.findViewById<ImageView>(R.id.search_bar_icon).apply {
            setOnClickListener {
                fetchJobs(editText.text.toString())
            }
        }
    }

    private fun configureRecyclerView(view: View) {
        remoteJobsList = view.findViewById<RecyclerView>(R.id.remote_jobs_recycler_view).apply {
            // Requires two things
            // Adapter -> iOS equivalent of a DataSource (perhaps a delegate as well?)
            // Layout Manager
            layoutManager = LinearLayoutManager(context).apply {
                // This is the default value for orientation, but I wanted to practice using apply.
                orientation = LinearLayoutManager.VERTICAL
            }
            recyclerAdapter = RemoteJobsAdapter(remoteJobs = remoteJobs) {
                findNavController().navigate(R.id.action_jobsListFragment_to_detailViewFragment)
            }
            adapter = recyclerAdapter
        }
    }

    // Configure retrofit
    private fun configureRetrofit() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://remotive.io/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Converts retrofit call, into any RxJava return type (single, observable, completable, flowable...)
                .addConverterFactory(MoshiConverterFactory.create()) // Converts JSON into Model and vise versa
                .build()
        // Builders are used to build some object.
        // It is a pattern that allows you to construct something in what is called a fluent API.
        remoteService = retrofit.create(RemoteJobsService::class.java)
    }

    private fun fetchJobs(position: String) {
        remoteService.getRemoteJobs(position)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                // Show a progress bar once the call begins.
                progressBar.visibility = View.VISIBLE
            }
            .subscribe({
            recyclerAdapter.apply {
                progressBar.visibility = View.GONE // Hide the progress bar after the call is over.
                this.remoteJobs = it.jobs
                this.notifyDataSetChanged()
            }
        },{
            Toast.makeText(context, R.string.generic_error_text, Toast.LENGTH_SHORT).show()
        })
    }
}