package com.leafy.githubsearcher.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.leafy.githubsearcher.core.utils.CardUserAdapter
import com.leafy.githubsearcher.core.R
import com.leafy.githubsearcher.favorite.databinding.ActivityFavoriteBinding
import com.leafy.githubsearcher.ui.detail.DetailActivity
import com.leafy.githubsearcher.utils.Utility
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModel()
    lateinit var binding: ActivityFavoriteBinding

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

        viewModel.list.observe(this, { list ->
            cardAdapter.setData(list)
            binding.layoutNoOutput.visibility = if (list.isNotEmpty()) View.GONE else View.VISIBLE
        })

        val imgWidth = resources.getDimension(R.dimen.image_width) + 2 * resources.getDimension(R.dimen.image_margin)

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