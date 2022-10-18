package com.example.pixelwallpaperapp

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
class ImageFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSetWallpaper: Button
    private lateinit var imageView: ImageView

    private val args by navArgs<ImageFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        imageView = view.findViewById(R.id.image)
        btnSetWallpaper = view.findViewById(R.id.btnSetWallpaper)
        progressBar = view.findViewById(R.id.progressBar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = args.photo.src.portrait
        Glide
            .with(view)
            .load(image)
            .placeholder(R.drawable.progress_animation)
            .into(imageView)

        btnSetWallpaper.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                val bitMap: Bitmap = Picasso.get().load(image).get()
                WallpaperManager.getInstance(requireContext()).apply {
                    setBitmap(bitMap)
                }
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Wallpaper changed !!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

}