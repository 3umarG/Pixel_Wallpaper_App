package com.example.pixelwallpaperapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixelwallpaperapp.pojo.Photo
import com.example.pixelwallpaperapp.ui.adapters.OnImageClickListener
import com.example.pixelwallpaperapp.ui.adapters.RVAdapter
import com.example.pixelwallpaperapp.ui.viewmodel.WallpaperViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var viewModel: WallpaperViewModel
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        rv = view.findViewById(R.id.rv)
        etSearch = view.findViewById(R.id.etSearch)
        searchButton = view.findViewById(R.id.searchButton)
        viewModel = ViewModelProvider(this)[WallpaperViewModel::class.java]
        progressBar = view.findViewById(R.id.progressBar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            // This will take time ..
            val job = launch {
                viewModel.getWallpaper()
            }
            job.join()
            withContext(Dispatchers.Main) {
                viewModel.wallpaperData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        val wallpaperAdapter = RVAdapter(
                            it.photos,
                            object : OnImageClickListener {
                                override fun onImageClick(photo: Photo) {
                                    val action =
                                        MainFragmentDirections.actionMainFragmentToImageFragment(
                                            photo
                                        )
                                    findNavController().navigate(action)
                                }
                            }
                        )
                        rv.apply {
                            adapter = wallpaperAdapter
                            layoutManager = GridLayoutManager(context, 2)
                        }

                        progressBar.visibility = View.INVISIBLE
                        rv.visibility = View.VISIBLE
                    }
                }
            }

        }


        searchButton.setOnClickListener {
            if (etSearch.text.isNotEmpty() && etSearch.text.isNotBlank()) {
                rv.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    val job = launch {
                        viewModel.getWallpaper(etSearch.text.toString())
                    }

                    job.join()
                    withContext(Dispatchers.Main) {
                        viewModel.wallpaperData.observe(viewLifecycleOwner) {
                            if (it != null) {
                                val wallpaperAdapter =
                                    RVAdapter(
                                        it.photos,
                                        object : OnImageClickListener {
                                            override fun onImageClick(photo: Photo) {
                                                val action =
                                                    MainFragmentDirections.actionMainFragmentToImageFragment(
                                                        photo
                                                    )
                                                findNavController().navigate(action)
                                            }
                                        }
                                    )
                                rv.apply {
                                    adapter = wallpaperAdapter
                                    layoutManager = GridLayoutManager(context, 2)
                                }

                                progressBar.visibility = View.INVISIBLE
                                rv.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }


}