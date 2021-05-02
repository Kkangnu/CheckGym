package net.onerm.checkgym.kiosk.model

data class KioskRequestAudio (
    val code: String,
    val code_nm: String,
    val filename: String,
    val desc: String,
    val sound_type: String,
    val effect_sound: String,
    val human_sound: String,
    val club_upload: String,
    val isscd: String,
    val before_end_days: String
)