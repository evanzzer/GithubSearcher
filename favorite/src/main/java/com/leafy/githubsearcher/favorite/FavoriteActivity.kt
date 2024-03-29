package com.leafy.githubsearcher.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.leafy.githubsearcher.core.R
import com.leafy.githubsearcher.core.utils.CardUserAdapter
import com.leafy.githubsearcher.core.utils.Utility
import com.leafy.githubsearcher.favorite.databinding.ActivityFavoriteBinding
import com.leafy.githubsearcher.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(FavoriteModule.favoriteModule)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.favorite)

        val cardAdapter = CardUserAdapter()
        cardAdapter.onItemClick = { data ->
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        viewModel.list.observe(this) { list ->
            cardAdapter.setData(list)
            binding.layoutNoOutput.root.visibility =
                if (list.isNotEmpty()) View.GONE else View.VISIBLE
        }

        val imgWidth =
            resources.getDimension(R.dimen.image_width) + 2 * resources.getDimension(R.dimen.image_margin)

        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(
                this@FavoriteActivity,
                Utility.getNumberOfColumn(this@FavoriteActivity, imgWidth)
            )
            setHasFixedSize(true)
            adapter = cardAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}