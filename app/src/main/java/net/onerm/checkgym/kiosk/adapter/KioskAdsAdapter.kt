package net.onerm.checkgym.kiosk.adapter

import android.content.Context
import android.util.Log
import net.onerm.checkgym.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import net.onerm.checkgym.const.CheckGymConsts
import net.onerm.checkgym.kiosk.model.KioskResponseAds


class KioskAdsAdapter internal constructor(
        mContext: Context,
        adsImageItems: List<KioskResponseAds>,
        viewPager2: ViewPager2
) : RecyclerView.Adapter<KioskAdsAdapter.AdsViewHolder>() {
    private var adsImageItems: List<KioskResponseAds>
    private var viewPager2: ViewPager2
    private var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return AdsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ads_image, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        var regex = Regex("""/(?:.*?).*[a-z]""")
        val strUrl = regex.find(adsImageItems[position].img_url)!!.value
        Log.d("MainActivity", strUrl)

        Glide.with(mContext).load(CheckGymConsts.CHECK_GYM_HOST + strUrl).into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return adsImageItems.size
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

    }

    init {
        this.adsImageItems = adsImageItems
        this.viewPager2 = viewPager2
        this.mContext = mContext
    }
}