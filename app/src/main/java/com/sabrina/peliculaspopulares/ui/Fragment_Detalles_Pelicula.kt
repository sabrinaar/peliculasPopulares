package com.sabrina.peliculaspopulares.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.model.Pelicula


class Fragment_Detalles_Pelicula : Fragment() {

    private lateinit var pelicula: Pelicula

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

}