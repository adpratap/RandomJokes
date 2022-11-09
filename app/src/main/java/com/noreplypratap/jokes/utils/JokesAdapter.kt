package com.noreplypratap.jokes.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noreplypratap.jokes.R
import com.noreplypratap.jokes.model.JokesData

class JokesAdapter(val jokes : List<JokesData>) : RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val  tvData : TextView
        val  tvPunchLine : TextView

        init {
            // Define click listener for the ViewHolder's View.
            tvData = view.findViewById(R.id.tvOfflineData)
            tvPunchLine = view.findViewById(R.id.tvOfflinePunchLine)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.offline_items, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvData.text = jokes[position].setup
        viewHolder.tvPunchLine.text = jokes[position].punchline

        viewHolder.itemView.setOnClickListener {
            onItemClicked?.let {
                it(jokes[position])
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = jokes.size

    private var onItemClicked : ((JokesData) -> Unit)? = null

    fun setOnClickListener(listener : (JokesData) -> Unit){
        onItemClicked = listener
    }

}
