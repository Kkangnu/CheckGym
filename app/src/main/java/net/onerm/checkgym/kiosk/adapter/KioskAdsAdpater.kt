package net.onerm.checkgym.kiosk.adapter

import net.onerm.checkgym.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView


public class KioskAdsAdpater internal constructor(
    sliderItems: List<String>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<KioskAdsAdpater.AdsViewHolder>() {

    private val adsImageItems: List<String>
    private val viewPager2: ViewPager2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return AdsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ads_image, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.setImage(adsImageItems[position])
        if (position == adsImageItems.size - 2) {
            //viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return adsImageItems.size
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(image: String) {
            //imageView.setImageResource(adsImageItems.getImage())
        }
    }

    init {
        this.adsImageItems = sliderItems
        this.viewPager2 = viewPager2
    }
}