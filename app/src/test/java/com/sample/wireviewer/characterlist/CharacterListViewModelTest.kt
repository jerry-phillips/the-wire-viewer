package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.Resource
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


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify character data is set`() {
        runTest {
            whenever(repository.getCharacters()).thenReturn(Resource.Success(listOf(mock())))
            subject = CharacterListViewModel(repository)

            verify(repository).getCharacters()
            assertEquals(1, subject.characters.requireValue().size)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify error is set on error`() {
        runTest {
            val error = Resource.Error()
            whenever(repository.getCharacters()).thenReturn(error)
            subject = CharacterListViewModel(repository)

            verify(repository).getCharacters()
            assertEquals(error, subject.error.requireValue())
        }
    }

    @Test
    fun `verify querying of data`(){
        runTest {
            val character = Character()
            character.text = "Idris Elba "
            whenever(repository.getCharacters()).thenReturn(Resource.Success(listOf(character)))
            subject = CharacterListViewModel(repository)
            subject.characters.value = listOf(character)
            subject.queryCharacters("Idris")

            verify(repository).getCharacters()
            assertEquals(listOf(character), subject.queriedCharacters.getOrAwaitValue())
        }
    }
}