package com.example.moviebox.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebox.R
import com.example.moviebox.databinding.MainFragmentBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.entities.Category
import com.example.moviebox.services.DataLoadingService
import com.example.moviebox.ui.adapters.MainFragmentAdapter
import com.example.moviebox.ui.details.DetailsFragment

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    // реализация события по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {
        override fun onItemClicked(movieId: Int) {
            val manager = activity?.supportFragmentManager
            // передаем во фрагмент с деталями фильма его ID
            manager?.let {
                val bundle = Bundle().apply {
                    putInt(DetailsFragment.KEY_BUNDLE, movieId)
                }
                manager
                    .beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("detailsFragment")
                    .commit()
            }
        }
    }

    // Приемник ширококвещательных сообщений, приходящих от сервиса DataLoadingService
    private val dataLoadingServiceReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getStringExtra(DataLoadingService.INTENT_SERVICE_STATUS)) {
                DataLoadingService.INTENT_SERVICE_LOADING -> binding.mainProgressBar.show()
                DataLoadingService.INTENT_SERVICE_SUCCESS -> {
                    val arr = intent.getParcelableArrayExtra(DataLoadingService.INTENT_SERVICE_DATA)
                    val categoryList = arr?.toList() as List<Category>
                    renderData(categoryList)
                }
                DataLoadingService.INTENT_SERVICE_ERROR -> {
                    binding.mainProgressBar.hide()
                    binding.main.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload)
                    ) { DataLoadingService.start(requireContext()) }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // подписываемся на локальные широковещательные сообщения от сервиса DataLoadingService
        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(
                dataLoadingServiceReceiver,
                IntentFilter(DataLoadingService.INTENT_ACTION_KEY)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // запкскаем сервис DataLoadingService
        DataLoadingService.start(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        // отписываемся от локальных широковещательных сообщений сервиса DataLoadingService
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(dataLoadingServiceReceiver)
        }
        // останавливаем сервис
        DataLoadingService.stop(requireContext())
        super.onDestroy()
    }

    // конфигурируем GUI главного фрагмента в соответствии с полученными данными
    private fun renderData(dataList: List<Category>) = with(binding) {
        mainProgressBar.hide()
        mainRecycler
            .layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = MainFragmentAdapter(onMovieItemClickListener).apply {
            setData(dataList)
        }
        mainRecycler.adapter = adapter
    }

    interface OnItemViewClickListener {
        fun onItemClicked(movieId: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}