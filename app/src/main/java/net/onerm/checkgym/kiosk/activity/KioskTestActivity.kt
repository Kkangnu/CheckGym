package net.onerm.checkgym.kiosk.activity

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.kiosk.model.KioskRequestAudio
import net.onerm.checkgym.kiosk.model.KioskRequestModel
import net.onerm.checkgym.service.CheckGymApi
import java.lang.reflect.Type


class KioskTestActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var imgAds : ImageView
    private lateinit var txResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiosk_test)

        initView()
    }

    private fun initView(){
        imgAds = findViewById(R.id.img_ads_image)


        findViewById<Button>(R.id.btn_move_main).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, KioskMainActivity::class.java))
        })
        findViewById<Button>(R.id.btn_network_noti).setOnClickListener(View.OnClickListener {
            getNotice()
        })
        findViewById<Button>(R.id.btn_network_checkout).setOnClickListener(View.OnClickListener {
            getCheckLogin()
        })
        findViewById<Button>(R.id.btn_network_logincheck).setOnClickListener(View.OnClickListener {
            getLoginCheck()
        })

        findViewById<Button>(R.id.btn_network_member_info).setOnClickListener(View.OnClickListener {
            getMemberInfo()
        })
        findViewById<Button>(R.id.btn_network_ads1).setOnClickListener(View.OnClickListener {
            getAds1()
        })
        findViewById<Button>(R.id.btn_network_ads2).setOnClickListener(View.OnClickListener {
            getAds2()
        })
        findViewById<Button>(R.id.btn_network_audio).setOnClickListener(View.OnClickListener {
            getAudio()
        })

        txResult = findViewById<Button>(R.id.tx_result)
    }

    private fun setSubStractLSN() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_AUDIO.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    // audio
    private fun getAudio() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_AUDIO.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    // 광고1
    private fun getAds1() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_ADS_1.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    // 광고2
    private fun getAds2() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_ADS_2.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    // 회원 정보
    private fun getMemberInfo() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_MEMBER_INFO.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO
        kioskRequestModel.mno = CheckGymConsts.API_VALUE_MNO

        makeMemberDispose(kioskRequestModel)
    }

    // 로그인 체크
    private fun getLoginCheck() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_CHECK_LOGIN.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO
        kioskRequestModel.uid = CheckGymConsts.API_VALUE_UID
        kioskRequestModel.ucategory = CheckGymConsts.API_VALUE_CATEGROY

        makeMemberDispose(kioskRequestModel)
    }

    // 체크 로그인
    private fun getCheckLogin() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_MEMBER_CHECKOUT.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO
        kioskRequestModel.custno = CheckGymConsts.API_VALUE_CUSTOMER_NUMBER

        makeMemberDispose(kioskRequestModel)
    }

    // 공지사항
    private fun getNotice(){
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_NOTICE.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    private fun makeMemberDispose (kioskRequestModel: KioskRequestModel) {
        imgAds.visibility = View.GONE
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postMember(kioskRequestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: KioskNoticeModel ->
                //for (item in response.items) {
                Log.d("MainActivity", response.toString())
                txResult.text = response.toString()
//                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            })
        )
    }

    private fun makeKioskDispose (kioskRequestModel: KioskRequestModel) {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postKiosk(kioskRequestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: KioskNoticeModel ->
                //for (item in response.items) {
                Log.d("MainActivity", response.toString())

                if(response.rows != null) {
//                    val gson =  Gson()
//val waypointList: List<Waypoint> = list.filterIsInstance<Waypoint>()
//                    val type = object : TypeToken<List<KioskRequestAudio>>() {}.type
//                    val className = Gson().fromJson(response.rows , type)


                    if(response.rows[0].img_url != null) {
                        var regex = Regex("""/(?:.*?).*[a-z]""")
                        val strUrl = regex.find(response.rows[0].img_url)!!.value
                        Log.d("MainActivity", strUrl)
                        imgAds.visibility = View.VISIBLE
                        Glide.with(this).load(CheckGymConsts.CHECK_GYM_HOST + strUrl).into(imgAds);
                    } else {
                        imgAds.visibility = View.GONE
                    }
                }
                txResult.text = response.toString()
//                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }
}