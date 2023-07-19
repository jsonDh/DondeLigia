package com.json.dondeligia

import android.app.Application
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class DondeLigiaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Glide.get(this).registry.replace(
            StorageReference::class.java,
            InputStream::class.java,
            FirebaseImageLoader.Factory())
    }

}