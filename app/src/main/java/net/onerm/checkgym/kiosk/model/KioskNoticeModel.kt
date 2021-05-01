package net.onerm.checkgym.kiosk.model

data class KioskNoticeModel (
        val endtype: String,
        val code: Long,
        val msg: String,
        val mno: String,
        val last_io: String,
        var custno: String,
        val name: String
)