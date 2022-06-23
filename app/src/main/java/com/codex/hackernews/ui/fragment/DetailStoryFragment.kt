package com.codex.hackernews.ui.fragment

import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.codex.hackernews.AssesmentApp
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
    private val args: DetailStoryFragmentArgs by navArgs()

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
        komentarAdapter = CommentAdapter()
        binding.rvDetailKomentar.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(requireContext())
            adapter = komentarAdapter
        }

        binding.tvDetailTitle.text = args.data.title
        binding.tvDetailBy.text = args.data.by
        binding.webContent.loadUrl(args.data.url.toString())
        binding.tvDetailDatetime.text = millisToDate(args.data.time.toLong())
        if (!args.data.kids.isNullOrEmpty()) {
            detailStoryViewModel.getItems(args.data.kids!!)
        }
        setFavouriteStatus(args.data.isFavourite ?: false)
        binding.btnBack.setOnClickListener {
            mainNavController.popBackStack()
        }

        binding.btnFavorite.setOnClickListener {
            if (args.data.isFavourite == true) {
                detailStoryViewModel.removeFromFavourite(args.data)
            } else {
                detailStoryViewModel.addToFavourite(args.data)
            }
        }
    }


    override fun onObserveAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailStoryViewModel.comments.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        binding.cpLoadingKomentar.visibility = View.VISIBLE
                    }
                    Resource.Status.SUCCESS -> {
                        if (!it.data.isNullOrEmpty()) {
                            komentarAdapter.submitData(it.data)
                            binding.cpLoadingKomentar.visibility = View.GONE
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.cpLoadingKomentar.visibility = View.GONE
                    }
                }
            })

            detailStoryViewModel.isFavourite.observe(viewLifecycleOwner, {
                setFavouriteStatus(it)
            })
        }
    }

    private fun setFavouriteStatus(status: Boolean) {
        val drawable = if (status) {
            ResourcesCompat.getDrawable(
                AssesmentApp.applicationContext().resources,
                R.drawable.ic_star_yellow,
                null
            )
        } else {
            ResourcesCompat.getDrawable(
                AssesmentApp.applicationContext().resources,
                R.drawable.ic_star_grey,
                null
            )
        }
        binding.btnFavorite.setImageDrawable(drawable)
    }
}