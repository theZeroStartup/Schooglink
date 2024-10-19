package com.zero.schooglink.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zero.schooglink.base.view.BaseFragment
import com.zero.schooglink.databinding.FragmentRecentLinksBinding
import com.zero.schooglink.model.Links
import com.zero.schooglink.view.activity.MainActivity
import com.zero.schooglink.viewmodel.DashboardVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentLinksFragment: BaseFragment() {

    private val dashboardVM: DashboardVM by activityViewModels()
    private lateinit var binding: FragmentRecentLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        lifecycleScope.launch { attachObservers() }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            //Search and filter results as the user types
            override fun afterTextChanged(p0: Editable?) {
                val query = p0.toString().trim()
                dashboardVM.filter(query, dashboardVM.recentLinksData.value)
            }
        })
    }

    private fun attachObservers() {
        //Observe api result data changes, validate & display apt ui elements
        dashboardVM.recentLinksData.observe(viewLifecycleOwner) {
            stopLoading()
            if (it.isNotEmpty()) {
                showData()
                dashboardVM.getLinksAdapter(requireContext()).updateData(it)
            }
            else
                showNoDataPlaceholder()
        }

        //Move to details screen
        dashboardVM.selectedLink.observe(viewLifecycleOwner) {
            moveToDetailsScreen(it)
        }

        //Copy link to clipboard
        dashboardVM.copyLink.observe(viewLifecycleOwner) {
            copyToClipboard(it)
        }

        //Show filtered data based on search query. If no data, show placeholder
        dashboardVM.filteredLinks.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    showData()
                    dashboardVM.getLinksAdapter(requireContext()).updateData(it)
                }
                else
                    showNoDataPlaceholder()
            }
        }
    }

    //Show ui elements, when data available
    private fun showData() {
        binding.ivNoDataFound.hide()
        binding.rvLinks.show()
    }

    //Show placeholder, when data not available
    private fun showNoDataPlaceholder() {
        binding.ivNoDataFound.show()
        binding.rvLinks.hide()
    }

    private fun initRecyclerView() {
        //Init recycler view
        binding.rvLinks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dashboardVM.getLinksAdapter(requireContext())
        }

        //Show loader anim
        binding.shimmer.apply {
            visibility = View.VISIBLE
            startShimmer()
        }
    }

    //Stop loading anim
    private fun stopLoading() {
        requireActivity().runOnUiThread {
            binding.shimmer.apply {
                visibility = View.GONE
                stopShimmer()
            }
        }
    }

    private fun moveToDetailsScreen(link: Links) {
        (requireActivity() as MainActivity).moveToDetailsScreen(link)
    }
}