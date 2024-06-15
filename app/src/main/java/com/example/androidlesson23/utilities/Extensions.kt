package com.example.androidlesson23.utilities

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidlesson23.R

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ImageView.loadImageWithoutBindingAdapter(url: String?) {
    Glide
        .with(this)
        .load(url)
        .centerCrop().placeholder(R.color.Transparent)
        .into(this)
}



fun String.highlightName(fragment: Fragment, name: String, colorResId: Int): SpannableString {
    val spannableString = SpannableString(this)
    val start = this.indexOf(name)
    if (start != -1) {
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(fragment.requireContext(), colorResId)),
            start,
            start + name.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableString
}

fun loadimagewithidWithoutBindingAdapter(imageView: ImageView, imageId: Int) {

    imageView.setImageResource(imageId)
}