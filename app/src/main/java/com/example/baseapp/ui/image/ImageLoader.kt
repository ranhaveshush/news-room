package com.example.baseapp.ui.image

import android.widget.ImageView
import coil.api.load

class ImageLoader {
    fun load(imageView: ImageView, url: String) {
        imageView.load(url)
    }
}
