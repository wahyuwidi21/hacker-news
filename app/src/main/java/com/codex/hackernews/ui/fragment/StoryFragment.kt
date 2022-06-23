package com.codex.hackernews.ui.fragment

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catastrophic.app.ui.viewmodel.StoryViewModel
import com.codex.hackernews.R
import com.codex.hackernews.base.BaseFragment
import com.codex.hackernews.databinding.FragmentStoryBinding
import com.codex.hackernews.ui.adapter.StoryAdapter
import com.codex.hackernews.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding, StoryViewModel>(false) {

    private val storyViewModel: StoryViewModel by viewModels()
    private val mainNavController by lazy { requireActivity().findNavController(R.id.nav_host_main) }

    private lateinit var binding: FragmentStoryBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun setLayout(): Int = R.layout.fragment_story

    override fun getViewModels(): StoryViewModel = storyViewModel

    override fun onInitialization() {
        super.onInitialization()
        binding = getViewDataBinding()
        binding.lifecycleOwner = this
    }

    override fun onReadyAction() {

        storyAdapter =   StoryAdapter {
            val bundle =
                bundleOf(
                    "data" to it
                )
            mainNavController.navigate(
                R.id.action_fragment_story_to_fragment_detail_story,
                bundle
            )
        }
        binding.cpLoading.visibility = View.VISIBLE
        binding.rvStory.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storyAdapter
        }
        storyViewModel.getItems()
    }

    override fun onObserveAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            storyViewModel.storyitems.observe(viewLifecycleOwner,{
                when(it.status){
                    Resource.Status.LOADING  -> {
                        binding.cpLoading.visibility = View.VISIBLE
                    }
                    Resource.Status.SUCCESS  -> {
                        if (!it.data.isNullOrEmpty()){

                         storyAdapter.submitData(it.data)
                            binding.cpLoading.visibility = View.GONE
                        }
                        binding.cpLoading.visibility = View.GONE
                    }
                    Resource.Status.ERROR  -> {
                        binding.cpLoading.visibility = View.GONE
                    }
                }
            })
        }
    }
}