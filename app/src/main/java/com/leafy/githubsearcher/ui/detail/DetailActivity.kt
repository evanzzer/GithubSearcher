package com.leafy.githubsearcher.ui.detail

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailViewModel>()
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
            AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Something went wrong.")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            finish()
        } else {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val sectionPagerAdapter = SectionPagerAdapter(this, user.username)
            binding.viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
                tab.text = resources.getString(SectionPagerAdapter.TAB_TITLE[position])
            }.attach()

            var statusFavorite = false

            viewModel.getFavorite(user.username).observe(this) { data ->
                binding.fabFavorite.visibility = View.VISIBLE
                statusFavorite = data?.isFavorite ?: false
                setFavoriteStatus(statusFavorite)
            }

            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavorite(user, statusFavorite)
                setFavoriteStatus(statusFavorite)
                Toast.makeText(
                    this,
                    resources.getString(
                        if (statusFavorite) R.string.favMessage else R.string.unfavMessage
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }

            loadDetails(user)
        }
    }

    private fun loadDetails(user: User) {
        binding.collapsingToolbar.title = user.username

        Glide.with(this)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(binding.avatar)

        viewModel.getDetails(user.username).observe(this) { details ->
            if (details != null) {
                when (details) {
                    is Status.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.layoutContentPrimary.root.visibility = View.VISIBLE
                        binding.layoutContentSecondary.root.visibility = View.VISIBLE
                        with(binding.layoutContentPrimary) {
                            name.text = details.data?.name
                            link.text = details.data?.githubUrl?.replace("https://", "")
                            location.text = details.data?.location
                            company.text = details.data?.company
                            email.text = details.data?.email
                        }
                        with(binding.layoutContentSecondary) {
                            repository.text = details.data?.repository?.toString()
                            follower.text = details.data?.follower?.toString()
                            following.text = details.data?.following?.toString()
                        }
                    }
                    is Status.Empty -> {}
                    is Status.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tab.visibility = View.GONE
                        binding.viewPager.visibility = View.GONE
                        binding.layoutError.root.visibility = View.VISIBLE
                        binding.layoutError.tvError.text = details.message
                    }
                    is Status.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layoutContentPrimary.root.visibility = View.GONE
                        binding.layoutContentSecondary.root.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setFavoriteStatus(statusFavorite: Boolean) {
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity,
                if (statusFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        ))
    }
}