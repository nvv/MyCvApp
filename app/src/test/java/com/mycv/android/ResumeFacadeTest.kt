package com.mycv.android

import com.mycv.android.core.NetworkManager
import com.mycv.android.core.Storage
import com.mycv.android.data.facade.ResumeFacade
import com.mycv.android.data.service.ResumeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.Assert
import org.junit.Test

class ResumeFacadeTest {

    val resumeService = mockk<ResumeService>()
    val networkManager = mockk<NetworkManager>()
    val storage = mockk<Storage>()

    val resumeFacade = ResumeFacade(resumeService, networkManager, storage)

    @Test
    fun `test check null response from empty json`() {

        every { resumeService.getResumeRaw() } returns EMPTY_RESPONSE
        every { networkManager.isNetworkAvailable() } returns true
        every { storage.resumeUpdateTimestap } returns 0
        every { storage.putResumeJsonToCache(EMPTY_RESPONSE) } returns Unit

        Assert.assertEquals(resumeFacade.getResume(), null)

        verifyOrder {
            networkManager.isNetworkAvailable()
            storage.resumeUpdateTimestap
            resumeService.getResumeRaw()
            storage.putResumeJsonToCache(EMPTY_RESPONSE)
        }
    }

    @Test
    fun `test check response from profile only json`() {

        every { networkManager.isNetworkAvailable() } returns true
        every { storage.resumeUpdateTimestap } returns 0
        every { resumeService.getResumeRaw() } returns RESPONSE_PROFILE_ONLY
        every { storage.putResumeJsonToCache(RESPONSE_PROFILE_ONLY) } returns Unit

        val resume = resumeFacade.getResume()
        Assert.assertNotNull(resume)
        Assert.assertNotNull(resume?.profile)
        Assert.assertNull(resume?.contacts)
        Assert.assertNull(resume?.education)
        Assert.assertNull(resume?.experience)
        Assert.assertNull(resume?.skillMap)
        Assert.assertNull(resume?.objectiveNotes)

        verifyOrder {
            networkManager.isNetworkAvailable()
            storage.resumeUpdateTimestap
            resumeService.getResumeRaw()
            storage.putResumeJsonToCache(RESPONSE_PROFILE_ONLY)
        }
    }

    @Test
    fun `test check get data from cache`() {

        every { networkManager.isNetworkAvailable() } returns true
        every { storage.resumeUpdateTimestap } returns System.currentTimeMillis()
        every { storage.getResumeJson() } returns RESPONSE_PROFILE_ONLY

        val resume = resumeFacade.getResume()
        Assert.assertNotNull(resume)

        verifyOrder {
            networkManager.isNetworkAvailable()
            storage.resumeUpdateTimestap
            storage.getResumeJson()
        }
    }

    @Test
    fun `test check get data from cache when no internet connection`() {

        every { networkManager.isNetworkAvailable() } returns false
        every { storage.getResumeJson() } returns RESPONSE_PROFILE_ONLY

        val resume = resumeFacade.getResume()
        Assert.assertNotNull(resume)

        verifyOrder {
            networkManager.isNetworkAvailable()
            storage.getResumeJson()
        }
    }

    @Test
    fun `test time to update resume not empty response`() {

        every { networkManager.isNetworkAvailable() } returns true
        every { storage.resumeUpdateTimestap } returns ResumeFacade.RELOAD_RESUME_PERIOD - 1000L
        every { resumeService.getResumeRaw() } returns RESPONSE_PROFILE_ONLY
        every { storage.putResumeJsonToCache(RESPONSE_PROFILE_ONLY) } returns Unit

        val resume = resumeFacade.getResume()
        Assert.assertNotNull(resume)

        verifyOrder {
            networkManager.isNetworkAvailable()
            storage.resumeUpdateTimestap
            resumeService.getResumeRaw()
            storage.putResumeJsonToCache(RESPONSE_PROFILE_ONLY)
        }
    }

    companion object {
        const val EMPTY_RESPONSE = ""

        const val RESPONSE_PROFILE_ONLY = "  {" +
                "\"profile\": {\n" +
                "    \"full_name\":\"Vladyslav Namashko\",\n" +
                "    \"profession\":\"Software Engineer\"\n" +
                "  }" +
                "}"
    }
}