package net.onerm.checkgym.kiosk.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.service.CheckGymApi


class KioskMainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable
    private lateinit var btnNetworkNoti : Button
    private lateinit var btnNetworkCheckout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiosk_main)

        btnNetworkNoti = findViewById<Button>(R.id.btn_network_noti)
        btnNetworkCheckout = findViewById<Button>(R.id.btn_network_checkout)

        btnNetworkNoti.setOnClickListener(View.OnClickListener {
            getNotice()
        })

        btnNetworkCheckout.setOnClickListener(View.OnClickListener {
            getCheckLogin()
        })
    }

    // 로그인 체크
    fun getCheckLogin() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postMember(ACT_MODE.GET_MEMBER_CHECKOUT, CheckGymConsts.API_VALUE_COMNO, CheckGymConsts.API_VALUE_CUSTOMER_NUMBER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: KioskNoticeModel ->
                    //for (item in response.items) {
                    Log.d("MainActivity", response.msg)
//                }
                }, { error: Throwable ->
                    Log.d("MainActivity", error.localizedMessage)
                    Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })
        )
    }

    // 공지사항
    fun getNotice(){
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postKiosk(ACT_MODE.GET_NOTICE, CheckGymConsts.API_VALUE_COMNO)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: KioskNoticeModel ->
                    //for (item in response.items) {
                    Log.d("MainActivity", response.msg)
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