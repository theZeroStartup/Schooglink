package com.zero.schooglink.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zero.schooglink.R
import com.zero.schooglink.databinding.ItemLinksBinding
import com.zero.schooglink.model.Links
import com.zero.schooglink.util.Utils.formatDate
import com.zero.schooglink.viewmodel.DashboardVM.Companion.TYPE_COPY
import com.zero.schooglink.viewmodel.DashboardVM.Companion.TYPE_OPEN_DETAILS

/**
 * Adapter to display the retrieved Link details in a list-type view
 *
 * @property linkDetails List of all links retrieved from the Api
 * @property onClick Callback to the view model to open/copy the selected item
 */
class LinksAdapter(private val context: Context,
                   private var linkDetails: List<Links>,
                   private val onClick: (String, Links) -> Unit): RecyclerView.Adapter<LinksAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemLinksBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLinksBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val link = linkDetails[position]
        holder.binding.tvTitle.text = link.title
        holder.binding.tvDate.text = formatDate(link.date)
        holder.binding.tvClicks.text = link.clicks.toString()
        holder.binding.tvLink.text = link.link
        Glide.with(context.applicationContext).load(link.image).placeholder(R.drawable.img_logo_sample).into(holder.binding.ivLogo)

        holder.binding.ibCopy.setOnClickListener { onClick.invoke(TYPE_COPY, link) }
        holder.binding.root.setOnClickListener { onClick.invoke(TYPE_OPEN_DETAILS, link) }
    }

    override fun getItemCount(): Int {
        return linkDetails.size
    }

    //Updates the list of files with new list
    fun updateData(data: List<Links>) {
        linkDetails = data
        notifyDataSetChanged()
    }
}