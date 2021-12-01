package com.example.moviebox.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox.model.entities.Category
import com.example.moviebox.ui.OnItemViewClickListener

class MainFragmentAdapter(private val itemViewClickListener: OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var dataList = arrayListOf<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Category>) {
        if (dataList != data) {
            dataList = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val innerAdapter: InnerRecyclerAdapter
        private val categoryTextView: TextView = itemView.findViewById(R.id.category_text_view)
        private val innerRecycler: RecyclerView = itemView.findViewById(R.id.inner_recycler)

        init {
            val context = itemView.context
            // инициализируем вложенный горизонтальный RecyclerView
            innerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerAdapter = InnerRecyclerAdapter(itemViewClickListener)
            innerRecycler.adapter = innerAdapter
        }

        fun bind(category: Category) {
            categoryTextView.text = category.name
            innerAdapter.setData(category.movieList)
        }
    }
}