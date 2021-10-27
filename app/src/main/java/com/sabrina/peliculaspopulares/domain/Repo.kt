package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.vo.Resource

interface Repo {

    suspend fun getPeliculasPopulares(page: Int): Resource<ResponsePeliculasPopulares>

    suspend fun getDeatllesPelicula(id_movie: Int): Resource<PeliculaDetalles>
}
