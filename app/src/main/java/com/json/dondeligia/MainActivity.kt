package com.json.dondeligia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.json.dondeligia.ui.theme.DondeLigiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DondeLigiaTheme {
                MainScreen()
            }
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
            0 -> Catalogs()
            1 -> Promotions()
            2 -> AboutMe()
        }
    }
}


@Composable
fun Catalogs() {
    Text(text = "Catálogos")
}

@Composable
fun Promotions() {
    Text(text = "Promociones")
}

@Composable
fun AboutMe() {
    Text(text = "Acerca de Mí")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    DondeLigiaTheme {
        MainScreen()
    }
}