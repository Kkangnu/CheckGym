package net.onerm.checkgym.kiosk.activity

import android.content.Intent
import android.graphics.Color.red
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.asksira.loopingviewpager.LoopingViewPager
import com.asksira.loopingviewpager.indicator.CustomShapePagerIndicator
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
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
    lateinit var viewPagerImageSlider: LoopingViewPager
    lateinit var kioskAdsAdpater: KioskAdsAdapter
    lateinit var textValue: AppCompatEditText

    lateinit var customShapePagerIndicator: CustomShapePagerIndicator

    lateinit var btnNum0: AppCompatButton
    lateinit var btnNum1: AppCompatButton
    lateinit var btnNum2: AppCompatButton
    lateinit var btnNum3: AppCompatButton

    lateinit var btnNum4: AppCompatButton
    lateinit var btnNum5: AppCompatButton
    lateinit var btnNum6: AppCompatButton
    lateinit var btnNum7: AppCompatButton

    lateinit var btnNum8: AppCompatButton
    lateinit var btnNum9: AppCompatButton
    lateinit var btnNumDel: AppCompatButton
    lateinit var btnNumCheck: AppCompatButton


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


        // 광고 인디케이터
        customShapePagerIndicator = findViewById(R.id.indicator)
        viewPagerImageSlider.onIndicatorProgress = { selectionPosition , progress ->
            customShapePagerIndicator.onPageScrolled (selectionPosition, progress)
        }



        customShapePagerIndicator.highlighterViewDelegate = {
            val highlighter = View(this)
            highlighter.layoutParams = FrameLayout.LayoutParams(32.dp(), 4.dp())
            highlighter.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            highlighter
        }
        customShapePagerIndicator.unselectedViewDelegate = {
            val unselected = View(this)
            unselected.layoutParams = LinearLayout.LayoutParams(32.dp(), 4.dp())
            unselected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            unselected.alpha = 0.4f
            unselected
        }



        //입력 번호
        textValue = findViewById(R.id.txt_value)
        // 버튼
        btnNum0 = findViewById(R.id.num_0)
        btnNum1 = findViewById(R.id.num_1)
        btnNum2 = findViewById(R.id.num_2)
        btnNum3 = findViewById(R.id.num_3)

        btnNum4 = findViewById(R.id.num_4)
        btnNum5 = findViewById(R.id.num_5)
        btnNum6 = findViewById(R.id.num_6)
        btnNum7 = findViewById(R.id.num_7)

        btnNum8 = findViewById(R.id.num_8)
        btnNum9 = findViewById(R.id.num_9)
        btnNumDel = findViewById(R.id.num_del)
        btnNumCheck = findViewById(R.id.num_chek)

        val obBtnNum0 = btnNum0.clicks().map { btnNum0 }
        val obBtnNum1 = btnNum1.clicks().map { btnNum1 }
        val obBtnNum2 = btnNum2.clicks().map { btnNum2 }
        val obBtnNum3 = btnNum3.clicks().map { btnNum3 }

        val obBtnNum4 = btnNum4.clicks().map { btnNum4 }
        val obBtnNum5 = btnNum5.clicks().map { btnNum5 }
        val obBtnNum6 = btnNum6.clicks().map { btnNum6 }
        val obBtnNum7 = btnNum7.clicks().map { btnNum7 }

        val obBtnNum8 = btnNum8.clicks().map { btnNum8 }
        val obBtnNum9 = btnNum9.clicks().map { btnNum9 }
        val obBtnNumDel = btnNumDel.clicks().map { btnNumDel }
        val obBtnNumCheck = btnNumCheck.clicks().map { btnNumCheck }

        val disposable = Observable.mergeArray(obBtnNum0, obBtnNum1, obBtnNum2, obBtnNum3,
                obBtnNum4, obBtnNum5, obBtnNum6, obBtnNum7,
                obBtnNum8, obBtnNum9, obBtnNumDel, obBtnNumCheck).subscribe {
            when(it) {
                btnNum0 -> {
                    textValue.setText(textValue.text.toString() + "0")
                }
                btnNum1 -> {
                    textValue.setText(textValue.text.toString() + "1")
                }
                btnNum2 -> {
                    textValue.setText(textValue.text.toString() + "2")
                }
                btnNum3 -> {
                    textValue.setText(textValue.text.toString() + "3")
                }
                btnNum4 -> {
                    textValue.setText(textValue.text.toString() + "4")
                }
                btnNum5 -> {
                    textValue.setText(textValue.text.toString() + "5")
                }
                btnNum6 -> {
                    textValue.setText(textValue.text.toString() + "6")
                }
                btnNum7 -> {
                    textValue.setText(textValue.text.toString() + "7")
                }
                btnNum8 -> {
                    textValue.setText(textValue.text.toString() + "8")
                }
                btnNum9 -> {
                    textValue.setText(textValue.text.toString() + "9")
                }
                btnNumDel -> {
                    if(textValue.text.toString().isNotEmpty()) {
                        textValue.setText(textValue.text?.subSequence(0, textValue.text!!.length - 1))
                    }

                }
                btnNumCheck -> {
                        getCheckLogin(textValue.text.toString())
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
                    customShapePagerIndicator.updateIndicatorCounts(viewPagerImageSlider.indicatorCount)
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