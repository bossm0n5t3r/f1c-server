package me.f1c.job.tasklet

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import me.f1c.domain.admin.AdminService
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.domain.session.SessionService
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class UpToDateTaskletTest {
    private val raceScheduleService = mockk<RaceScheduleService>()
    private val sessionService = mockk<SessionService>()
    private val adminService = mockk<AdminService>(relaxed = true)
    private val sut = UpToDateTasklet(raceScheduleService, sessionService, adminService)

    @Test
    @DisplayName("Sessions 의 가장 최근 경기의 날짜와 현재시간 기준으로 가장 최근에 종료된 경기의 날짜가 같으면 early return")
    fun executeTest0() {
        val now =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.UTC)
                .toString()

        every { sessionService.getLatest() } returns
            mockk {
                every { dateEnd } returns now
            }
        every { raceScheduleService.findLatestFinished() } returns
            mockk {
                every { raceDatetime } returns now
            }

        sut.execute(mockk(relaxed = true), mockk(relaxed = true))

        verify(exactly = 0) { adminService.upToDate() }
    }

    @Test
    @DisplayName("Sessions 의 가장 최근 경기의 날짜가 없으면 AdminService.upToDate 실행")
    fun executeTest1() {
        every { sessionService.getLatest() } returns null
        every { raceScheduleService.findLatestFinished() } returns
            mockk {
                every { raceDatetime } returns
                    Clock.System
                        .now()
                        .toLocalDateTime(TimeZone.UTC)
                        .toString()
                        .also { println(it) }
            }

        sut.execute(mockk(relaxed = true), mockk(relaxed = true))

        verify(exactly = 1) { adminService.upToDate() }
    }

    @Test
    @DisplayName("현재시간 기준으로 가장 최근에 종료된 경기의 날짜가 없으면 AdminService.upToDate 실행")
    fun executeTest2() {
        every { sessionService.getLatest() } returns
            mockk {
                every { dateEnd } returns
                    Clock.System
                        .now()
                        .toLocalDateTime(TimeZone.UTC)
                        .toString()
            }
        every { raceScheduleService.findLatestFinished() } returns null

        sut.execute(mockk(relaxed = true), mockk(relaxed = true))

        verify(exactly = 1) { adminService.upToDate() }
    }

    @Test
    @DisplayName("Sessions 의 가장 최근 경기의 날짜와 현재시간 기준으로 가장 최근에 종료된 경기의 날짜가 다르면 AdminService.upToDate 실행")
    fun executeTest3() {
        every { sessionService.getLatest() } returns
            mockk {
                every { dateEnd } returns
                    Clock.System
                        .now()
                        .toLocalDateTime(TimeZone.UTC)
                        .toString()
            }
        every { raceScheduleService.findLatestFinished() } returns
            mockk {
                every { raceDatetime } returns
                    Clock.System
                        .now()
                        .plus(1, DateTimeUnit.DAY, TimeZone.UTC)
                        .toLocalDateTime(TimeZone.UTC)
                        .toString()
                        .also { println(it) }
            }

        sut.execute(mockk(relaxed = true), mockk(relaxed = true))

        verify(exactly = 1) { adminService.upToDate() }
    }
}
