package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.data.model.ResponseRating
import com.sabrina.peliculaspopulares.vo.Resource

interface Repo {

    suspend fun getPeliculasPopulares(page: Int): Resource<ResponsePeliculasPopulares>

    suspend fun getDetallesPelicula(id_movie: Int): Resource<PeliculaDetalles>

    suspend fun setRating(id_movie: Int, rating:Float) : Resource<ResponseRating>
}
