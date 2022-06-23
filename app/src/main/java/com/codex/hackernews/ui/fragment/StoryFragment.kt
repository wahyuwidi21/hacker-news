package com.codex.hackernews.ui.fragment

import android.view.View
import android.widget.Toast
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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

        storyAdapter = StoryAdapter {
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
    }

    override fun onObserveAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            storyViewModel.getStories().catch { e ->
                binding.cpLoading.visibility = View.GONE
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }.collect {
                storyAdapter.submitData(it)
                binding.cpLoading.visibility = View.GONE
            }
        }
    }
}