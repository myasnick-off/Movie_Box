package com.example.moviebox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentTabBinding
import com.example.moviebox.di.hide
import com.example.moviebox.di.show
import com.example.moviebox.di.showSnackBar
import com.example.moviebox.model.rest_entities.MovieDTO
import com.example.moviebox.ui.OnItemViewClickListener
import com.example.moviebox.ui.details.DetailsFragment

class TabFragment : Fragment() {

    // создаем/вызываем для каждой страницы свой экземпляр TabViewModel, помеченный своим ключем
    // этот экземпляр TabViewModel помещаем во ViewModelProvider Активити!
    private val viewModel: TabViewModel by lazy {
        ViewModelProvider(requireActivity())["KEY_#$tabId", TabViewModel::class.java]
    }

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    private var tabId: Int = 0
    private lateinit var adapter: TabRecyclerAdapter

    // реализация событий по нажатию на itemView фильма в RecyclerView
    private val onMovieItemClickListener = object : OnItemViewClickListener {

        //по короткому нажатию запускаем фрагмент с деталями фильма
        override fun onItemClicked(movieId: Long) {
            val manager = activity?.supportFragmentManager
            // передаем во фрагмент с деталями фильма его ID
            manager?.let {
                val bundle = Bundle().apply {
                    putLong(DetailsFragment.KEY_BUNDLE, movieId)
                }
                manager
                    .beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("detailsFragment")
                    .commit()
            }
        }
        // по длинному нажатию выводим попап меню
        override fun onItemLongClicked(movie: MovieDTO, view: View) {
            val pop = PopupMenu(requireContext(), view)
            pop.inflate(R.menu.menu_popup_profile)
            pop.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_delete -> {
                        deleteMovie(movie)
                    }
                }
                true
            }
            pop.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabId = it.getInt(ARG_TAB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabRecycler.layoutManager = GridLayoutManager(context, 2)
        adapter = TabRecyclerAdapter(onMovieItemClickListener)
        binding.tabRecycler.adapter = adapter

        val observer = Observer<ProfileAppState> { renderData(it) }
        viewModel.getProfileLiveData().observe(viewLifecycleOwner, observer)
        getAllData(tabId)
    }

    override fun onDestroyView() {
//        Toast.makeText(context, "destroy #$tabId", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: ProfileAppState) = with(binding) {
        when (appState) {
            ProfileAppState.Loading -> tabProgressBar.show()
            is ProfileAppState.Success -> {
                tabProgressBar.hide()
                adapter.submitList(appState.movieList)
            }
            is ProfileAppState.Error -> {
                tabProgressBar.hide()
                historyLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) { getAllData(tabId) }
            }
        }
    }

    private fun getAllData(tabId: Int) {
        when(tabId) {
            0 -> viewModel.getAllHistory()
            1 -> viewModel.getAllFavorite()
            2 -> viewModel.getAllWishlist()
        }
    }

    private fun deleteMovie(movie: MovieDTO) {
        when(tabId) {
            0 -> viewModel.deleteFromHistory(movie)
            1 -> viewModel.deleteFromFavorite(movie)
            2 -> viewModel.deleteFromWishlist(movie)
        }
    }

    companion object {
        private const val ARG_TAB_ID = "TAB_ID"

        fun newInstance(tabId: Int) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB_ID, tabId)
                }
            }
    }
}