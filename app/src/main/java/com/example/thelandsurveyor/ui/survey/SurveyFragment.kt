package com.example.thelandsurveyor.ui.survey

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thelandsurveyor.R
import com.example.thelandsurveyor.databinding.FragmentSurveyBinding
import com.example.thelandsurveyor.util.callPermissionDialog
import com.example.thelandsurveyor.util.showMessageToUser
import java.util.concurrent.Executor
import java.util.function.Consumer

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    var currentLatitude = String()
    var currentLongitude = String()
    var currentAccuracy = String()
    var currentProvider = String()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val surveyViewModel =
            ViewModelProvider(this)[SurveyViewModel::class.java]

        _binding = FragmentSurveyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button: Button = binding.getLocation
        button.setOnClickListener {
            callAccessLocation()
            Log.d("DEBUG", currentLongitude)
        }

        val textView: TextView = binding.textDashboard

        surveyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callAccessLocation() {

        val permissionAFL = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionACL = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permissionAFL != PackageManager.PERMISSION_GRANTED &&
            permissionACL != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                callPermissionDialog(requireContext(), requireActivity(), getString(R.string.fine_location_permission),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_CODE)
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_CODE)
            }
        } else {
            getCurrentCoordinates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            var i = 0
            while (i < permissions.size) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    when (permissions[i]) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> getCurrentCoordinates()
                    }
                }
                i++
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getCurrentCoordinates() {

        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        Log.d("DEBUG", locationManager.toString())
        if (!isGPSEnabled && !isNetworkEnabled) {
            showMessageToUser(requireContext(), getString(R.string.activate_gps))
        } else {
            if (isGPSEnabled) {
                try {
                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    Log.d("DEBUG", lastLocation.toString())
                    if (lastLocation != null) {
                        binding.latitude.text = lastLocation.latitude.toString()
                        binding.longitude.text = lastLocation.longitude.toString()
                        binding.accuracy.text = lastLocation.accuracy.toString()
                        binding.provider.text = "GPS"
                    }
//                    callWriteOnSDCard()
                } catch(ex: SecurityException) {
                    Log.d(getString(R.string.log_tag), getString(R.string.security_exception))
                }
            }
            else if (isNetworkEnabled) {
                try {
                    val lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (lastLocation != null) {
                        binding.latitude.text = lastLocation.latitude.toString()
                        binding.longitude.text = lastLocation.longitude.toString()
                        binding.accuracy.text = lastLocation.accuracy.toString()
                        binding.provider.text = "GPS"
                    }
//                    callWriteOnSDCard()
                } catch(ex: SecurityException) {
                    Log.d(getString(R.string.log_tag), getString(R.string.security_exception))
                }
            }
        }
    }


}