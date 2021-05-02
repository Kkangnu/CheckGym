package net.onerm.checkgym.const

object CheckGymConsts {
    const val CHECK_GYM_HOST = "http://www.onerm.net"
    const val KIOSK_URL = "/api/apiKiosk.php"
    const val MEMBER_URL = "/api/apiMember.php"

    const val API_BODY_COMNO = "comno"
    const val API_BODY_ACTMODE = "actmode"
    const val API_BODY_CUSTOMER_NUMBER = "custno"
    const val API_BODY_CATEGORY = "ucategory"


    const val API_VALUE_COMNO = "1521"
    const val API_VALUE_CUSTOMER_NUMBER = "566465456"
    const val API_VALUE_CATEGROY = "u"
    const val API_VALUE_UID = "566465456"
    const val API_VALUE_MNO = "3180511"
}

enum class ACT_MODE(val action:String) {
    GET_NOTICE("getNoti"),
    GET_MEMBER_CHECKOUT("member_show_checkout"),
    GET_CHECK_LOGIN("checkLogin"),
    GET_MEMBER_INFO("transactionStatus"),
    GET_ADS_1("kiosk_get_ad"),
    GET_ADS_2("kiosk_get_ad2"),
    GET_AUDIO("kiosk_get_audio"),
    SET_SUBSTRACT_LSN("substractLsn")

}