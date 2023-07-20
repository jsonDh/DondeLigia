package com.json.dondeligia.utils

import com.json.dondeligia.data.entities.Brand
import com.json.dondeligia.data.entities.Catalog

object HandleCatalogsList {

    private const val TAG = "HANDLE-CATALOGS"

    fun convertToList(hashMap: HashMap<String, HashMap<String, Any>>): List<Brand> {
        return hashMap.map { (brandName, brandData) ->
            val name = brandData["name"] as? String ?: ""
            val image = brandData["image"] as? String ?: ""
            val catalogs = brandData["catalogs"] as? HashMap<*, *>

            val catalogList: MutableList<Catalog> = mutableListOf()
            if (catalogs != null) {
                for ((key, value) in catalogs){
                    val item = value as HashMap<*,*>
                    catalogList.add(Catalog(
                        link = item["link"] as? String ?: "",
                        type = item["type"] as? String ?: "",
                        date = item["date"] as? Long ?: 0L
                    ))
                }
            }

            Brand(name = name, image = image, catalogs = catalogList)
        }
    }

}