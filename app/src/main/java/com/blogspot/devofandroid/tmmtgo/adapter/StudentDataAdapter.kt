package com.blogspot.devofandroid.tmmtgo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.devofandroid.tmmtgo.R
import com.blogspot.devofandroid.tmmtgo.activity.SaveLocationMapActivity
import com.blogspot.devofandroid.tmmtgo.adapter.StudentDataAdapter.PostViewHolder
import com.blogspot.devofandroid.tmmtgo.dataClass.GetData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentDataAdapter(var context: Context, var getList: ArrayList<GetData>) :
    RecyclerView.Adapter<PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.activity_view_recycler, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val get = getList[position]
        holder.personName.text = get.name
        holder.personStandard.text = get.className
        holder.personSchool.text = get.schoolName
        holder.viewButton.setOnClickListener {
            val intent = Intent(it.context, SaveLocationMapActivity::class.java)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return getList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var personName: TextView = itemView.findViewById(R.id.person_name)
        var personStandard: TextView = itemView.findViewById(R.id.name_standard)
        var personSchool: TextView = itemView.findViewById(R.id.school_name)
        var viewButton: FloatingActionButton = itemView.findViewById(R.id.btn_mapview)


    }
}