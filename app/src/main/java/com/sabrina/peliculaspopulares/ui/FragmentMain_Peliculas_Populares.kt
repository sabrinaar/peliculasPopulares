package com.sabrina.peliculaspopulares.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.domain.RepoImpl
import com.sabrina.peliculaspopulares.ui.Adapter.AdaptadorPeliculas
import com.sabrina.peliculaspopulares.ui.viewmodel.MainViewModel
import com.sabrina.peliculaspopulares.ui.viewmodel.VMFactory
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_peliculas_popul.*
import java.util.*

const val QUERY_PAGE_SIZE = 20

class FragmentMain_Peliculas_Populares : Fragment() {

    private val viewmodel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }
    lateinit var adaptadorPeliculas: AdaptadorPeliculas
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_peliculas_popul, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setUpBuscador()
        setUpObserver()

        adaptadorPeliculas.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putParcelable("pelicula", it)
            findNavController().navigate(R.id.fragment_Detalles_Pelicula, bundle)
        }


    }

    fun setUpObserver(){
        viewmodel.list_peliculas_populares.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.FiltroRegresar -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        adaptadorPeliculas.differ.submitList(newsResponse.popularPelisList.toList())

                        val totalPages = newsResponse.totalResultados / QUERY_PAGE_SIZE + 2
                        isLastPage = viewmodel.pagina_peliculas_a_buscar == totalPages
                    }
                }
                is Resource.Filtro -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        adaptadorPeliculas.differ.submitList(newsResponse.popularPelisList.toList())

                        isLastPage = true
                    }
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        adaptadorPeliculas.differ.submitList(newsResponse.popularPelisList.toList())
                        val totalPages = newsResponse.totalPaginas + 2
                        isLastPage = viewmodel.pagina_peliculas_a_buscar == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    findNavController().navigate(R.id.action_fragment_Peliculas_Populares_to_fragment_Sin_Conexion2)
                    response.message?.let { message ->
                        Log.e("TAG", "Error: $message")
                    }
                }
            }
        })
    }

    fun setUpBuscador() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewmodel.getPeliculasPopularesFiltro(p0!!) //buscar peli al hacer submit
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.equals("")){ //cuando borra el nombre a buscar
                    viewmodel.getPeliculasPopularesFiltroBack()
                }
                return false
            }
        }
        )
    }

    private fun hideProgressBar() {
        progress_bar_popular.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        progress_bar_popular.visibility = View.VISIBLE
        isLoading = true
    }



    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewmodel.getPeliculasPopulares()
                isScrolling = false
            } else {
                rv_peliculas_populares.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        adaptadorPeliculas = AdaptadorPeliculas()
        rv_peliculas_populares.apply {
            adapter = adaptadorPeliculas
            layoutManager = GridLayoutManager(requireContext(), 3)
            addOnScrollListener(this@FragmentMain_Peliculas_Populares.scrollListener)
        }
    }

}
