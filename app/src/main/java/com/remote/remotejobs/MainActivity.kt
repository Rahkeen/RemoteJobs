package com.remote.remotejobs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var playgroundList: RecyclerView

    private val playgroundAdapter = PlaygroundAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playgroundList = findViewById<RecyclerView>(R.id.playground_recyclerview).apply {
            adapter = playgroundAdapter
            layoutManager = LinearLayoutManager(context)
        }

//        playgroundAdapter.items = listOf("Test")
//        playgroundAdapter.notifyDataSetChanged()
    }
}

class PlaygroundAdapter : RecyclerView.Adapter<PlaygroundViewHolder>() {

    var items: List<String> = listOf(
        "Hello",
        "Goodbye",
        "Hello",
        "Goodbye",
        "Hello",
        "Goodbye",
        "Hello",
        "Goodbye",
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaygroundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_playground_cell, parent, false)
        return PlaygroundViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaygroundViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // override this if you want to setup multiple view types
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}

class PlaygroundViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var display: TextView = view.findViewById(R.id.playground_text)

    fun bind(data: String) {
        display.text = data
    }
}