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


    private val repository: CharacterListRepository = mock()
    private lateinit var subject: CharacterListViewModel
    private val characterList = mutableListOf(Character(text = "Idris Elba "),
    Character(text = "Lucy"))

    @Test
    fun `verify character data is set`() {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(characterList)
            setUpTestSubject()
            verify(repository).getCharacters()
            assertEquals(characterList, (subject.characters.value as CharacterData.Success).data)
        }
    }

    @Test
    fun `verify error is set on error`() {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(null)
            setUpTestSubject()
            verify(repository).getCharacters()
            assertEquals( CharacterData.Error, subject.characters.value)
        }
    }

    @Test
    fun `verify querying of data`() {
        runBlocking {
            whenever(repository.getCharacters()).thenReturn(characterList)
            setUpTestSubject()
            delay(5)
            subject.queryCharacters("Idris")

            assertEquals(
                listOf(Character(text = "Idris Elba ")),
                (subject.characters.value as CharacterData.Success).data
            )
        }
    }

    private fun setUpTestSubject() {
        subject = CharacterListViewModel(repository)
    }
}