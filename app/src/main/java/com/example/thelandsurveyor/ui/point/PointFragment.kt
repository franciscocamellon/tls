package com.example.thelandsurveyor.ui.point

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thelandsurveyor.R

class PointFragment : Fragment() {

    companion object {
        fun newInstance() = PointFragment()
    }

    private lateinit var viewModel: PointViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_point, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PointViewModel::class.java)
        // TODO: Use the ViewModel
    }

}