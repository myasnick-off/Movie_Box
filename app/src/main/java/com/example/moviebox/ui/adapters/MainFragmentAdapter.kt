package com.example.moviebox.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.databinding.MainRecyclerItemBinding
import com.example.moviebox.model.entities.Category
import com.example.moviebox.ui.main.MainFragment

class MainFragmentAdapter(private val itemViewClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var categoryList: List<Category> = listOf()
    private lateinit var binding: MainRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Category>) {
        categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size // когда размер списка больше 7 RecyclerView начинает дурить (начиная с 8го элемента вставляет элементы из начала)

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(category: Category) = with(binding) {
            categoryTextView.text = category.name
            // инициализируем вложенный горизонтальный RecyclerView
            innerRecycler.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = InnerRecyclerAdapter(itemViewClickListener).apply {
                    setData(category.movieList)
                }
            innerRecycler.adapter = adapter
        }

    }
}