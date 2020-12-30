package com.leafy.githubsearcher.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DATA = "userdata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val user = intent.getParcelableExtra<User>(EXTRA_DATA)
        if (user == null) {
            AlertDialog.Builder(this@DetailActivity)
                    .setTitle("Error")
                    .setMessage("Something went wrong.")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            finish()
        } else {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            var statusFavorite = false

            viewModel.getFavorite(user.username).observe(this, { data ->
                binding.fabFavorite.visibility = View.VISIBLE
                statusFavorite = data?.isFavorite ?: false
                setFavoriteStatus(statusFavorite)
            })

            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavorite(user, statusFavorite)
                setFavoriteStatus(statusFavorite)
            }

            loadDetails(user)
        }
    }

    private fun loadDetails(user: User) {
        binding.collapsingToolbar.title = user.username

        Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(binding.avatar)

        viewModel.getDetails(user.username).observe(this, { details ->
            if (details != null) {
                when (details) {
                    is Status.Success -> {
                        binding.progressBar.visibility = View.GONE
                        with(binding.layoutContent) {
                            layoutNestedDetail.visibility = View.VISIBLE
                            name.text = details.data?.name
                            link.text = details.data?.githubUrl
                            location.text = details.data?.location
                            company.text = details.data?.company
                            email.text = details.data?.email
                            repository.text = details.data?.repository?.toString()
                            follower.text = details.data?.follower?.toString()
                            following.text = details.data?.following?.toString()
                        }
                    }
                    is Status.Empty -> finish() // Not Used
                    is Status.Error -> {
                        binding.progressBar.visibility = View.GONE
                        AlertDialog.Builder(this@DetailActivity)
                                .setTitle("Error")
                                .setMessage(details.message)
                                .setPositiveButton("OK") { _, _ -> }
                                .show()
                        finish()
                    }
                    is Status.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layoutContent.layoutNestedDetail.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setFavoriteStatus(statusFavorite: Boolean) {
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity,
                if (statusFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        ))
    }
}