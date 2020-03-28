package com.leehendryp.androidcase.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.databinding.RouteDetailsFragmentBinding
import javax.inject.Inject

class RouteDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: RouteDetailsFragmentBinding

    companion object {
        fun newInstance() = RouteDetailsFragment()
    }

    private val viewModel: RouteDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.route_details_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs: RouteDetailsFragmentArgs by navArgs()
        val routeWithAnttPrices = safeArgs.routeWithAnttPrices

        // TODO put routeWithAnttPrices info into map + bottom sheet
        observeViewModel()
    }

    private fun observeViewModel() {
        // TODO: Implement the ViewModel
    }
}
