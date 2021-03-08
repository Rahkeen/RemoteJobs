package com.remote.remotejobs

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val remoteJobsViewModel: RemoteJobsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    /****
     * TODO List:
     * 1. Figure out how to know which job was selected upon segue.
     * 2. Figure out Complete the aesthetic for the details page.
     * 3. Implement deep linking. 
     */
}