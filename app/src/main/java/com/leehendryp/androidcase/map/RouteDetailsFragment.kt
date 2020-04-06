package com.leehendryp.androidcase.map

import android.graphics.Color.CYAN
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.databinding.RouteDetailsFragmentBinding
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import javax.inject.Inject

class RouteDetailsFragment : Fragment(), OnMapReadyCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: RouteDetailsFragmentBinding

    private val viewModel: RouteDetailsViewModel by viewModels { viewModelFactory }

    private var routeWithAnttPrices: RouteWithAnttPrices? = null

    private lateinit var map: GoogleMap

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
        retrieveRouteWithAnttPrices()
        observeViewModel()
        initMapFragment()
    }

    private fun retrieveRouteWithAnttPrices() {
        val safeArgs: RouteDetailsFragmentArgs by navArgs()
        routeWithAnttPrices = safeArgs.routeWithAnttPrices
    }

    private fun observeViewModel() = Unit

    private fun initMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.fragment_route_details_map
        ) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let { this.map = it }
        routeWithAnttPrices?.let { showRoute(it.route[0]) }
    }

    private fun showRoute(route: List<List<Double>>) {
        val coordinates: List<List<Double>> = route
        val polylineOptions = createPolylineOption()
        val boundsBuilder = LatLngBounds.Builder()

        polylineOptions.addRoutePoints(route, boundsBuilder)

        map.apply {
            addPolyline(polylineOptions)
            moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    boundsBuilder.build(),
                    resources.getDimension(R.dimen.four_measures).toInt()
                )
            )
            addMarkers(coordinates)
        }
    }

    private fun GoogleMap.addMarkers(coordinates: List<List<Double>>) {
        val origin = coordinates[0]
        val destination = coordinates[1]

        val (originLongitude,
            originLatitude) = Pair(origin[0], origin[1])

        val (destinationLongitude,
            destinationLatitude) = Pair(destination[0], destination[1])

        addMarker(MarkerOptions().position(LatLng(originLatitude, originLongitude)))
        addMarker(MarkerOptions().position(LatLng(destinationLatitude, destinationLongitude)))
    }

    private fun PolylineOptions.addRoutePoints(
        route: List<List<Double>>,
        boundsBuilder: LatLngBounds.Builder
    ) {
        route.indices.forEach { index ->
            val point = LatLng(route[index][1], route[index][0])
            add(point)
            boundsBuilder.include(point)
        }
    }

    private fun createPolylineOption(): PolylineOptions = PolylineOptions()
        .width(12f)
        .color(context?.let { ContextCompat.getColor(it, R.color.colorAccent) } ?: CYAN)
        .geodesic(true)
}
