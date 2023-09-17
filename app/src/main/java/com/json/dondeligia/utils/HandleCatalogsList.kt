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
                        name = item["name"] as? String ?: "",
                        link = item["link"] as? String ?: "",
                        type = item["type"] as? String ?: "",
                        date = item["date"] as? Long ?: 0L
                    ))
                }
            }

            Brand(name = name, image = image, catalogs = catalogList)
        }
    }

    fun getMockBrand() : Brand {
        val item1 = Catalog("Hogar", "https://issuu.com/vianney56/docs/vianney_23-24_cr", "url", 1689637147)
        val item2 = Catalog("Bebé", "https://issuu.com/vianney56/docs/vianney_23-24_cr/182", "url", 1689637147)
        val item3 = Catalog("Invierno", "https://issuu.com/vianney56/docs/invierno_22-23_mx_issuu", "url", 1689637147)
        val item4 = Catalog("Teens", "gs://donde-ligia.appspot.com/catalogs/vianney_teens_2023.pdf", "pdf", 1689637147)
        val catalogList: MutableList<Catalog> = mutableListOf()
        catalogList.add(item1)
        catalogList.add(item2)
        catalogList.add(item3)
        catalogList.add(item4)
        return Brand("Vianney", "gs://donde-ligia.appspot.com/logos/vianey.png", catalogList)
    }

}