package com.sabrina.peliculaspopulares.data

import com.google.gson.*
import com.sabrina.peliculaspopulares.data.model.*
import com.sabrina.peliculaspopulares.vo.Resource
import com.sabrina.peliculaspopulares.vo.RetrofitClient
import java.lang.Exception

import okhttp3.MediaType
import okhttp3.RequestBody







class DataSource {

    suspend fun getPeliculasPopulares(pageNum: Int) : Resource<ResponsePeliculasPopulares> {

        try{
              return Resource.Success(RetrofitClient.webservice.getPopularesPeliculas(pageNum)
              )
        }catch (e:Exception
        ){
            return Resource.Error("Error de conexión")
        }

    }


    suspend fun getDetallesPelicula(id_movie:Int):Resource<PeliculaDetalles>{
        try {
            return Resource.Success(RetrofitClient.webservice.getDetallesPelicula(id_movie))
        }catch (e:Exception){
            return Resource.Error("Error de conexión")
        }
    }



    suspend fun setRating(id_movie: Int, rating:Float) : Resource<ResponseRating>{
        try {

            val session_id:String=RetrofitClient.webservice.getGuestSession().guest_session_id
            val resp: ResponseRating= RetrofitClient.webservice.setRatingPelicula(id_movie, session_id, RatingUser(rating))
            return Resource.Success(resp)
        }catch (e:Exception){
            return Resource.Error("Error al puntuar pelicula")
        }
    }

}