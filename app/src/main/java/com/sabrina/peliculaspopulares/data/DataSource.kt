package com.sabrina.peliculaspopulares.data

import androidx.lifecycle.MutableLiveData
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.vo.Resource
import com.sabrina.peliculaspopulares.vo.RetrofitClient
import retrofit2.Response
import java.lang.Exception

class DataSource {

    suspend fun getPeliculasPopulares(pageNum: Int) : Resource<ResponsePeliculasPopulares> {

        try{
              return Resource.Success(RetrofitClient.webservice.getPopularesPeliculas(pageNum)
              )
        }catch (e:Exception
        ){
            return Resource.Error("error")
        }

    }

    suspend fun getDetallesPelicula(id_movie:Int):Resource<PeliculaDetalles>{
        return Resource.Success(RetrofitClient.webservice.getDetallesPelicula(id_movie))
    }
}