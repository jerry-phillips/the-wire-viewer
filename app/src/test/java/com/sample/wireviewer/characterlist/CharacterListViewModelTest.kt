package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CharacterListViewModelTest : BaseTest() {


    private lateinit var repository: CharacterListRepository
    private lateinit var subject: CharacterListViewModel
    private val characterList = mutableListOf(Character(text = "Idris Elba "),
    Character(text = "Lucy"))

    @Test
    fun `verify character data is set`() {
        runBlocking {
            setUpTestSubject()
            whenever(repository.getCharacters()).thenReturn(characterList)
            subject.fetchCharacters()
            delay(50)
            assert(subject.characters.value is CharacterData.Success)
        }
    }
    @Test
    fun `verify fetch`() {
        runBlocking {
            setUpTestSubject()
            verify(repository).getCharacters()
        }
    }

    @Test
    fun `verify error is set on error`() {
        runBlocking {
            setUpTestSubject()
            whenever(repository.getCharacters()).thenReturn(null)
            subject.fetchCharacters()
            assert(subject.characters.value is CharacterData.Error)
        }
    }

    @Test
    fun `verify querying of data`() {
        runBlocking {
            setUpTestSubject()
            whenever(repository.getCharacters()).thenReturn(characterList)
            subject.fetchCharacters()
            delay(50)
            subject.queryCharacters("Idris")

            assertEquals(
                listOf(Character(text = "Idris Elba ")),
                (subject.characters.value as CharacterData.Success).data
            )
        }
    }

    private fun setUpTestSubject() {
        repository = mock()
        subject = CharacterListViewModel(repository)
    }
}