package com.sabrina.peliculaspopulares.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sabrina.peliculaspopulares.domain.Repo
import com.sabrina.peliculaspopulares.vo.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel (private val repo: Repo) : ViewModel() {

    val peliculasList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getPeliculasPopulares())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}