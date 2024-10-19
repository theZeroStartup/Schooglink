package com.zero.schooglink.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.zero.schooglink.R
import com.zero.schooglink.base.view.BaseActivity
import com.zero.schooglink.databinding.ActivityMainBinding
import com.zero.schooglink.model.Links
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        const val EXTRA_LINK = "link"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var navOptions: NavOptions
    private var activeFragment: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmentTransitionAnim()
        listenToFragmentNavigation()
        clickListeners()
        binding.customNavBar.ibLinks.isActivated = true
    }

    //Show details screen onclick of an item in the recyclerview
    fun moveToDetailsScreen(link: Links) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_LINK, Gson().toJson(link))
        startActivity(intent)
    }

    //Click listeners for the bottom nav tabs
    private fun clickListeners() {
        binding.customNavBar.ibLinks.setOnClickListener { switchToFragment(R.id.dashboardFragment) }
        binding.customNavBar.ibCourses.setOnClickListener { switchToFragment(R.id.coursesFragment) }
        binding.customNavBar.ibCampaigns.setOnClickListener { switchToFragment(R.id.campaignFragment) }
        binding.customNavBar.ibProfile.setOnClickListener { switchToFragment(R.id.profileFragment) }
    }

    //Listen to fragment navigation to update screen titles, icons, etc.
    private fun listenToFragmentNavigation() {
        val navController = getNavController()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            activeFragment = controller.currentDestination?.id
            binding.tvTitle.text = controller.currentDestination?.label

            setAllTabsInactive()

            when (controller.currentDestination?.id!!) {
                R.id.dashboardFragment -> {
                    binding.customNavBar.tvLinks.setTextColor(Color.BLACK)
                    binding.customNavBar.ibLinks.isActivated = true
                }
                R.id.coursesFragment -> {
                    binding.customNavBar.tvCourses.setTextColor(Color.BLACK)
                    binding.customNavBar.ibCourses.isActivated = true
                }
                R.id.campaignFragment -> {
                    binding.customNavBar.tvCampaigns.setTextColor(Color.BLACK)
                    binding.customNavBar.ibCampaigns.isActivated = true
                }
                R.id.profileFragment -> {
                    binding.customNavBar.tvProfile.setTextColor(Color.BLACK)
                    binding.customNavBar.ibProfile.isActivated = true
                }
            }
        }
    }

    //Set all tabs in bottom nav to inactive state
    private fun setAllTabsInactive() {
        binding.customNavBar.ibLinks.isActivated = false
        binding.customNavBar.ibCourses.isActivated = false
        binding.customNavBar.ibCampaigns.isActivated = false
        binding.customNavBar.ibProfile.isActivated = false

        binding.customNavBar.tvLinks.setTextColor(getColor(R.color.grey))
        binding.customNavBar.tvCourses.setTextColor(getColor(R.color.grey))
        binding.customNavBar.tvCampaigns.setTextColor(getColor(R.color.grey))
        binding.customNavBar.tvProfile.setTextColor(getColor(R.color.grey))
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

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onSupportNavigateUp()
    }

    private fun getNavController(): NavController {
        return (getHostFragment() as NavHostFragment).navController
    }

    private fun getHostFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView)
    }
}