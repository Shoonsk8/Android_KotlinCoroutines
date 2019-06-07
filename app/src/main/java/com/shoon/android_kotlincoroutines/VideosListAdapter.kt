package com.shoon.android_kotlincoroutines

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.character_list_item.view.*
import kotlinx.android.synthetic.main.footer_layout.view.*
import kotlinx.coroutines.*

class CharacterDiffTool(private val oldData: List<VideosList>, private val newData: List<VideosList>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldData[oldItemPosition] == newData[newItemPosition]

}

class VideosListAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "VideoListAdapter"
        private const val ITEMS_PER_QUERY = 10
        private const val TYPE_FOOTER = 1
        private const val TYPE_ITEM = 2
    }

    private val data = mutableListOf<VideosList>()

    private val dataJob = Job()
    private val workerScope = CoroutineScope(Dispatchers.Main + dataJob)

    open class VideoItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val videoImageView: ImageView = view.character_image
        val videoNameView: TextView = view.character_name
        val newsNameView: TextView = view.character_publisher
        val imageJob = Job()
        val imageScope = CoroutineScope(Dispatchers.Main + imageJob)

        fun bindModel(videos: VideosList){
            videoNameView.text = videos.name
            newsNameView.text = videos.news_name

            imageScope.launch {
                var image: Bitmap? = null
                withContext(Dispatchers.IO){
                    SpacetelescopeDao.getVideoImage(videos)?.let { it -> image = it }

                }
                image.let {
                    if (videoNameView.text == videos.name){
                        videoImageView.setImageBitmap(it)
                    }
                }
            }
        }
    }

    open class FooterViewHolder(view: View): RecyclerView.ViewHolder(view){
        val countTextView: TextView = view.count_view
    }

    init {
        getList()
    }

    private fun getList(offset: Int = 0, limit: Int = ITEMS_PER_QUERY){
        Log.i(TAG, "Querying $offset")
        workerScope.launch {
            withContext(Dispatchers.IO){
                data.addAll(SpacetelescopeDao.getVideoSuspend(offset, limit))
            }
            notifyDataSetChanged()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        dataJob.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_FOOTER -> //Inflating footer view
                return FooterViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.footer_layout,
                        parent,
                        false
                    )
                )
            else -> //Inflating recycle view item layout
                return VideoItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.character_list_item,
                        parent,
                        false
                    ) as View
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == (data.size - 5)){
            getList(data.size)
        }
        if(holder is FooterViewHolder){
            holder.countTextView.text = data.size.toString()
        }else{
            val itemHolder = holder as VideoItemViewHolder
            itemHolder.bindModel(data[position])
        }
    }

    override fun getItemCount(): Int = data.size + 1

    override fun getItemViewType(position: Int): Int {
        return when{
            position >= data.size -> TYPE_FOOTER
            else -> TYPE_ITEM
        }
    }
}
