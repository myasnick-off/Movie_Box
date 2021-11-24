package com.example.moviebox.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.databinding.MainRecyclerItemBinding
import com.example.moviebox.model.entities.Category
import com.example.moviebox.ui.main.MainFragment

class MainFragmentAdapter(private val itemViewClickListener: MainFragment.OnItemViewClickListener) :
    ListAdapter<Category, MainFragmentAdapter.MainViewHolder>(DiffCallBack()) {

    private lateinit var binding: MainRecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size // когда размер списка больше 7 RecyclerView начинает дурить (начиная с 8го элемента вставляет элементы из начала)

    // добавляем класс DiffUtil для обработки изменений в списке, и реализовываем его методы
    private class DiffCallBack: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(category: Category) = with(binding) {
            categoryTextView.text = category.name
            // инициализируем вложенный горизонтальный RecyclerView
            innerRecycler.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = InnerRecyclerAdapter(itemViewClickListener).apply {
                    submitList(category.movieList.toMutableList())
                }
            innerRecycler.adapter = adapter
        }

    }
}