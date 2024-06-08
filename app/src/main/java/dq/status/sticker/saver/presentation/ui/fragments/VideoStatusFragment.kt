package dq.status.sticker.saver.presentation.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dq.status.sticker.saver.databinding.FragmentVideoStatusBinding
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.ui.activity.RequestPermissionActivity
import dq.status.sticker.saver.presentation.utils.AlertUtils
import dq.status.sticker.saver.presentation.utils.Constants
import dq.status.sticker.saver.presentation.utils.FileUtils
import dq.status.sticker.saver.presentation.utils.PrefKeys
import dq.status.sticker.saver.presentation.utils.PreferenceUtils
import dq.status.sticker.saver.presentation.utils.Resource
import dq.status.sticker.saver.presentation.utils.adapters.ImageAdapter
import dq.status.sticker.saver.presentation.utils.adapters.VideoAdapter
import dq.status.sticker.saver.presentation.utils.getFolderPermissionForWhatsapp
import dq.status.sticker.saver.presentation.view_models.VideoStatusViewModel
import kotlinx.coroutines.launch


class VideoStatusFragment : Fragment() {
    private lateinit var binding: FragmentVideoStatusBinding
    private lateinit var viewModel: VideoStatusViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoStatusBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[VideoStatusViewModel::class.java]

        if (PreferenceUtils.getPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)) {
            loadWhatsappStatusVideos()
        } else {
            startActivity(Intent(requireContext(), RequestPermissionActivity::class.java))
        }
        return binding.root
    }


    private fun loadWhatsappStatusVideos() {
        val list = ArrayList<StatusItemLocal>()
        lifecycleScope.launch {
            viewModel.getVideoStatus(requireContext()).collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success<*> -> {
                        binding.progressBar.visibility = View.GONE
                        list.addAll(it.data as Collection<StatusItemLocal>)
                        binding.recyclerView.adapter = VideoAdapter(binding.root.context, list)
                        binding.recyclerView.layoutManager =
                            GridLayoutManager(binding.root.context, 3)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        AlertUtils.showToast(requireContext(), "Error Occurred.")
                    }

                }
            }
        }


    }
}