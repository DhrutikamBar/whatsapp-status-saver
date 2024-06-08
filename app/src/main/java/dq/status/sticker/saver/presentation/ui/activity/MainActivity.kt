package dq.status.sticker.saver.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dq.status.sticker.saver.R
import dq.status.sticker.saver.databinding.ActivityMainBinding


import dq.status.sticker.saver.presentation.ui.fragments.ImageStatusFragment
import dq.status.sticker.saver.presentation.ui.fragments.VideoStatusFragment
import dq.status.sticker.saver.presentation.utils.AlertUtils
import dq.status.sticker.saver.presentation.utils.Resource
import dq.status.sticker.saver.presentation.utils.adapters.ViewPagerAdapter
import dq.status.sticker.saver.presentation.utils.animations.ZoomOutPageTransformer
import dq.status.sticker.saver.presentation.view_models.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        setUpTabLayout()

    }

    private fun setUpTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ImageStatusFragment(), "Image")
        adapter.addFragment(VideoStatusFragment(), "Video")
        //  adapter.addFragment(StickerEmojiFragment(), "Stickers")
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        setUpTabIcons()
    }

    private fun setUpTabIcons() {
        val iconsList = ArrayList<Int>()
        iconsList.add(R.drawable.file_image_fill)
        iconsList.add(R.drawable.file_video_fill)
        //   iconsList.add(R.drawable.emoji_sticker_fill)

        binding.tabs.getTabAt(0)
        binding.tabs.getTabAt(1)
        //  binding.tabs.getTabAt(2)


        binding.tabs.setUnboundedRipple(true)
        binding.tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white))
        binding.tabs.setTabTextColors(
            ContextCompat.getColor(this, R.color.mainColorUltraProLight),
            ContextCompat.getColor(this, R.color.white)
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment?.onActivityResult(requestCode, resultCode, data)
        }
    }

}