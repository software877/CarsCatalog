package com.d3f.musclecarscatalog.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.d3f.musclecarscatalog.MainActivityView
import com.d3f.musclecarscatalog.R
import com.d3f.musclecarscatalog.databinding.CarDetailsFragmentBinding
import com.d3f.musclecarscatalog.viewmodels.FragmentsData
import com.d3f.musclecarscatalog.viewmodels.TestViewModel

class CarDetailsFragment: Fragment() {
    private val viewModel: TestViewModel by activityViewModels()
    private val binding : CarDetailsFragmentBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            (activity as? MainActivityView)?.goToCatalog()
        }

        viewModel.state.observe(viewLifecycleOwner, Observer<FragmentsData> { fragmentsData ->
            when(fragmentsData) {
                is FragmentsData.PassData -> {
                    val carModel = fragmentsData.carModel
                    binding.webView.setWebViewClient(WebViewClient())
                    binding.webView.loadUrl(carModel.url)
                }
                is FragmentsData.DefaultData -> {}
            }
        })
    }

}