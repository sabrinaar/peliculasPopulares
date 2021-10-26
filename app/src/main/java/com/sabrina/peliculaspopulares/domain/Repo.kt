package com.sabrina.peliculaspopulares.domain

import androidx.lifecycle.LiveData
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.vo.Resource
import retrofit2.Response

interface Repo {

    //    suspend fun getPeliculasPopulares(page:Int): Resource<ResponsePeliculasPopulares>
    suspend fun getPeliculasPopulares(page:Int): Resource<ResponsePeliculasPopulares>

         //suspend fun getPeliculasPopulares(page:Int): LiveData<MutableList<Pelicula>>

                suspend fun getDeatllesPelicula(id_movie: Int): Resource<PeliculaDetalles>
}
