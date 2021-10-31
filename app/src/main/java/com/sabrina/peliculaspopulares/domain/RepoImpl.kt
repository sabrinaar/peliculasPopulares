package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.DataSource
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.data.model.ResponseRating
import com.sabrina.peliculaspopulares.vo.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {

    override suspend fun getPeliculasPopulares(page: Int): Resource<ResponsePeliculasPopulares> {
        return dataSource.getPeliculasPopulares(page)
    }


    override suspend fun getDetallesPelicula(id_movie: Int): Resource<PeliculaDetalles> {
        return dataSource.getDetallesPelicula(id_movie)
    }

    override suspend fun setRating(id_movie: Int, rating:Float) : Resource<ResponseRating>{
        return dataSource.setRating(id_movie,rating)
    }



}