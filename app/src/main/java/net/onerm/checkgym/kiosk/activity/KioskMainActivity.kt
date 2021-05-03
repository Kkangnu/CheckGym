package net.onerm.checkgym.kiosk.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.adapter.KioskAdsAdapter
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.kiosk.model.KioskRequestModel
import net.onerm.checkgym.service.CheckGymApi


class KioskMainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var viewPagerImageSlider: ViewPager2
    lateinit var kioskAdsAdpater: KioskAdsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiosk_main)

        initView()
        initData()
    }

    private fun initData(){
        // Get광고
        getAds1()
    }

    private fun initView(){
        // 광고
        viewPagerImageSlider = findViewById(R.id.viewPagerImageSlider)
    }

    // 광고1
    private fun getAds1() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_ADS_1.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    private fun makeKioskDispose (kioskRequestModel: KioskRequestModel) {
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postKiosk(kioskRequestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: KioskNoticeModel ->
                    Log.d("MainActivity", response.toString())

                    if(response.rows != null) {
                        kioskAdsAdpater = KioskAdsAdapter(this, response.rows, viewPagerImageSlider)
                        viewPagerImageSlider.adapter = kioskAdsAdpater
                    }
                }, { error: Throwable ->
                    Log.d("MainActivity", error.localizedMessage)
                    Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })
        )
    }

    
    override fun onDestroy() {
        super.onDestroy()
    }
}