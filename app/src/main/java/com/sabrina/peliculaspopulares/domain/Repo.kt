package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.vo.Resource

interface Repo {

        fun getPeliculasPopulares():Resource<List<Pelicula>>
}