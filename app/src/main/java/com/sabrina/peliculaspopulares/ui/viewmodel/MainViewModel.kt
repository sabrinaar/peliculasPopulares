package com.sabrina.peliculaspopulares.ui.viewmodel


import androidx.lifecycle.*
import com.sabrina.peliculaspopulares.data.model.ResponsePeliculasPopulares
import com.sabrina.peliculaspopulares.domain.Repo
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class MainViewModel(private val repo: Repo) : ViewModel() {

    val list_peliculas_populares: MutableLiveData<Resource<ResponsePeliculasPopulares>> =
        MutableLiveData()
    var pagina_peliculas = 1
    var peliculasResponse: ResponsePeliculasPopulares? = null


    fun peliculaDetalles(id_pelicula: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getDeatllesPelicula(id_pelicula))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }


    init {
        getPeliculasPopulares()
    }


    fun getPeliculasPopulares() = viewModelScope.launch {
        list_peliculas_populares.postValue(Resource.Loading())
        val response = repo.getPeliculasPopulares(pagina_peliculas)
        list_peliculas_populares.postValue(handlePeliculasPopularesResponse(response))
    }

    private fun handlePeliculasPopularesResponse(response: Resource<ResponsePeliculasPopulares>): Resource<ResponsePeliculasPopulares> {
        lateinit var result: Resource<ResponsePeliculasPopulares>
        when (response) {
            is Resource.Success -> {
                pagina_peliculas++
                if (peliculasResponse == null) {
                    peliculasResponse = response.data
                } else {
                    val oldPeliculas = peliculasResponse?.popularPelisList
                    val newPeliculas = response.data.popularPelisList
                    oldPeliculas?.addAll(newPeliculas)
                }
                result = Resource.Success(peliculasResponse ?: response.data)
            }
            is Resource.Error -> {
                result = Resource.Error(response.message)
            }
        }
        return result
    }
}
