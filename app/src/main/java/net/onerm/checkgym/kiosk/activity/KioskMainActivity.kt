package net.onerm.checkgym.kiosk.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.kiosk.model.KioskRequestModel
import net.onerm.checkgym.service.CheckGymApi
import org.w3c.dom.Text


class KioskMainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var btnNetworkNoti : Button
    private lateinit var btnNetworkCheckout : Button
    private lateinit var btnNetworkLoginCheck : Button

    private lateinit var txResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiosk_main)

        btnNetworkNoti = findViewById<Button>(R.id.btn_network_noti)
        btnNetworkCheckout = findViewById<Button>(R.id.btn_network_checkout)
        btnNetworkLoginCheck = findViewById<Button>(R.id.btn_network_logincheck)
        txResult = findViewById<Button>(R.id.tx_result)

        btnNetworkNoti.setOnClickListener(View.OnClickListener {
            getNotice()
        })

        btnNetworkCheckout.setOnClickListener(View.OnClickListener {
            getCheckLogin()
        })

        btnNetworkLoginCheck.setOnClickListener(View.OnClickListener {
            getLoginCheck()
        })
    }

    //
    private fun getLoginCheck() {
        compositeDisposable = CompositeDisposable()

        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_MEMBER_CHECKOUT.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO
        kioskRequestModel.custno = CheckGymConsts.API_VALUE_CUSTOMER_NUMBER
        kioskRequestModel.ucategory = CheckGymConsts.API_VALUE_CATEGROY
//            var map :MutableMap<String, String> = mutableMapOf()
//            map[CheckGymConsts.API_BODY_COMNO] = comno;
//            map[CheckGymConsts.API_BODY_ACTMODE] = actMode.action;
//            map[CheckGymConsts.API_BODY_CUSTOMER_NUMBER] = customNum;
//            map[CheckGymConsts.API_BODY_CATEGORY] = ucategory;

        compositeDisposable.add(CheckGymApi.postMember(ACT_MODE.GET_MEMBER_CHECKOUT, kioskRequestModel)
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

    // 로그인 체크
    private fun getCheckLogin() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postMember(ACT_MODE.GET_MEMBER_CHECKOUT, CheckGymConsts.API_VALUE_COMNO, CheckGymConsts.API_VALUE_CUSTOMER_NUMBER)
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

    // 공지사항
    private fun getNotice(){
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postKiosk(ACT_MODE.GET_NOTICE, CheckGymConsts.API_VALUE_COMNO)
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}