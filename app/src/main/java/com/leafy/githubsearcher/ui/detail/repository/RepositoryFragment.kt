package com.leafy.githubsearcher.ui.detail.repository

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.utils.ListRepositoryAdapter
import com.leafy.githubsearcher.databinding.FragmentRepositoryBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryFragment : Fragment() {
    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoryViewModel by viewModel()

    companion object {
        const val EXTRA_DATA = "repositoryUser"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val listAdapter = ListRepositoryAdapter()

            listAdapter.onItemClick = { data ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.url)))
            }

            if (arguments != null) {
                val username = arguments?.getString(EXTRA_DATA)

                if (username != null)
                    viewModel.getRepository(username).observe(viewLifecycleOwner, { list ->
                        if (list != null) {
                            with(binding) {
                                when (list) {
                                    is Status.Success -> {
                                        progressBar.visibility = View.GONE
                                        rvRepository.visibility = View.VISIBLE
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
                                        rvRepository.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    })
            } else {
                with(binding) {
                    layoutNoOutput.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    rvRepository.visibility = View.GONE
                }
            }

            with(binding.rvRepository) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = listAdapter
            }
        }
    }
}