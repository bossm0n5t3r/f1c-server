package me.f1c.port.admin

import me.f1c.domain.ResponseDto

interface AdminController {
    fun upToDate(): ResponseDto<Unit>
}
