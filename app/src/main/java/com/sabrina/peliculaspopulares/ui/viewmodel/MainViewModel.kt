package com.sabrina.peliculaspopulares.ui.viewmodel


import androidx.lifecycle.*
import com.sabrina.peliculaspopulares.data.model.Pelicula
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.domain.Repo
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {

    val list_peliculas_populares: MutableLiveData<Resource<ResponsePeliculasPopulares>> =
        MutableLiveData()
    var pagina_peliculas_a_buscar = 1
    var peliculasResponse: ResponsePeliculasPopulares? = null
    private val peliculaNombre = MutableLiveData<String>()
    var oldPeliculas = mutableListOf<Pelicula>()
    var newPeliculas = mutableListOf<Pelicula>()

    /////////////////////////////// PELICULAS POPULARES VIEWMODEL ////////////////////

    init {
        getPeliculasPopulares()
    }

    fun setPelicula(pelicula_nombre: String) {
        peliculaNombre.value = pelicula_nombre
    }


    /*
    get peliculas populares segun nombre
     */
    fun getPeliculasPopularesFiltro(nombre: String) = viewModelScope.launch {
        setPelicula(nombre)
        list_peliculas_populares.postValue(Resource.Loading())
        val response = Resource.Filtro(peliculasResponse) as Resource<ResponsePeliculasPopulares>
        list_peliculas_populares.postValue(handlePeliculasPopularesResponse(response))

    }

    /*
    Ver la lista de peliculas despues de borrar la pelicula a buscar
     */
    fun getPeliculasPopularesFiltroBack() = viewModelScope.launch {
        list_peliculas_populares.postValue(Resource.Loading())
        val response =
            Resource.FiltroRegresar(peliculasResponse) as Resource<ResponsePeliculasPopulares>
        list_peliculas_populares.postValue(handlePeliculasPopularesResponse(response))

    }

    /*
    get peliculas populares segun la pagina
     */
    fun getPeliculasPopulares() = viewModelScope.launch {
        list_peliculas_populares.postValue(Resource.Loading())
        val response = repo.getPeliculasPopulares(pagina_peliculas_a_buscar)
        list_peliculas_populares.postValue(handlePeliculasPopularesResponse(response))
    }

    private fun handlePeliculasPopularesResponse(response: Resource<ResponsePeliculasPopulares>): Resource<ResponsePeliculasPopulares> {
        lateinit var result: Resource<ResponsePeliculasPopulares>

        when (response) {
            is Resource.FiltroRegresar -> {

                if (peliculasResponse == null) {
                    peliculasResponse = response.data
                } else {
                    response.data.popularPelisList = oldPeliculas
                    newPeliculas = response.data.popularPelisList
                }
                result = Resource.Success(response.data)
            }
            is Resource.Filtro -> {

                response.data.popularPelisList =
                    response.data.popularPelisList.filter { pelicula ->
                        pelicula.titulo.toLowerCase()
                            .contains(peliculaNombre.value.toString().toLowerCase())
                    }.toMutableList()
                newPeliculas = response.data.popularPelisList

                result = Resource.Filtro(response.data)
            }
            is Resource.Success -> {
                pagina_peliculas_a_buscar++
                if (peliculasResponse == null) {
                    peliculasResponse = response.data
                    oldPeliculas = peliculasResponse?.popularPelisList!!
                } else {
                    oldPeliculas = peliculasResponse?.popularPelisList!!
                    newPeliculas = response.data!!.popularPelisList
                    oldPeliculas.addAll(newPeliculas)
                }
                result = Resource.Success(peliculasResponse ?: response.data)
            }
            is Resource.Error -> {
                result = Resource.Error(response.message)
            }
        }
        return result
    }

    ////////////////////////////////// DETALLES PELICULA VIEMODEL ////////////////

    fun peliculaDetalles(id_pelicula: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getDetallesPelicula(id_pelicula))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun setRating(id_pelicula: Int, rating:Float) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.setRating(id_pelicula, rating))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}
