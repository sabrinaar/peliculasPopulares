package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Sabrina Arrebillaga
 */
/*GET para hacer endpoints a URL base*/
interface WebService {

  //  @GET("popular?api_key=")
  //  suspend fun getListPeliculasPopulares(@Query(value = "api_key") api_key:String): PeliculasPopulares

    @GET("popular")
    suspend fun getPopularesPeliculas(@Query("page") page: Int): ResponsePeliculasPopulares


    @GET("{movie_id}")
    suspend fun getDetallesPelicula(@Path("movie_id") id:Int): PeliculaDetalles

}