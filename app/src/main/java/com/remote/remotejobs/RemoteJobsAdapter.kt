package com.remote.remotejobs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RemoteJobsAdapter(context: Context, remoteJobs: List<RemoteJob>): RecyclerView.Adapter<RemoteJobsViewHolder>() {

    private val adapterContext = context
    var jobs = remoteJobs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobsViewHolder {
        val xCell = LayoutInflater.from(parent.context).inflate(R.layout.cell_jobs_remote, parent, false)
        return RemoteJobsViewHolder(xCell)
    }

    override fun onBindViewHolder(holder: RemoteJobsViewHolder, position: Int) {
        holder.bind(
                adapterContext.getString(R.string.position_name, jobs[position].title),
                adapterContext.getString(R.string.company_name, jobs[position].companyName)
        )
    }

    // Should take in the size of the list that results from the api call.
    override fun getItemCount(): Int {
        return jobs.size
    }
}

// Set up the view here.
// This will take in an inflated view.
class RemoteJobsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var jobTextView: TextView = itemView.findViewById(R.id.job_name_text_view)
    private var companyTextView: TextView = itemView.findViewById(R.id.company_name_text_view)

    fun bind(position: String, company: String) {
        jobTextView.text = position
        companyTextView.text = company
    }
}