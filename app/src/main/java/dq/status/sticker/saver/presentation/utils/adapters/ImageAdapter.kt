package dq.status.sticker.saver.presentation.utils.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dq.status.sticker.saver.R
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.ui.activity.ViewStatusActivity
import dq.status.sticker.saver.presentation.utils.Constants

class ImageAdapter(private val context: Context, private val items: ArrayList<StatusItemLocal>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.status_image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = items[position]
        // holder.imageView.setImageResource(item.uri)
        Glide.with(context).load(item.uri.toUri()).into(holder.imageView)
        holder.imageView.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    ViewStatusActivity::class.java
                ).putExtra("statusType", Constants.MEDIA_TYPE_IMAGE).putExtra("url", item.uri)
                    .putExtra("fileName", item.fileName)
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }
}
