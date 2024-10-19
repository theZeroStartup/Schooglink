package com.zero.schooglink.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.zero.schooglink.R
import com.zero.schooglink.base.view.BaseFragment
import com.zero.schooglink.databinding.FragmentDashboardBinding
import com.zero.schooglink.model.Dashboard
import com.zero.schooglink.viewmodel.DashboardVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val dashboardVM: DashboardVM by activityViewModels()

    private lateinit var navOptions: NavOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch { attachObservers() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardVM.requestDashboardDetails.postValue(true)
        clickListeners()
        binding.tvTopLinks.performClick()
        binding.tvGreeting.text = dashboardVM.getGreetingMessage()
        initFragmentTransitionAnim()
    }

    //Click listeners for tabs to switch between top & recent link fragments and update UI elements accordingly
    private fun clickListeners() {
        binding.tvTopLinks.setOnClickListener {
            binding.tvRecentLinks.background = null
            binding.tvRecentLinks.setTextColor(requireContext().getColor(R.color.grey))
            (it as TextView).setTextColor(Color.WHITE)
            it.background = ResourcesCompat.getDrawable(requireContext().resources, R.drawable.bg_btn_blue_curved, null)

            switchToFragment(R.id.topLinksFragment)
        }

        binding.tvRecentLinks.setOnClickListener {
            binding.tvTopLinks.background = null
            binding.tvTopLinks.setTextColor(requireContext().getColor(R.color.grey))
            (it as TextView).setTextColor(Color.WHITE)
            it.background = ResourcesCompat.getDrawable(requireContext().resources, R.drawable.bg_btn_blue_curved, null)

            switchToFragment(R.id.recentLinksFragment)
        }
    }

    private fun attachObservers() {
        //Observe api result data changes, validate & display apt ui elements
        dashboardVM.dashboardDetailsResponse.observe(this) {
            if (it.statusCode == 200) {
                showData()
                populateDataInUi(it)
            }
            else {
                showNoDataPlaceholder()
                showToast(requireContext(), it.message.toString())
            }
        }
    }

    //Show ui elements, when data available
    private fun showData() {
        binding.ivNoDataFound.hide()
        binding.llLinkTabsRoot.show()
        binding.linksFragmentContainerView.show()
    }

    //Show placeholder, when data not available
    private fun showNoDataPlaceholder() {
        binding.ivNoDataFound.show()
        binding.llLinkTabsRoot.hide()
        binding.linksFragmentContainerView.hide()
    }

    //Extract link-related data from the entirety of api response data and store in Viewmodel
    private fun populateDataInUi(dashboardDetails: Dashboard) {
        dashboardDetails.data?.recentLinks?.let { dashboardVM.setRecentLinksData(it) }
        dashboardDetails.data?.topLinks?.let { dashboardVM.setTopLinksData(it) }
    }

    //Fragment open & close animation (slide in & slide out)
    private fun initFragmentTransitionAnim() {
        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.enter_from_right).setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left).setPopExitAnim(R.anim.exit_to_right)

        navOptions = navBuilder.build()
    }

    //Open a new fragment using nav component
    private fun switchToFragment(destinationId: Int) {
        if (isFragmentInBackStack(destinationId)) {
            getNavController().popBackStack(destinationId, false)
        } else {
            getNavController().navigate(destinationId, null, navOptions)
        }
    }

    //Check if fragment to be opened is already in back stack
    private fun isFragmentInBackStack(destinationId: Int) =
        try {
            getNavController().getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }

    private fun getNavController(): NavController {
        return (getHostFragment() as NavHostFragment).navController
    }

    private fun getHostFragment(): Fragment? {
        return childFragmentManager.findFragmentById(R.id.linksFragmentContainerView)
    }
}