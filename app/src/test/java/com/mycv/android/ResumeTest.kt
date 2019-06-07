package com.mycv.android

import com.mycv.android.data.model.EducationInstitution
import com.mycv.android.data.model.Profile
import com.mycv.android.data.model.Resume
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*

class ResumeTest {

    val resumeMock = mockk<Resume>()

    @Test
    fun `test check complete profile name`() {

        every { resumeMock.profile?.fullName } returns FULL_NAME

        assertEquals(resumeMock.profile?.fullName, FULL_NAME)
        assertNotEquals(resumeMock.profile?.fullName, "Vlad")

        verify {
            resumeMock.profile?.fullName
        }
    }

    @Test
    fun `test check has education`() {

        every { resumeMock.education?.size } returns 1

        assertTrue(resumeMock.education?.size ?: 0 >= 1)

        verify {
            resumeMock.education
        }
    }

    @Test
    fun `test check Alma Mater`() {

        every { resumeMock.education?.get(0) } returns EducationInstitution(title = UNIVERSITY)

        assertEquals(resumeMock.education?.get(0)?.title, UNIVERSITY)
        assertNotEquals(resumeMock.education?.get(0)?.title, "Oxford")

        verify {
            resumeMock.education
        }
    }

    companion object {
        const val FULL_NAME = "Vladyslav"
        const val UNIVERSITY = "Taurida National University"
    }
}
