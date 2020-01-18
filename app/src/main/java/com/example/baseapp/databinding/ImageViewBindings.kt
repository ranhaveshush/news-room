package com.example.baseapp.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.baseapp.ui.image.ImageLoader

@BindingAdapter("imageUrl")
fun loadImageUrl(imageView: ImageView, url: String) {
    val imageLoader = ImageLoader()
    imageLoader.load(imageView, url)
}
