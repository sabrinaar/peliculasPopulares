package com.sabrina.peliculaspopulares.data

import com.sabrina.peliculaspopulares.data.model.PeliculaDetalles
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.vo.Resource
import com.sabrina.peliculaspopulares.vo.RetrofitClient
import java.lang.Exception

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
        return Resource.Success(RetrofitClient.webservice.getDetallesPelicula(id_movie))
    }
}