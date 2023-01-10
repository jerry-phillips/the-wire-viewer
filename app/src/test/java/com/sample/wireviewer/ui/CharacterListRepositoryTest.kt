package com.sample.wireviewer.ui

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.model.RequestData
import com.sample.wireviewer.services.DuckDuckGoService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

internal class CharacterListRepositoryTest : BaseTest() {

    @Test
    fun `verify fetch character data`() {
        val duckGoService: DuckDuckGoService = mock()
        val response: Response<RequestData> = mock()
        val characterList = listOf(Character())
        val requestData = RequestData(characterList)
        val subject = CharacterListRepository(duckGoService)
        runBlocking {
            whenever(duckGoService.getWireCharacters("the wire characters", "json")).thenReturn(response)
            whenever(response.body()).thenReturn(requestData)
            whenever(response.isSuccessful).thenReturn(true)
            val result = subject.getCharacters()

            verify(duckGoService).getWireCharacters("the wire characters", "json")
            assertEquals(characterList, result)
        }
    }
}