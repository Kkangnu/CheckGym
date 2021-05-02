package net.onerm.checkgym.kiosk.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


data class KioskNoticeModel (
        val endtype: String,
        val code: Long,
        val msg: String,
        val comno: String,
        val comname: String,
        val mno: String,
        val username: String,
        val photo: String,
        val birthday: String,
        val birthday_yn: String,
        val last_io: String,
        val custno: String,
        val name: String,
        val totCnt: String,
        val Cnt: String,
        val rows: List<KioskResponseAds>,
        val visit: KioaskResponseVisite,
        val cos: KioskResponseCos,
        val lsn: KioskResponseCos,
        val totTran: KioskResponseTran
        //val rows: List<KioskRequestAudio>
        //val kioskResponseAds: List<KioskResponseAds>
) {
    override fun toString(): String {
        return Gson().toJson(this).replace(",", ",\n").replace("(","(\n")
    }
}