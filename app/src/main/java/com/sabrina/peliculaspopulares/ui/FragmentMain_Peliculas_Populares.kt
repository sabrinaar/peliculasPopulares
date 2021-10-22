package com.sabrina.peliculaspopulares.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.domain.RepoImpl
import com.sabrina.peliculaspopulares.ui.Adapter.AdapterPeliculas
import com.sabrina.peliculaspopulares.ui.viewmodel.MainViewModel
import com.sabrina.peliculaspopulares.ui.viewmodel.VMFactory
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_peliculas_popul.*


class FragmentMain_Peliculas_Populares : Fragment(), AdapterPeliculas.onPeliculaClickListener {

    private val viewmodel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }

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
        viewmodel.peliculasList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progress_bar_popular.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progress_bar_popular.visibility = View.GONE
                    rv_movie_list.adapter = AdapterPeliculas(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    progress_bar_popular.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrió un error al traer los datos ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }


    private fun setupRecyclerView() {
        rv_movie_list.layoutManager = LinearLayoutManager(requireContext())
        rv_movie_list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onPeliculaClick(pelicula:Pelicula) {
        val bundle = Bundle()
        bundle.putParcelable("pelicula",pelicula)
        findNavController().navigate(R.id.fragment_Detalles_Pelicula, bundle)
    }
}