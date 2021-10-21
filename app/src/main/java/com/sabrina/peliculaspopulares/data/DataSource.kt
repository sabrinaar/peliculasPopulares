package com.sabrina.peliculaspopulares.data

import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.vo.Resource

class DataSource {

    fun getPeliculasPopulares():Resource<List<Pelicula>>{
        return Resource.Success(listOf(Pelicula ("","")))
    }
}