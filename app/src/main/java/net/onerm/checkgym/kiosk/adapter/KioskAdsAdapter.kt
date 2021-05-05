package net.onerm.checkgym.kiosk.adapter

import android.util.Log
import net.onerm.checkgym.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.asksira.loopingviewpager.LoopingViewPager
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskResponseAds


class KioskAdsAdapter internal constructor(
        adsImageItems: List<KioskResponseAds>,
        viewPager: LoopingViewPager
) : LoopingPagerAdapter<KioskResponseAds>(adsImageItems, true) {
    private var adsImageItems: List<KioskResponseAds>
    private var viewPager: LoopingViewPager

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context).inflate(
            R.layout.item_ads_image, container, false
        )
    }

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        var regex = Regex("""/(?:.*?).*[a-z]""")
        val strUrl = regex.find(adsImageItems[listPosition].img_url)!!.value
        Log.d("MainActivity", strUrl)

        val imageView: RoundedImageView = convertView.findViewById(R.id.imageSlide)

        Glide.with(convertView.context).load(CheckGymConsts.CHECK_GYM_HOST + strUrl).into(imageView);
    }

    init {
        this.adsImageItems = adsImageItems
        this.viewPager = viewPager
    }
}