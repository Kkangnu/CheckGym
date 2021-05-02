package net.onerm.checkgym.kiosk.activity

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


class KioskMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiosk_main)

        initView()
    }

    private fun initView(){

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}