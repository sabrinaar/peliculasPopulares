package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.vo.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getPeliculasPopulares(): Resource<List<Pelicula>> {
        return dataSource.getPeliculasPopulares()
    }

    override suspend fun getDeatllesPelicula(id_movie: Int): Resource<PeliculaDetalles> {
        return dataSource.getDetallesPelicula(id_movie)
    }


}