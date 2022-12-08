package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.DuckDuckGoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CharacterListViewModelTest: BaseTest() {

    private lateinit var subject: CharacterListViewModel
    private val repository: CharacterListRepository = mock()
    private val characterList = mutableListOf<Character>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify character data is set`() {
        runTest {
            val data = DuckDuckGoResponse.Success(listOf(mock()))
            whenever(repository.getCharacters()).thenReturn(data)
            subject = CharacterListViewModel(repository, characterList)

            verify(repository).getCharacters()
            assertEquals(data, subject.characters.value)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify error is set on error`() {
        runTest {
            val error = DuckDuckGoResponse.Error
            whenever(repository.getCharacters()).thenReturn(error)
            subject = CharacterListViewModel(repository, characterList)

            verify(repository).getCharacters()
            assertEquals(error, subject.characters.value)
        }
    }

    @Test
    fun `verify querying of data`(){
        runTest {
            val characterData = DuckDuckGoResponse.Success(listOf(Character(text = "Idris Elba "),
                Character(text = "Lucy")))
            whenever(repository.getCharacters()).thenReturn(characterData)
            subject = CharacterListViewModel(repository, characterList)
            characterList.addAll(characterData.data)
            subject.queryCharacters("Idris")

            assertEquals(listOf(Character(text = "Idris Elba ")), (subject.characters.value as DuckDuckGoResponse.Success).data)
        }
    }

    @Test
    fun `verify data is reset`() {
        runTest {
            val characterData = DuckDuckGoResponse.Success(listOf(Character(text = "Idris Elba "),
                Character(text = "Lucy")))
            val data = DuckDuckGoResponse.Success(listOf(mock()))
            whenever(repository.getCharacters()).thenReturn(data)
            subject = CharacterListViewModel(repository, characterList)
            characterList.addAll(characterData.data)
            subject.queryCharacters("Idris")
            subject.resetData()
            assertEquals(characterData.data, (subject.characters.value as DuckDuckGoResponse.Success).data)
        }
    }
}