package net.onerm.checkgym.service

import io.reactivex.rxjava3.core.Observable
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
        fun postKiosk(@Field(CheckGymConsts.API_BODY_ACTMODE) actMode: ACT_MODE, @Field(CheckGymConsts.API_BODY_COMNO) comno: String) : Observable<KioskNoticeModel> {
            var map :MutableMap<String, String> = mutableMapOf()
            map[CheckGymConsts.API_BODY_COMNO] = comno;
            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
            return RetrofitCreator.create(CheckGymApiImpl::class.java).postKiosk(map)
        }

        fun postMember(@Field(CheckGymConsts.API_BODY_ACTMODE) actMode: ACT_MODE, @Field(CheckGymConsts.API_BODY_COMNO) comno: String,  @Field(CheckGymConsts.API_BODY_CUSTOMER_NUMBER) customNum: String) : Observable<KioskNoticeModel> {
            var map :MutableMap<String, String> = mutableMapOf()
            map[CheckGymConsts.API_BODY_COMNO] = comno;
            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
            map[CheckGymConsts.API_BODY_CUSTOMER_NUMBER] = customNum;
            return RetrofitCreator.create(CheckGymApiImpl::class.java).postMember(map)
        }
    }
}