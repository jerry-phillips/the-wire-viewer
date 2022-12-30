package com.sample.wireviewer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.wireviewer.R
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import com.sample.wireviewer.ui.theme.TheWireTheme


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharacterListView(viewModel: CharacterListViewModel, onItemClick: (String?, String?) -> Unit) {

    val charactersData: CharacterData by viewModel.characters.collectAsStateWithLifecycle()

    TheWireTheme {
        Scaffold(topBar = {
            ExpandableSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = {query ->
                    if (query.isNotEmpty()) {
                        viewModel.queryCharacters(query)
                    } else {
                        viewModel.resetCharacterData()
                    }
                },
                onSearchDisplayClosed = {
                    viewModel.resetCharacterData()
                }
            )
        }) { innerPadding ->
            when (charactersData) {
                is CharacterData.NoData -> {
                    ShowMessageDialog(message = R.string.no_data)
                }
                is CharacterData.Empty -> {
                    ShowProgessView()
                }
                is CharacterData.Success -> {
                    val modifier = Modifier.fillMaxSize().padding(innerPadding)
                    CharacterList((charactersData as CharacterData.Success).data, onItemClick, modifier)
                }
                is CharacterData.Error -> {
                    ShowMessageDialog(message = R.string.something_went_wrong)
                }
            }
        }
    }

}

@Composable
private fun CharacterList(
    characters: List<Character>,
    onItemClick: (String?, String?) -> Unit,
    modifier: Modifier
){
    LazyColumn(
        modifier = modifier
    ) {
        items(characters) { character ->
            CharacterRow(character = character, onItemClick = onItemClick)
        }
    }
}

@Composable
private fun CharacterRow(character: Character, onItemClick: (String?, String?) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onItemClick.invoke(character.icon?.url, character.text)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SetImage(size = 40.dp, character.icon?.url)
        Text(
            modifier = Modifier.padding(start = 16.dp),
            fontWeight = FontWeight.Bold,
            text = Character.getCharacterName(character.text) ?: ""
        )

    }
}

