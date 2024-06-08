package dq.status.sticker.saver.presentation.utils.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dq.status.sticker.saver.R
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.ui.activity.ViewStatusActivity
import dq.status.sticker.saver.presentation.utils.Constants

class VideoAdapter(private val context: Context, private val items: ArrayList<StatusItemLocal>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.status_video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = items[position]
        holder.bindVideo(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivVideoThumbnail)

        fun bindVideo(item: StatusItemLocal) {
            val videoUri = Uri.parse(item.uri)
            Glide.with(context).load(videoUri).thumbnail(0.5f).into(ivThumbnail)
            ivThumbnail.setOnClickListener {
                context.startActivity(
                    Intent(
                        context,
                        ViewStatusActivity::class.java
                    ).putExtra("statusType", Constants.MEDIA_TYPE_VIDEO).putExtra("url", item.uri)
                        .putExtra("fileName", item.fileName)
                )
            }
        }
    }
}
