package com.remote.remotejobs

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JobWrapper(
        @Json(name = "0-legal-notice") val legalNotice: String,
        @Json(name = "job-count") val jobCount: Int,
        @Json(name = "jobs") val jobs: List<RemoteJob>
)

// Denotes a class a serializable
@JsonClass(generateAdapter = true)
data class RemoteJob(
        // Specifies name for JSON field.
        @Json(name = "id") val id: String,
        @Json(name = "url") val url: String,
        @Json(name = "title") val title: String,
        @Json(name = "company_name") val companyName : String,
        @Json(name = "category") val category: String,
        @Json(name = "job_type") val jobType: String,
        @Json(name = "publication_date") val publicationDate: String,
        @Json(name = "candidate_required_location") val location: String,
        @Json(name = "salary") val salary: String,
        @Json(name = "description") val description: String,
)