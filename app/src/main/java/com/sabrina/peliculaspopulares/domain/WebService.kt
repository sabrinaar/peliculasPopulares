package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.vo.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Sabrina Arrebillaga
 */
/*GET para hacer endpoints a URL base*/
interface WebService {

    @GET("popular")
    suspend fun getPopularesPeliculas(@Query("page") page: Int): ResponsePeliculasPopulares


    @GET("{movie_id}")
    suspend fun getDetallesPelicula(@Path("movie_id") id: Int): PeliculaDetalles

}

