package com.json.dondeligia.presentation.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun PromotionsList(){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Button(onClick = { 
            val fv = FloatingView(context)
            fv.open()
        }) {
            Text(text = "Floating View")
        }
    }
}