package com.minosai.typingdnahack.ui.tweet.home.rv

import android.view.View
import com.minosai.typingdnahack.R
import com.minosai.typingdnahack.base.BaseAdapter
import com.minosai.typingdnahack.base.BaseViewHolder
import com.minosai.typingdnahack.ui.tweet.model.Tweet
import kotlinx.android.synthetic.main.layout_tweet.view.*

class TweetAdapter(private val interaction: TweetViewHolder.Interaction?) :
    BaseAdapter<Tweet, TweetViewHolder>() {

    override fun getLayoutRes(): Int = R.layout.layout_tweet

    override fun getViewHolder(itemView: View): TweetViewHolder {
        return TweetViewHolder(itemView, interaction)
    }
}

class TweetViewHolder(itemView: View, interaction: Interaction?) : BaseViewHolder<Tweet>(itemView) {

    init {
        itemView.tweetFlag.setOnClickListener {
            interaction?.onTweetFlagClicked(currentData, absoluteAdapterPosition)
        }

        itemView.tweetLike.setOnClickListener {
            interaction?.onTweetLikeClicked(currentData, absoluteAdapterPosition)
        }

        itemView.tweetShare.setOnClickListener {
            interaction?.onTweetShareClicked(currentData, absoluteAdapterPosition)
        }
    }

    override fun bind(data: Tweet) = with(itemView) {
        tweetContent.text = data.content
        tweetFlag.setImageResource(
            if (data.isFlagged) R.drawable.ic_baseline_flag_24
            else R.drawable.ic_baseline_outlined_flag_24
        )
        tweetLike.setImageResource(
            if (data.isLiked) R.drawable.ic_baseline_thumb_up_24
            else R.drawable.ic_outline_thumb_up_24
        )
    }

    interface Interaction {
        fun onTweetFlagClicked(data: Tweet?, position: Int)
        fun onTweetShareClicked(data: Tweet?, position: Int)
        fun onTweetLikeClicked(data: Tweet?, position: Int)
    }
}