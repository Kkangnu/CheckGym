package net.onerm.checkgym.kiosk.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.onerm.checkgym.R
import net.onerm.checkgym.const.ACT_MODE
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.service.CheckGymApi


class KioskMainActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(CheckGymApi.postKiosk(ACT_MODE.GET_NOTICE, CheckGymConsts.API_VALUE_COMNO)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: String ->
                //for (item in response.items) {
                    Log.d("MainActivity", response)
//                }
            }, { error: Throwable ->
                Log.d("MainActivity", error.localizedMessage)
                Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}