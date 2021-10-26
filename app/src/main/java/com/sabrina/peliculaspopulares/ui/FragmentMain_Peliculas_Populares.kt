package com.sabrina.peliculaspopulares.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabrina.peliculaspopulares.MainActivity
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.domain.RepoImpl
import com.sabrina.peliculaspopulares.ui.Adapter.AdaptadorPeliculas
import com.sabrina.peliculaspopulares.ui.viewmodel.MainViewModel
import com.sabrina.peliculaspopulares.ui.viewmodel.VMFactory
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_peliculas_popul.*

const val QUERY_PAGE_SIZE = 20

class FragmentMain_Peliculas_Populares : Fragment() {

    private val viewmodel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }
    lateinit var adaptadorPeliculas: AdaptadorPeliculas
    lateinit var viewModel: MainViewModel

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

   /* fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }*/


    fun isOnline(): Boolean {
        val connMgr = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // if (isOnline()){
            setupRecyclerView()

            adaptadorPeliculas.setOnItemClickListener {
                val bundle = Bundle()
                bundle.putParcelable("pelicula", it)
                findNavController().navigate(R.id.fragment_Detalles_Pelicula, bundle)
            }

            /* viewmodel.peliculasPopularesList.observe(viewLifecycleOwner, Observer { result ->
                 when (result) {
                     is Resource.Loading -> {
                         progress_bar_popular.visibility = View.VISIBLE

                     }
                     is Resource.Success -> {
                         progress_bar_popular.visibility = View.GONE
                         rv_peliculas_populares.adapter = AdapterPeliculas(requireContext(), result.data, this)
                     }
                     is Resource.Failure -> {
                         progress_bar_popular.visibility = View.GONE

                         if (result.exception is UnknownHostException) {
                             findNavController().navigate(R.id.action_fragment_Peliculas_Populares_to_fragment_Sin_Conexion2)
                         }
                     }
                 }
             })*/
            viewmodel.list_peliculas_populares.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            adaptadorPeliculas.differ.submitList(newsResponse.popularPelisList.toList())
                            val totalPages = newsResponse.totalResultados / QUERY_PAGE_SIZE + 2
                            isLastPage = viewmodel.pagina_peliculas == totalPages
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        println("acaa")
                        findNavController().navigate(R.id.action_fragment_Peliculas_Populares_to_fragment_Sin_Conexion2)

                        response.message?.let { message ->
                            Log.e("TAG", "An error occured: $message")
                        }
                    }
                }
            })
      //  }else{
      //      findNavController().navigate(R.id.action_fragment_Peliculas_Populares_to_fragment_Sin_Conexion2)
      //  }
    }


    private fun hideProgressBar() {
        progress_bar_popular.visibility = View.GONE
        isLoading = false
    }

    /*private fun setupRecyclerView() {
        rv_peliculas_populares.layoutManager = GridLayoutManager(requireContext(), 3)
    }*/


    private fun showProgressBar() {
        progress_bar_popular.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

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
