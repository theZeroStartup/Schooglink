package com.zero.schooglink.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.zero.schooglink.R
import com.zero.schooglink.base.view.BaseActivity
import com.zero.schooglink.databinding.ActivityDetailsBinding
import com.zero.schooglink.model.Links
import com.zero.schooglink.util.Utils.formatDate
import com.zero.schooglink.view.activity.MainActivity.Companion.EXTRA_LINK

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private var selectedLink: Links? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get link data from intent extras and convert it to POJO using Gson
        val strLink = intent?.extras?.getString(EXTRA_LINK)
        selectedLink = Gson().fromJson(strLink, Links::class.java)

        populateUi()
    }

    //Show details of the selected link
    private fun populateUi() {
        Glide.with(this).load(selectedLink?.image)
            .placeholder(R.drawable.img_logo_sample).into(binding.ivLogo)
        binding.tvTitle.text = selectedLink?.title
        binding.tvDate.text = formatDate(selectedLink?.date.toString())
        binding.tvClicks.text = selectedLink?.clicks.toString()
        binding.tvLink.text = selectedLink?.link.toString()

        binding.ibBack.setOnClickListener { finish() }
        binding.btnOpenLink.setOnClickListener { openLink(selectedLink?.link.toString()) }
    }

    //Open the link in browser or any equivalent app
    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}