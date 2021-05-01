package net.onerm.checkgym.const

object CheckGymConsts {
    const val CHECK_GYM_HOST = "http://www.onerm.net"
    const val KIOSK_URL = "/api/apiKiosk.php"
    const val MEMBER_URL = "/api/apiMember.php"

    const val API_BODY_COMNO = "comno"
    const val API_BODY_ACTMODE = "actmode"
    const val API_BODY_CUSTOMER_NUMBER = "custno"

    const val API_VALUE_COMNO = "1521"
    const val API_VALUE_CUSTOMER_NUMBER = "566465456"
}

enum class ACT_MODE(val action:String) {
    GET_NOTICE("getNoti"), GET_MEMBER_CHECKOUT("member_show_checkout")
}