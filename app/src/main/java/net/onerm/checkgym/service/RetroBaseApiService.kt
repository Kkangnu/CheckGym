package net.onerm.checkgym.service

import io.reactivex.rxjava3.core.Observable
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class CheckGymApi {
    interface CheckGymApiImpl {
        @FormUrlEncoded
        @POST(CheckGymConsts.CHECK_GYM_HOST + CheckGymConsts.KIOSK_URL)
        fun postKiosk(@Field(CheckGymConsts.API_BODY_ACTMODE) actMode: ACT_MODE, @Field(CheckGymConsts.API_BODY_COMNO) comno: String) : Observable<String>
    }

    companion object {
        fun postKiosk(@Field(CheckGymConsts.API_BODY_ACTMODE) actMode: ACT_MODE, @Field(CheckGymConsts.API_BODY_COMNO) comno: String) : Observable<String> {
            return RetrofitCreator.create(CheckGymApiImpl::class.java).postKiosk(actMode, comno)
        }
    }
}