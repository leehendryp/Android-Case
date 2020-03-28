package com.leehendryp.androidcase.dataentry.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.core.Extensions.fadeIn
import com.leehendryp.androidcase.core.Extensions.mat
import com.leehendryp.androidcase.core.Extensions.transparent
import com.leehendryp.androidcase.core.Extensions.vanish
import com.leehendryp.androidcase.databinding.DataEntryFragmentBinding
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Error
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Loading
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Success
import com.leehendryp.androidcase.dataentry.presentation.DataEntryViewModel.Companion.MIN_SHAFTS
import javax.inject.Inject


class DataEntryFragment : Fragment() {
    private lateinit var binding: DataEntryFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DataEntryViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.data_entry_fragment, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        binding.apply {
            setNavigationListener()
            setSeekBarMinValue()
        }
    }

    private fun observeViewModel() {
        // viewModel.state.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(state: DataEntryState) {
        toggleLoading()
        when (state) {
            is Success -> toggleCalcRouteButton(state)
            is Error -> showErrorMessage()
        }
    }

    private fun toggleCalcRouteButton(state: DataEntryState) {
        binding.buttonCalculateRoute.apply {
            if (state !is Success) {
                isEnabled = false
                transparent()
            } else {
                isEnabled = true
                mat()
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_LONG).show()
    }

    private fun toggleLoading() {
        val animDuration: Long = 700
        binding.containerLoadingWheel.apply {
            if (viewModel.state.value == Loading) fadeIn(animDuration) else vanish(animDuration)
        }
    }

    private fun DataEntryFragmentBinding.setNavigationListener() {
        buttonCalculateRoute.setOnClickListener { goToRouteDetailsView() }
    }

    private fun goToRouteDetailsView() {
        val data: RouteWithAnttPrices? = (viewModel.state.value as Success).data
        val action = DataEntryFragmentDirections.goToRouteView(data)
        findNavController().navigate(action)
    }

    private fun DataEntryFragmentBinding.setSeekBarMinValue() {
        setProgressToMinValue()

        seekBarDataEntryTruckShaft.apply {
            setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (progress < MIN_SHAFTS) setProgressToMinValue()
                        updateShaftDisplay()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                }
            )
        }
    }

    private fun setProgressToMinValue() {
        binding.seekBarDataEntryTruckShaft.progress = MIN_SHAFTS
    }

    private fun updateShaftDisplay() {
        binding.apply {
            textDataEntryShaftNumberDisplay.text = seekBarDataEntryTruckShaft.progress.toString()
        }
    }
}