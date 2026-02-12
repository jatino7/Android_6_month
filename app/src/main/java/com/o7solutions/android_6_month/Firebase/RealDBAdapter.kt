package com.o7solutions.android_6_month.Firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.o7solutions.android_6_month.R

class RealDBAdapter(val listOfSUsers: ArrayList<User>): RecyclerView.Adapter<RealDBAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
       val item = listOfSUsers[position]

        holder.name.text = item.username
        holder.email.text = item.email


    }

    override fun getItemCount(): Int {
        return listOfSUsers.size
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        val name : TextView = view.findViewById<TextView>(R.id.nameTV)
        val email: TextView = view.findViewById<TextView>(R.id.emailTV)


    }
}