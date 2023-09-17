package com.json.dondeligia.presentation.views.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.json.dondeligia.R
import com.json.dondeligia.data.entities.Brand
import com.json.dondeligia.data.entities.Catalog
import com.json.dondeligia.presentation.viewmodel.CatalogsListState
import com.json.dondeligia.presentation.viewmodel.CatalogsListViewModel
import com.json.dondeligia.utils.HandleCatalogsList

@Composable
fun CatalogsList(catalogsListViewModel: CatalogsListViewModel) {
    LaunchedEffect(Unit) {
        catalogsListViewModel.catalogsList.collect { catalogsState ->
            if (catalogsState is CatalogsListState.Initial) {
                catalogsListViewModel.getCatalogs()
            }
        }
    }
    val catalogsListState by catalogsListViewModel.catalogsList.collectAsState()
    when (catalogsListState) {
        is CatalogsListState.Loading -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ShowLoading()
            }
        }

        is CatalogsListState.Success -> {
            val brands = (catalogsListState as CatalogsListState.Success).data
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(brands.size) { index ->
                    CatalogBrand(brands[index])
                }
            }
        }

        else -> {}
    }
}

@Composable
fun CatalogBrand(brand: Brand) {
    val imageRef = Firebase.storage.getReferenceFromUrl(brand.image)
    val showCatalogsDialog = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.clickable {
            Log.d("CATALOG-LIST", "showCatalogsDialog = true")
            showCatalogsDialog.value = true
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            GlideImage(
                model = imageRef,
                contentDescription = brand.name,
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.purple_200),
                        CircleShape
                    )
                    .width(80.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = brand.name,
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Catálogos: ${brand.catalogs.size}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    Divider(
        modifier = Modifier
            .padding(100.dp, 0.dp, 10.dp, 0.dp)
            .alpha(0.3f)
    )

    if (showCatalogsDialog.value) {
        Log.d("CATALOG-LIST", "showCatalogsDialog is true")
        ShowCatalogsDialog(brand)
    }
}

@Composable
fun ShowCatalogsDialog(brand: Brand) {
    //val brand = HandleCatalogsList.getMockBrand()
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value){
        Log.d("CATALOG-LIST", "openDialog is true")
        AlertDialog(
            onDismissRequest = {
                Log.d("CATALOG-LIST", "openDialog = false")
                openDialog.value = false
            },
            confirmButton = { /*TODO*/ },
            title = {
                Text(
                    text = "Catálogos de ${brand.name}",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    brand.catalogs.forEach {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = false,
                                    onClick = { },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = false,
                                onClick = { /*TODO*/ },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(text = it.name, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        Log.d("CATALOG-LIST", "openDialog = false")
                        openDialog.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


@Composable
fun ShowLoading() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary
    )
}