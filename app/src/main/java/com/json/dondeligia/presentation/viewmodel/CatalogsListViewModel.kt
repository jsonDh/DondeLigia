package com.json.dondeligia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.json.dondeligia.data.entities.Brand
import com.json.dondeligia.utils.HandleCatalogsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogsListViewModel : ViewModel() {

    private val _catalogsListState = MutableStateFlow<CatalogsListState>(CatalogsListState.Initial)
    val catalogsList : StateFlow<CatalogsListState> = _catalogsListState

    fun getCatalogs(){
        getCatalogsList()
    }

    private fun getCatalogsList(){
        _catalogsListState.value = CatalogsListState.Loading
        val database = Firebase.database
        val reference = database.getReference("brands")
        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as HashMap<String, HashMap<String, Any>>
                _catalogsListState.value = CatalogsListState.Success(HandleCatalogsList.convertToList(data))
            }

            override fun onCancelled(error: DatabaseError) {
                _catalogsListState.value = CatalogsListState.Error(error.message)
            }

        })
    }

    companion object {
        private const val TAG = "CATALOG-VM"
    }

}

sealed class CatalogsListState {
    object Initial : CatalogsListState()
    object Loading : CatalogsListState()
    data class Success(val data : List<Brand>) : CatalogsListState()
    data class Error(val message: String) : CatalogsListState()
}