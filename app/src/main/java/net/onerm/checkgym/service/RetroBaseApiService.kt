package net.onerm.checkgym.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.reactivex.rxjava3.core.Observable
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.kiosk.model.KioskRequestModel
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*


class CheckGymApi {
    interface CheckGymApiImpl {
        @FormUrlEncoded
        @POST(CheckGymConsts.CHECK_GYM_HOST + CheckGymConsts.KIOSK_URL)
        fun postKiosk(@FieldMap fields: Map<String, String>) : Observable<KioskNoticeModel>

        @FormUrlEncoded
        @POST(CheckGymConsts.CHECK_GYM_HOST + CheckGymConsts.MEMBER_URL)
        fun postMember(@FieldMap fields: Map<String, String>) : Observable<KioskNoticeModel>
    }

    companion object {
        fun postKiosk(actMode: ACT_MODE, comno: String) : Observable<KioskNoticeModel> {
            var map :MutableMap<String, String> = mutableMapOf()
            map[CheckGymConsts.API_BODY_COMNO] = comno;
            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
            return RetrofitCreator.create(CheckGymApiImpl::class.java).postKiosk(map)
        }

        fun postMember(actMode: ACT_MODE, comno: String, customNum: String) : Observable<KioskNoticeModel> {
            var map :MutableMap<String, String> = mutableMapOf()
            map[CheckGymConsts.API_BODY_COMNO] = comno;
            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
            map[CheckGymConsts.API_BODY_CUSTOMER_NUMBER] = customNum;
            return RetrofitCreator.create(CheckGymApiImpl::class.java).postMember(map)
        }

        fun postMember(actMode: ACT_MODE, kioskRequestModel: KioskRequestModel) : Observable<KioskNoticeModel> {
//            var map :MutableMap<String, String> = mutableMapOf()
//            map[CheckGymConsts.API_BODY_COMNO] = comno;
//            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
//            map[CheckGymConsts.API_BODY_CUSTOMER_NUMBER] = customNum;
//            map[CheckGymConsts.API_BODY_CATEGORY] = ucategory;
            val oMapper = ObjectMapper()
            //val map: Map<String, Any> = oMapper.convertValue(kioskNoticeModel, MutableMap<String, Any>::class.java)
            //var map : Map<String, *>? =  oMapper.convertValue(kioskNoticeModel, TypeReference<Map<String, Any>>() {})
            var map : MutableMap<String, String> = oMapper.readValue(oMapper.writeValueAsString(kioskRequestModel))
            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action

            return RetrofitCreator.create(CheckGymApiImpl::class.java).postMember(map)
        }
    }
}