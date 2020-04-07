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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.leehendryp.androidcase.R
import com.leehendryp.androidcase.databinding.RouteDetailsFragmentBinding
import com.leehendryp.androidcase.dataentry.domain.Point
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
        routeWithAnttPrices?.let { showRoute(it) }
    }

    private fun showRoute(routeWithAnttPrices: RouteWithAnttPrices) {
        val coordinates: List<List<Double>> = routeWithAnttPrices.route[0]
        val polylineOptions = createPolylineOption()
        val boundsBuilder = LatLngBounds.Builder()

        polylineOptions.addRoutePoints(coordinates, boundsBuilder)

        map.apply {
            addPolyline(polylineOptions)
            moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    boundsBuilder.build(),
                    resources.getDimension(R.dimen.six_measures).toInt()
                )
            )
            addMarkers(routeWithAnttPrices.points)
        }
    }

    private fun GoogleMap.addMarkers(points: List<Point>) {
        points.forEach { coordinates ->
            val (longitude, latitude) =
                Pair(coordinates.point[1], coordinates.point[0])

            addMarker(
                MarkerOptions()
                    .position(LatLng(longitude, latitude))
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            defineMarkerColor(points, coordinates)
                        )
                    )
                    .title(defineTitle(points, coordinates))
            )
        }
    }

    private fun defineMarkerColor(
        points: List<Point>,
        coordinates: Point
    ): Float {
        return when (points.indexOf(coordinates)) {
            0 -> HUE_RED
            else -> HUE_BLUE
        }
    }

    private fun defineTitle(
        points: List<Point>,
        coordinates: Point
    ): String {
        return when (points.indexOf(coordinates)) {
            0 -> getString(R.string.map_origin)
            else -> getString(R.string.map_destination)
        }
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
        .width(resources.getDimension(R.dimen.half_measure))
        .color(context?.let { ContextCompat.getColor(it, R.color.colorAccent) } ?: CYAN)
        .geodesic(true)
}
