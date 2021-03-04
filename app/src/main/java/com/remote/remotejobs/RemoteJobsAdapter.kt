package com.remote.remotejobs

import android.content.Context
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class RemoteJobsAdapter(var remoteJobs: List<RemoteJob>, private val tapAction: () -> Unit): RecyclerView.Adapter<RemoteJobsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobsViewHolder {
        val xCell = LayoutInflater.from(parent.context).inflate(R.layout.cell_jobs_remote, parent, false)
        return RemoteJobsViewHolder(xCell)
    }

    override fun onBindViewHolder(holder: RemoteJobsViewHolder, position: Int) {
        holder.bind(remoteJobs[position], tapAction)
    }

    // Should take in the size of the list that results from the api call.
    override fun getItemCount(): Int {
        return remoteJobs.size
    }
}

// Set up the view here.
// This will take in an inflated view.
class RemoteJobsViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private var jobTextView: TextView = itemView.findViewById(R.id.job_name_text_view)
    private var companyTextView: TextView = itemView.findViewById(R.id.company_name_text_view)

    fun bind(job: RemoteJob, tapAction: () -> Unit) {
        jobTextView.text = view.context.getString(R.string.position_name, job.title)
        companyTextView.text = view.context.getString(R.string.company_name, job.companyName)
        view.setOnClickListener {
            tapAction()
        }
    }
}