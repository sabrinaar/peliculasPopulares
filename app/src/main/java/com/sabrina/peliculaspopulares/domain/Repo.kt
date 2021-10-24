package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.vo.Resource

interface Repo {

        suspend fun getPeliculasPopulares(): Resource<List<Pelicula>>

        suspend fun getDeatllesPelicula(id_movie: Int): Resource<PeliculaDetalles>
}
