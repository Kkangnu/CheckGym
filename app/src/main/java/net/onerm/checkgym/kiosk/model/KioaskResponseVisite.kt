package net.onerm.checkgym.kiosk.model

data class KioaskResponseVisite (
    val mno: String,
    val cntVisit: String,
    val cntVisitMonth: String,
    val cntVisitDay: String,
    val lusecheckoutBtn: String,
    val visitDate: String,
    val visitTime: String,
    val auto_subtraction: String,
    val manual_subtraction: String
)
