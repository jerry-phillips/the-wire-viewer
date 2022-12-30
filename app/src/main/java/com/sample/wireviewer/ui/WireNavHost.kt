package com.sample.wireviewer.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigate() {
    val navigationController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navigationController,
        startDestination = Screen.CharacterListScreen.route
    ) {
        composable(route = Screen.CharacterListScreen.route) {
            val viewModel: CharacterListViewModel = hiltViewModel()
            val navigateToDestination = { url: String?, text: String? ->
                viewModel.resetCharacterData()
                val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                navigationController.navigate(
                    "${Screen.CharacterDetailScreen.route}/${encodedUrl}/${text}"
                )
            }
            CharacterListView(viewModel, navigateToDestination)
        }
        composable(route = "${Screen.CharacterDetailScreen.route}/{url}/{text}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("text") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = { -> slideInHorizontally(animationSpec = tween(500)) },
            exitTransition = { -> slideOutHorizontally(animationSpec = tween(
                500)) }){ entry ->
            val hasBackStackEntry = navigationController.previousBackStackEntry != null

            CharacterDetailView(
                hasBackStackEntry = hasBackStackEntry,
                navigateToDestination = { navigationController.navigateUp() },
                url = entry.arguments?.getString("url"),
                text = entry.arguments?.getString("text"))
        }
    }
}

private const val CHARACTERLISTSCREEN = "characterlistscreen"
private const val CHARATERDETAILSCREEN = "characterdetailview"
sealed class Screen(val route: String) {
    object CharacterListScreen: Screen(CHARACTERLISTSCREEN)
    object CharacterDetailScreen: Screen(CHARATERDETAILSCREEN)

}