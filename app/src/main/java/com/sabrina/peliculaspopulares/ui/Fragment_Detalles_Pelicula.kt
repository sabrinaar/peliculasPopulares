package com.sabrina.peliculaspopulares.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.domain.RepoImpl
import com.sabrina.peliculaspopulares.ui.viewmodel.MainViewModel
import com.sabrina.peliculaspopulares.ui.viewmodel.VMFactory
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.android.synthetic.main.fragment_detalles_pelicula.*
import kotlinx.android.synthetic.main.progress_dialog.*
import java.net.UnknownHostException

class Fragment_Detalles_Pelicula : Fragment() {

    private lateinit var pelicula: Pelicula
    private val viewmodel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            pelicula = it.getParcelable("pelicula")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_pelicula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.peliculaDetalles(pelicula.id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progress_bar_detalles.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    setInfoPelicula(result.data)
                    progress_bar_detalles.visibility = View.GONE
                }
                is Resource.Failure -> {
                    progress_bar_detalles.visibility = View.GONE
                    if (result.exception is UnknownHostException) {
                        findNavController().navigate(R.id.action_fragment_Detalles_Pelicula_to_fragment_Sin_Conexion)
                    }
                }
            }
        })

    }


    fun setInfoPelicula(pelicula: PeliculaDetalles) {
        var generos_pelicula: String = ""


        if (pelicula.portada.isNotEmpty() && !pelicula.portada.isNullOrBlank()) {
            Glide.with(requireContext())
                .load(getString(R.string.portada_url_base) + pelicula.portada)
                .centerCrop().into(image_portada_detalle)
        }else{
            Glide.with(requireContext())
                .load(R.drawable.ic_no_image)
                .centerCrop().into(image_portada_detalle)
        }

        titulo_pelicula.text = pelicula.titulo
        anio_pelicula.text = pelicula.fecha.substring(0, 4)

        fecha_estreno_pelicula.text = pelicula.fecha
        rating_pelicula.text = pelicula.rating.toString()
        idioma_pelicula.text = pelicula.idioma_original
        duracion_pelicula.text = pelicula.duracion.toString()
        descripcion_pelicula.text = pelicula.descripcion

        if (!pelicula.genero.isNullOrEmpty()) {
            for (elemento in pelicula.genero) {
                generos_pelicula = generos_pelicula + elemento.nombre_genero + ", "
            }

            generos_pelicula = generos_pelicula.substring(
                0,
                generos_pelicula.length - 2
            ) //elimino coma del ultimo elemento

        }
        genero_pelicula.text = generos_pelicula

    }

}