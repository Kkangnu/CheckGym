package net.onerm.checkgym.kiosk.model

data class KioskNoticeModel (
        var endtype: String,
        var code: Long,
        var msg: String,
        var comno: String,
        var comname: String,
        var mno: String,
        var username: String,
        var photo: String,
        var birthday: String,
        var birthday_yn: String,
        var last_io: String,
        var custno: String,
        var name: String
) {
    override fun toString(): String {
        return super.toString().replace(",", ",\n").replace("(","(\n")
    }
}