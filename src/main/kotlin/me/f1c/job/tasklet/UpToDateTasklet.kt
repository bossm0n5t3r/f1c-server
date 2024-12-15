package me.f1c.job.tasklet

import kotlinx.datetime.LocalDateTime
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.domain.session.SessionService
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class UpToDateTasklet(
    private val raceScheduleService: RaceScheduleService,
    private val sessionService: SessionService,
) : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus {
        // Sessions 의 가장 최근 경기의 날짜 가져오기
        val latestSessionDate =
            sessionService
                .getLatest()
                ?.dateEnd
                ?.let { LocalDateTime.parse(it).date }

        // 현재시간 기준으로 가장 최근에 종료된 경기의 날짜 가져오기
        val latestFinishedRaceDate =
            raceScheduleService
                .findLatestFinished()
                ?.raceDatetime
                ?.let { LocalDateTime.parse(it).date }

        // Sessions 의 가장 최근 경기의 날짜와 현재시간 기준으로 가장 최근에 종료된 경기의 날짜가 같으면 early return
        if (latestSessionDate != null && latestFinishedRaceDate != null && latestSessionDate == latestFinishedRaceDate) {
            LOGGER.info("{} execute: {}, {}, {}", LogResult.SUCCEEDED, "early return", latestSessionDate, latestFinishedRaceDate)
            return RepeatStatus.FINISHED
        }

        // Sessions 의 가장 최근 경기의 날짜가 없거나 현재시간 기준으로 가장 최근에 종료된 경기의 날짜가 없거나 둘이 같지 않으면 upToDate 실행
//        adminService.upToDate()
        return RepeatStatus.FINISHED.also {
            LOGGER.info(
                "{} execute: {}, {}, {}",
                LogResult.SUCCEEDED,
                "Done adminService.upToDate()",
                latestSessionDate,
                latestFinishedRaceDate,
            )
        }
    }
}
