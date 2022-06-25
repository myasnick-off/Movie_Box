package com.example.moviebox.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox._core.ui.OnItemViewClickListener
import com.example.moviebox.home.domain.model.Category

class HomeFragmentAdapter(private val itemViewClickListener: OnItemViewClickListener) :
    RecyclerView.Adapter<HomeFragmentAdapter.MainViewHolder>() {

    private var dataList = listOf<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Category>) {
        if (dataList != data) {
            dataList = data
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val innerAdapter: InnerAdapter
        private val categoryTextView: TextView = itemView.findViewById(R.id.category_text_view)
        private val innerRecycler: RecyclerView = itemView.findViewById(R.id.inner_recycler)

        init {
            val context = itemView.context
            // инициализируем вложенный горизонтальный RecyclerView
            innerRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerAdapter = InnerAdapter(itemViewClickListener)
            innerRecycler.adapter = innerAdapter
        }

        fun bind(category: Category) {
            categoryTextView.text = category.name
            innerAdapter.setData(category.movieList)
        }
    }
}