package com.sabrina.peliculaspopulares.data

import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.vo.Resource
import com.sabrina.peliculaspopulares.vo.RetrofitClient

class DataSource {

   suspend fun getPeliculasPopulares():Resource<List<Pelicula>>{
        return Resource.Success(RetrofitClient.webservice.getPopularesPeliculas(1).popularPelisList)
    }

    suspend fun getDetallesPelicula(id_movie:Int):Resource<PeliculaDetalles>{
        return Resource.Success(RetrofitClient.webservice.getDetallesPelicula(id_movie))
    }
}