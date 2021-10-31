package com.sabrina.peliculaspopulares.domain

import com.sabrina.peliculaspopulares.data.model.*
import com.sabrina.peliculaspopulares.vo.Resource
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Sabrina Arrebillaga
 */
/*GET para hacer endpoints a URL base*/
interface WebService {

    @GET("movie/popular")
    suspend fun getPopularesPeliculas(@Query("page") page: Int): ResponsePeliculasPopulares


    @GET("movie/{movie_id}")
    suspend fun getDetallesPelicula(@Path("movie_id") id: Int): PeliculaDetalles

    //https://api.themoviedb.org/3/authentication/guest_session/new?api_key=210c2545bb0679992dd2cb84412b0906
    @GET("authentication/guest_session/new")
    suspend fun getGuestSession() : ResponseSession



    //Ejemplo: https://api.themoviedb.org/3/movie/831405/rating?api_key=210c2545bb0679992dd2cb84412b0906&guest_session_id=50f0013c5dd604b6e56dfede2fd81762

    @Headers("Content-Type: application/json")
    @POST("movie/{movie_id}/rating")
    suspend fun setRatingPelicula(@Path("movie_id") movie_id: Int, @Query("guest_session_id") guest_session_id: String, @Body rating: RatingUser): ResponseRating


}

