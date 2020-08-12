package com.amilien.prometheus.view.feed

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.amilien.prometheus.R
import com.amilien.prometheus.utils.navAnimOptions
import com.amilien.prometheus.view.BaseFragment
import com.amilien.prometheus.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : BaseFragment(R.layout.fragment_feed) {

    private val feedViewModel: FeedViewModel by createActivityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_add_location.setOnClickListener { findNavController().navigate(R.id.navigation_maps, null, navAnimOptions) }
        with(FeedAdapter(requireContext())) {
            recycler_view.adapter = this
            setItems(feedViewModel.items)
        }
    }
}
