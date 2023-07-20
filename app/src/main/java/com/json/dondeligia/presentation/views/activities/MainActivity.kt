package com.json.dondeligia.presentation.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.json.dondeligia.R
import com.json.dondeligia.presentation.viewmodel.CatalogsListViewModel
import com.json.dondeligia.presentation.views.components.AboutMe
import com.json.dondeligia.presentation.views.components.CatalogsList
import com.json.dondeligia.presentation.views.components.PromotionsList
import com.json.dondeligia.presentation.views.ui.theme.DondeLigiaTheme

class MainActivity : ComponentActivity() {

    private val catalogsListVM: CatalogsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DondeLigiaTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        Scaffold(
            topBar = { TopBar() },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Tabs()
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        WhatsAppFab()
                    }
                }
            }
        )
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

    @Composable
    fun Tabs() {
        var state by remember { mutableStateOf(0) }
        val titles = listOf("Catálogos", "Promociones", "Acerca de Mí")
        Column {
            TabRow(
                selectedTabIndex = state,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = { Text(text = title) }
                    )
                }
            }
            when (state) {
                0 -> CatalogsList(catalogsListVM)
                1 -> PromotionsList()
                2 -> AboutMe()
            }
        }
    }

    @Composable
    fun WhatsAppFab() {
        FloatingActionButton(
                onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Call, "Whats App Contact")
        }
    }

}
