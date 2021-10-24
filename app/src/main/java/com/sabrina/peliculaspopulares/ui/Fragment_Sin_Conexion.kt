package com.sabrina.peliculaspopulares.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sabrina.peliculaspopulares.R
import kotlinx.android.synthetic.main.fragment_sin_conexion.*


class Fragment_Sin_Conexion : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sin_conexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_reconectar.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_Sin_Conexion_to_fragment_Peliculas_Populares2)
        }

    }

}