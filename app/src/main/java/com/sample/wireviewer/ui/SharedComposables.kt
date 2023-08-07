package com.sample.wireviewer.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.wireviewer.R

@Composable
fun SetImage(size: Dp, url: String?) {
    AsyncImage(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        model = "https://www.duckduckgo.com${url}",
        contentDescription = "Character image small",
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.ic_sphere_wireframe_10deg_6r)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTopAppBar(hasBackStackEntry: Boolean, navigateBack: () -> Unit, @StringRes stringResource: Int?) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = stringResource ?: R.string.empty),
            fontWeight = FontWeight.Bold,
        )
    }, navigationIcon = {
        if (hasBackStackEntry) {
            IconButton(onClick = { navigateBack.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    }
    )
}

@Composable
fun ShowProgressView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowMessageDialog(@StringRes message: Int) {
    AlertDialog(
        title = { Text(text = stringResource(id = message)) },
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Ok")
            }
        })
}