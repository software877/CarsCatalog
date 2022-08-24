package com.d3f.musclecarscatalog.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.d3f.musclecarscatalog.MainActivityView
import com.d3f.musclecarscatalog.R
import com.d3f.musclecarscatalog.adapters.CarsRecyclerViewAdapter
import com.d3f.musclecarscatalog.databinding.CatalogFragmentBinding
import com.d3f.musclecarscatalog.states.CarsState
import com.d3f.musclecarscatalog.viewmodels.CarsViewModel
import com.d3f.musclecarscatalog.viewmodels.FragmentsData
import com.d3f.musclecarscatalog.viewmodels.TestViewModel

class CatalogFragment: Fragment() {

    private val carsViewModel = CarsViewModel()
    private val binding: CatalogFragmentBinding by viewBinding()
    private val viewModel: TestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carsViewModel.state.observe(this@CatalogFragment, Observer<CarsState> { carsState ->
            when(carsState) {
                is CarsState.DefaultState -> {
                    println("Default")

                }
                is CarsState.SuccessState -> {
                    val carsAdapter = CarsRecyclerViewAdapter({
                        viewModel.addData(it)
                        (activity as? MainActivityView)?.goToDetails()
                    })
                    binding.carsRecyclerView.adapter = carsAdapter
                    carsAdapter.setCars(carsState.carsResponse.data)
                    println("Success")
                }
                is CarsState.ErrorState -> {
                    println("Error")

                }
            }
        })

        carsViewModel.getCars()

    }

    override fun onResume() {
        super.onResume()
    }




}