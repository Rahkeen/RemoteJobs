package com.remote.remotejobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.navArgument

class DetailViewFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_view_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewMod = (activity as MainActivity).remoteJobsViewModel
        viewMod.getJobsAt(0)?.let {
            configureDetails(view, it)
        }
    }

    private fun configureDetails(view: View, currentJob: RemoteJob) {
        view.findViewById<TextView>(R.id.test_text_view).apply {
            text = currentJob.title
        }

        view.findViewById<EditText>(R.id.job_description_edit_text).apply {
            setText(parseJobDescription(currentJob.description))
        }
    }

    private fun parseJobDescription(description: String): String {
        var copyDescription = description
        var firstIndex = 0
        var secondIndex = 0
       while (firstIndex <= secondIndex && secondIndex < copyDescription.length) {
           firstIndex = copyDescription.indexOf("<", 0, false)
           secondIndex = copyDescription.indexOf(">",0, false)
           copyDescription = copyDescription.replaceRange(firstIndex, secondIndex + 1, "")
       }
        return copyDescription
    }
}