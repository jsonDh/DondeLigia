package com.json.dondeligia.utils

import android.util.Log
import com.json.dondeligia.data.entities.Brand
import com.json.dondeligia.data.entities.Catalog

object HandleCatalogsList {

    private const val TAG = "HANDLE-CATALOGS"

    fun convertToList(hashMap: HashMap<String, HashMap<String, Any>>): List<Brand> {
        return hashMap.map { (brandName, brandData) ->
            val name = brandData["name"] as? String ?: ""
            val image = brandData["image"] as? String ?: ""
            val catalogData = brandData["catalog"] as? HashMap<*, *>

            val catalogList = if (catalogData != null) {
                listOf(
                    Catalog(
                        link = catalogData["link"] as? String ?: "",
                        type = catalogData["type"] as? String ?: "",
                        date = (catalogData["date"] as? Long) ?: 0L
                    )
                )
            } else {
                emptyList()
            }

            Brand(name = name, image = image, catalogs = catalogList)
        }
    }

}