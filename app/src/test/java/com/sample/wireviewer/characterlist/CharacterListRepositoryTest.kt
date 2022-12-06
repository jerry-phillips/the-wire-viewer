package com.sample.wireviewer.characterlist

import com.sample.wireviewer.model.Character
import com.sample.wireviewer.model.RequestData
import com.sample.wireviewer.services.DuckDuckGoResponse
import com.sample.wireviewer.services.DuckDuckGoService
import kotlinx.coroutines.test.runTest
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
        runTest {
            whenever(duckGoService.getWireCharacters(WIREQUERY, DATAFORMAT)).thenReturn(response)
            whenever(response.body()).thenReturn(requestData)
            whenever(response.isSuccessful).thenReturn(true)
            val subject = CharacterListRepository(duckGoService)
            val result = subject.getCharacters() as DuckDuckGoResponse.Success

            verify(duckGoService).getWireCharacters(WIREQUERY, DATAFORMAT)
            assertEquals(characterList, result.data)
        }
    }
}