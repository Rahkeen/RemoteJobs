package com.remote.remotejobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RemoteJobsAdapter(
    var jobs: List<RemoteJob>,
) : RecyclerView.Adapter<RemoteJobsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobsViewHolder {
        val xCell = LayoutInflater.from(parent.context).inflate(R.layout.cell_jobs_remote, parent, false)
        return RemoteJobsViewHolder(xCell)
    }

    override fun onBindViewHolder(holder: RemoteJobsViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    // Should take in the size of the list that results from the api call.
    override fun getItemCount(): Int {
        return jobs.size
    }
}

// Set up the view here.
// This will take in an inflated view.
class RemoteJobsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val jobTextView: TextView = itemView.findViewById(R.id.job_name_text_view)
    private val companyTextView: TextView = itemView.findViewById(R.id.company_name_text_view)

    fun bind(job: RemoteJob) {
        jobTextView.text = job.title
        companyTextView.text = job.companyName
    }
}