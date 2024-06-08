package dq.status.sticker.saver.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dq.status.sticker.saver.databinding.FragmentImageStatusBinding
import dq.status.sticker.saver.domain.model.StatusItemLocal
import dq.status.sticker.saver.presentation.ui.activity.RequestPermissionActivity
import dq.status.sticker.saver.presentation.utils.AlertUtils
import dq.status.sticker.saver.presentation.utils.PrefKeys
import dq.status.sticker.saver.presentation.utils.PreferenceUtils
import dq.status.sticker.saver.presentation.utils.Resource
import dq.status.sticker.saver.presentation.utils.adapters.ImageAdapter
import dq.status.sticker.saver.presentation.view_models.ImageStatusViewModel
import kotlinx.coroutines.launch


class ImageStatusFragment : Fragment() {
    private lateinit var binding: FragmentImageStatusBinding
    private lateinit var viewModel: ImageStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageStatusBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[ImageStatusViewModel::class.java]
        if (PreferenceUtils.getPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)) {
            loadWhatsappStatusImages()
        } else {
            startActivity(Intent(requireContext(), RequestPermissionActivity::class.java))
        }
        // Inflate the layout for this fragment
        return binding.root
    }


    private fun loadWhatsappStatusImages() {
        val list = ArrayList<StatusItemLocal>()
        lifecycleScope.launch {
            viewModel.getImageStatus(requireContext()).collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success<*> -> {
                        binding.progressBar.visibility = View.GONE
                        list.addAll(it.data as Collection<StatusItemLocal>)
                        binding.recyclerView.adapter = ImageAdapter(binding.root.context, list)
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

    override fun onResume() {
        super.onResume()
        /*if (PreferenceUtils.getPrefBoolean(PrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false)) {
            loadWhatsappStatusImages()
        } else {
            startActivity(Intent(requireContext(), RequestPermissionActivity::class.java))
        }*/
    }
}