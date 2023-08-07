package com.sample.wireviewer.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sample.wireviewer.model.Character
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sample.wireviewer.ui.theme.TheWireTheme

@Composable
fun CharacterDetailView(
    hasBackStackEntry: Boolean,
    navigateToDestination: ()-> Unit,
    url: String?,
    text: String?
) {
    TheWireTheme {
        Scaffold(
            topBar = { StandardTopAppBar(
                hasBackStackEntry = hasBackStackEntry,
                navigateBack = navigateToDestination,
                stringResource = null) }
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