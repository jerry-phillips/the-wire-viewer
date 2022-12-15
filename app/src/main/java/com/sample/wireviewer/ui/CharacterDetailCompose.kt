package com.sample.wireviewer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sample.wireviewer.model.Character
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sample.wireviewer.ui.theme.TheWireTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailView(
    navController: NavController = rememberNavController(),
    url: String?,
    text: String?
) {
    TheWireTheme {
        Scaffold(
            topBar = { StandardTopAppBar(navController = navController, stringResource = null) }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    SetImage(size = 100.dp, url = url)
                }
                Column {
                    Text(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        text = Character.getCharacterName(text) ?: ""
                    )
                }

                Column(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
                    Text(
                        fontSize = 14.sp,
                        text = Character.getCharacterDescription(text) ?: ""
                    )
                }

            }
        }
    }
}