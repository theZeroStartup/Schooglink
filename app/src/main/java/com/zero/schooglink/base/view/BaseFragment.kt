package com.zero.schooglink.base.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.zero.schooglink.view.activity.MainActivity

abstract class BaseFragment: Fragment() {

    /**
     * @param text Text to be copied to the Clipboard
     */
    fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // Create a ClipData object with the text to copy
        val clip = ClipData.newPlainText("label", text)

        // Set the ClipData to the ClipboardManager
        clipboard.setPrimaryClip(clip)
        showToast(requireContext(), "Copied to clipboard")
    }

    /**
     * @param message Messages to be shown on UI
     */
    fun showToast(context: Context, message: String) {
        (requireActivity() as MainActivity).showToast(context, message)
    }

    //Extension functions to show/hide a view
    fun View.show() { visibility = View.VISIBLE }
    fun View.hide() { visibility = View.GONE }
}
