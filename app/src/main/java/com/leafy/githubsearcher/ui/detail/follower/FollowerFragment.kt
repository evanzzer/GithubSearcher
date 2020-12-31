package com.leafy.githubsearcher.ui.detail.follower

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.utils.ListUserAdapter
import com.leafy.githubsearcher.databinding.FragmentFollowerBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FollowerFragment() : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowerViewModel by viewModel()

    companion object {
        const val EXTRA_DATA = "followerUser"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val listAdapter = ListUserAdapter()

            if (arguments != null) {
                val username = arguments?.getString(EXTRA_DATA)

                if (username != null)
                    viewModel.getFollower(username).observe(viewLifecycleOwner, { list ->
                        if (list != null) {
                            with(binding) {
                                when (list) {
                                    is Status.Success -> {
                                        progressBar.visibility = View.GONE
                                        rvFollower.visibility = View.VISIBLE
                                        listAdapter.setData(list.data)
                                    }
                                    is Status.Empty -> {
                                        progressBar.visibility = View.GONE
                                        layoutNoOutput.visibility = View.VISIBLE
                                    }
                                    is Status.Error -> {
                                        progressBar.visibility = View.GONE
                                        layoutNoOutput.visibility = View.VISIBLE
                                        AlertDialog.Builder(context)
                                                .setTitle("Error")
                                                .setMessage("Follower list failed to load\nError: ${list.message}")
                                                .setPositiveButton("OK") { _, _ -> }
                                                .show()
                                    }
                                    is Status.Loading -> {
                                        progressBar.visibility = View.VISIBLE
                                        layoutNoOutput.visibility = View.GONE
                                        rvFollower.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    })
            } else {
                with(binding) {
                    layoutNoOutput.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    rvFollower.visibility = View.GONE
                }
            }

            with(binding.rvFollower) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = listAdapter
            }
        }
    }
}