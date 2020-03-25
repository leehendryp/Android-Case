package com.leehendryp.androidcase.dataentry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.databinding.DataEntryFragmentBinding
import javax.inject.Inject

class DataEntryFragment : Fragment() {
    private lateinit var binding: DataEntryFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DataEntryViewModel by viewModels { viewModelFactory }

    companion object {
        fun newInstance() = DataEntryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.data_entry_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        binding.apply { setButtonClickListener() }
    }

    private fun observeViewModel() = Unit

    private fun DataEntryFragmentBinding.setButtonClickListener() {
        buttonCalculateRoute.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.go_to_route_view, null)
        )
    }
}