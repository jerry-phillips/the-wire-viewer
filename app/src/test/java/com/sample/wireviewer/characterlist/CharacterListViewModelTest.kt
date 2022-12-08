package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CharacterListViewModelTest : BaseTest() {

    private var subject: CharacterListViewModel? = null
    private val repository: CharacterListRepository = mock()
    private val characterList = mutableListOf(Character(text = "Idris Elba "),
    Character(text = "Lucy"))

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify character data is set`() {
        runTest {
            val data = CharacterData.Success(listOf())
            whenever(repository.getCharacters()).thenReturn(data)
            setUpTestSubject()
            verify(repository).getCharacters()
            assertEquals(data, subject!!.characters.value)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify error is set on error`() {
        runTest {
            val error = CharacterData.Error
            whenever(repository.getCharacters()).thenReturn(error)
            setUpTestSubject()
            verify(repository).getCharacters()
            assertEquals(error, subject!!.characters.value)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify querying of data`() {
        runTest {

            whenever(repository.getCharacters()).thenReturn(CharacterData.Success(
               mutableListOf()
            ))
            setUpTestSubject()
            delay(5)
            subject!!.queryCharacters("Idris")

            assertEquals(
                listOf(Character(text = "Idris Elba ")),
                (subject!!.characters.value as CharacterData.Success).data
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify data is reset`() {
        runTest {
            val data = CharacterData.Success(characterList)
            whenever(repository.getCharacters()).thenReturn(data)
            setUpTestSubject()
            subject!!.queryCharacters("Idris")
            subject!!.resetData()
            assertEquals(
                characterList,
                (subject!!.characters.value as CharacterData.Success).data
            )
        }
    }

    private fun setUpTestSubject() {
        subject = CharacterListViewModel(repository, characterList)
    }
}