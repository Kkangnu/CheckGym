package net.onerm.checkgym.kiosk.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_ads_view.*
import kotlinx.android.synthetic.main.view_number_view.*
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.adapter.KioskAdsAdapter
import net.onerm.checkgym.kiosk.model.KioskNoticeModel
import net.onerm.checkgym.kiosk.model.KioskRequestModel
import net.onerm.checkgym.service.CheckGymApi
import net.onerm.checkgym.utils.dp


class KioskMainActivity : AppCompatActivity() {
    var compositeDisposable= CompositeDisposable()
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
        viewPagerImageSlider.onIndicatorProgress = { selectionPosition , progress ->
            indicator.onPageScrolled (selectionPosition, progress)
        }

        indicator.highlighterViewDelegate = {
            val highlighter = View(this)
            highlighter.layoutParams = FrameLayout.LayoutParams(32.dp(), 4.dp())
            highlighter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            highlighter
        }
        indicator.unselectedViewDelegate = {
            val unselected = View(this)
            unselected.layoutParams = LinearLayout.LayoutParams(32.dp(), 4.dp())
            unselected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            unselected.alpha = 0.4f
            unselected
        }

        val obBtnNum0 = num_0.clicks().map { num_0 }
        val obBtnNum1 = num_1.clicks().map { num_1 }
        val obBtnNum2 = num_2.clicks().map { num_2 }
        val obBtnNum3 = num_3.clicks().map { num_3 }

        val obBtnNum4 = num_4.clicks().map { num_4 }
        val obBtnNum5 = num_5.clicks().map { num_5 }
        val obBtnNum6 = num_6.clicks().map { num_6 }
        val obBtnNum7 = num_7.clicks().map { num_7 }

        val obBtnNum8 = num_8.clicks().map { num_8 }
        val obBtnNum9 = num_9.clicks().map { num_9 }
        val obBtnNumDel = num_del.clicks().map { num_del }
        val obBtnNumCheck = num_chek.clicks().map { num_chek }

        val disposable = Observable.mergeArray(obBtnNum0, obBtnNum1, obBtnNum2, obBtnNum3,
                obBtnNum4, obBtnNum5, obBtnNum6, obBtnNum7,
                obBtnNum8, obBtnNum9, obBtnNumDel, obBtnNumCheck).subscribe {
            when(it) {
                num_0 -> {
                    txt_value.setText(txt_value.text.toString() + "0")
                }
                num_1 -> {
                    txt_value.setText(txt_value.text.toString() + "1")
                }
                num_2 -> {
                    txt_value.setText(txt_value.text.toString() + "2")
                }
                num_3 -> {
                    txt_value.setText(txt_value.text.toString() + "3")
                }
                num_4 -> {
                    txt_value.setText(txt_value.text.toString() + "4")
                }
                num_5 -> {
                    txt_value.setText(txt_value.text.toString() + "5")
                }
                num_6 -> {
                    txt_value.setText(txt_value.text.toString() + "6")
                }
                num_7 -> {
                    txt_value.setText(txt_value.text.toString() + "7")
                }
                num_8 -> {
                    txt_value.setText(txt_value.text.toString() + "8")
                }
                num_9 -> {
                    txt_value.setText(txt_value.text.toString() + "9")
                }
                num_del -> {
                    if(txt_value.text.toString().isNotEmpty()) {
                        txt_value.setText(txt_value.text?.subSequence(0, txt_value.text!!.length - 1))
                    }

                }
                num_chek -> {
                        getCheckLogin(txt_value.text.toString())
                }
            }
        }

        compositeDisposable.add(disposable)
    }

    // 광고1
    private fun getAds1() {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_ADS_1.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO

        makeKioskDispose(kioskRequestModel)
    }

    // 로그인
    private fun getCheckLogin(cutno: String) {
        var kioskRequestModel = KioskRequestModel()
        kioskRequestModel.actmode = ACT_MODE.GET_MEMBER_CHECKOUT.action
        kioskRequestModel.comno = CheckGymConsts.API_VALUE_COMNO
        kioskRequestModel.custno = cutno //CheckGymConsts.API_VALUE_CUSTOMER_NUMBER

        makeMemberDispose(kioskRequestModel)
    }

    private fun makeKioskDispose (kioskRequestModel: KioskRequestModel) {
        compositeDisposable.add(CheckGymApi.postKiosk(kioskRequestModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: KioskNoticeModel ->
                    Log.d("MainActivity", response.toString())
                    kioskAdsAdpater = KioskAdsAdapter(response.rows, viewPagerImageSlider)
                    viewPagerImageSlider.adapter = kioskAdsAdpater
                    indicator.updateIndicatorCounts(viewPagerImageSlider.indicatorCount)
                }, { error: Throwable ->
                    Log.d("MainActivity", error.localizedMessage)
                    Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })
        )
    }

    private fun makeMemberDispose (kioskRequestModel: KioskRequestModel) {
        compositeDisposable.add(CheckGymApi.postMember(kioskRequestModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: KioskNoticeModel ->
                Log.d("MainActivity", response.toString())
                if(response.code == 0L) {
                    startActivity(Intent(this, KioskDetailActivity::class.java))
                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}