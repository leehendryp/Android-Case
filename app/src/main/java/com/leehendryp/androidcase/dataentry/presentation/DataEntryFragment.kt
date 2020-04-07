package com.leehendryp.androidcase.dataentry.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.core.AndroidCaseApplication
import com.leehendryp.androidcase.core.extensions.doOnProgressChanged
import com.leehendryp.androidcase.core.extensions.fadeIn
import com.leehendryp.androidcase.core.extensions.mat
import com.leehendryp.androidcase.core.extensions.setOnPlaceSelectedListener
import com.leehendryp.androidcase.core.extensions.transparent
import com.leehendryp.androidcase.core.extensions.vanish
import com.leehendryp.androidcase.databinding.DataEntryFragmentBinding
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.request.Spots
import com.leehendryp.androidcase.dataentry.domain.Address
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import com.leehendryp.androidcase.dataentry.domain.toAddress
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Error
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Loading
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Success
import com.leehendryp.androidcase.dataentry.presentation.DataEntryViewModel.Companion.MIN_SHAFTS
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import javax.inject.Inject

class DataEntryFragment : Fragment() {
    private lateinit var binding: DataEntryFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DataEntryViewModel by viewModels { viewModelFactory }

    private lateinit var startingPoint: Address
    private lateinit var destination: Address

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.data_entry_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectDependencies()
        observeViewModelState()
        initAutocompleteFragment(
            R.id.fragment_data_entry_starting_point,
            AddressType.STARTING_POINT
        )
        initAutocompleteFragment(R.id.fragment_data_entry_destination, AddressType.DESTINATION)

        binding.apply {
            setSeekBarMinValue()
            setNavigationOnButton()
        }
    }

    private fun injectDependencies() {
        (activity?.application as AndroidCaseApplication).appComponent.inject(this)
    }

    private fun observeViewModelState() {
        viewModel.state.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(state: DataEntryState) {
        toggleLoading()
        when (state) {
            is Success -> goToRouteDetailsView(state.data)
            is Error -> showErrorMessage()
        }
    }

    private fun toggleCalcRouteButton(enable: Boolean) {
        binding.buttonCalculateRoute.apply {
            if (enable) {
                isEnabled = true
                mat()
            } else {
                isEnabled = false
                transparent()
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

    private fun initAutocompleteFragment(@IdRes resourceId: Int, type: AddressType) {
        val fragment =
            childFragmentManager.findFragmentById(resourceId) as AutocompleteSupportFragment

        fragment.apply {
            setNecessaryAddressFields()
            setOnPlaceSelectedListener(type)
        }
    }

    private fun DataEntryFragmentBinding.setNavigationOnButton() {
        buttonCalculateRoute.setOnClickListener {
            viewModel.getRouteDetailsFrom(createInfoProvidedByDriver())
        }
    }

    private fun DataEntryFragmentBinding.createInfoProvidedByDriver(): InfoProvidedByDriver {
        val start = Spots(
            arrayListOf(
                startingPoint.location.longitude,
                startingPoint.location.latitude
            )
        )

        val finish = Spots(
            arrayListOf(
                destination.location.longitude,
                destination.location.latitude
            )
        )

        return InfoProvidedByDriver(
            spots = arrayListOf(start, finish),
            fuelConsumption = parseInt(editTextDataEntryAverageConsumption.text.toString()),
            fuelPrice = parseDouble(editTextDataEntryFuelPrice.text.toString()),
            shafts = seekBarDataEntryTruckShaft.progress
        )
    }

    private fun goToRouteDetailsView(data: RouteWithAnttPrices) {
        val action = DataEntryFragmentDirections.goToRouteView(data)
        findNavController().navigate(action)
    }

    private fun DataEntryFragmentBinding.setSeekBarMinValue() {
        setProgressToMinValue()

        seekBarDataEntryTruckShaft.apply {
            doOnProgressChanged { setProgressToMinValue().also { updateShaftDisplay() } }
        }
    }

    private fun setProgressToMinValue() {
        binding.seekBarDataEntryTruckShaft.apply {
            if (progress < MIN_SHAFTS) progress = MIN_SHAFTS
        }
    }

    private fun updateShaftDisplay() {
        binding.apply {
            textDataEntryShaftNumberDisplay.text = seekBarDataEntryTruckShaft.progress.toString()
        }
    }

    private fun AutocompleteSupportFragment.setNecessaryAddressFields() {
        setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        )
    }

    private fun AutocompleteSupportFragment.setOnPlaceSelectedListener(type: AddressType) {
        setOnPlaceSelectedListener(
            onPlaceSelected = { place ->
                when (type) {
                    AddressType.STARTING_POINT -> startingPoint = place.toAddress()
                    AddressType.DESTINATION -> destination = place.toAddress()
                }
            },
            onError = { showErrorMessage() }
        )
    }

    enum class AddressType {
        STARTING_POINT,
        DESTINATION
    }
}