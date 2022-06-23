package com.codex.hackernews.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.codex.hackernews.ui.viewmodel.DetailStoryViewModel
import com.codex.hackernews.R
import com.codex.hackernews.base.BaseFragment
import com.codex.hackernews.databinding.FragmentDetailStoryBinding
import com.codex.hackernews.ui.adapter.CommentAdapter
import com.codex.hackernews.utils.Resource
import com.codex.hackernews.utils.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailStoryFragment : BaseFragment<FragmentDetailStoryBinding, DetailStoryViewModel>(false) {

    private val detailStoryViewModel: DetailStoryViewModel by viewModels()
    private val mainNavController by lazy { requireActivity().findNavController(R.id.nav_host_main) }
    private val args:DetailStoryFragmentArgs  by navArgs()

    private lateinit var binding: FragmentDetailStoryBinding
    private lateinit var komentarAdapter: CommentAdapter

    override fun setLayout(): Int = R.layout.fragment_detail_story

    override fun getViewModels(): DetailStoryViewModel = detailStoryViewModel

    override fun onInitialization() {
        super.onInitialization()
        binding = getViewDataBinding()
        binding.lifecycleOwner = this
    }

    override fun onReadyAction() {

        komentarAdapter =  CommentAdapter()

        binding.rvDetailKomentar.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(requireContext())
            adapter = komentarAdapter
        }
        binding.tvDetailTitle.text = args.data.title
        binding.tvDetailBy.text = args.data.by
        binding.webContent.loadUrl(args.data.url.toString())
        binding.tvDetailDatetime.text = millisToDate(args.data.time.toLong())
        if(!args.data.kids.isNullOrEmpty()){
            detailStoryViewModel.getItems(args.data.kids!!)
        }

    }

    override fun onObserveAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailStoryViewModel.comments.observe(viewLifecycleOwner,{
                when(it.status){
                    Resource.Status.LOADING  -> {
                    }
                    Resource.Status.SUCCESS  -> {
                        if (!it.data.isNullOrEmpty()){
                         komentarAdapter.submitData(it.data)
                        }
                    }
                    Resource.Status.ERROR  -> {
                    }
                }
            })
        }
    }
}