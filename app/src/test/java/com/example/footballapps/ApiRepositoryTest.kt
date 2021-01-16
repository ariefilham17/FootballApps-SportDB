package com.example.footballapps

import com.example.footballapps.helpers.networks.ApiRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    private val apiRepository = mock(ApiRepository::class.java)

    @Test
    fun testDoRequestEventNextLeague() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestEventLastLeague() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestEventDetail() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=441613"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestTeamDetail() {
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=441613"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

}