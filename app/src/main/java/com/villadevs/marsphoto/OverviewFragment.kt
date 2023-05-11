package com.villadevs.marsphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import coil.load
import com.villadevs.marsphoto.databinding.FragmentOverviewBinding
import com.villadevs.marsphoto.viewmodel.OverviewViewModel

private const val ARG_PARAM1 = "param1"

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(viewLifecycleOwner) { newStatus ->
            //binding.tvMars.text = newStatus
            when (newStatus) {
                OverviewViewModel.MarsApiStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                OverviewViewModel.MarsApiStatus.ERROR -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                OverviewViewModel.MarsApiStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                }
            }
        }



        // Initialize the adapter and set it to the RecyclerView.
        val adapter = PhotoGridAdapter()
        binding.rvRecyclerView.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner) { newPhotos ->
            adapter.submitList(newPhotos)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}