package com.leafy.githubsearcher.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.utils.CardUserAdapter
import com.leafy.githubsearcher.core.utils.Utility
import com.leafy.githubsearcher.databinding.ActivityHomeBinding
import com.leafy.githubsearcher.ui.detail.DetailActivity
import com.leafy.githubsearcher.ui.settings.SettingActivity
import com.leafy.githubsearcher.utils.Keyboard
import com.leafy.githubsearcher.utils.StartupTheme
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val cardAdapter = CardUserAdapter()
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: ActivityHomeBinding

    private val observer = Observer<Status<List<User>>> { list ->
        if (list != null) {
            with(binding) {
                when (list) {
                    is Status.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        layoutSearchInit.root.visibility = View.GONE
                        layoutNoOutput.root.visibility = View.GONE
                        layoutError.root.visibility = View.GONE
                        rvSearch.visibility = View.GONE
                    }
                    is Status.Empty -> {
                        progressBar.visibility = View.GONE
                        layoutNoOutput.root.visibility = View.VISIBLE
                    }
                    is Status.Success -> {
                        progressBar.visibility = View.GONE
                        rvSearch.visibility = View.VISIBLE
                        cardAdapter.setData(list.data)
                    }
                    is Status.Error -> {
                        progressBar.visibility = View.GONE
                        layoutError.root.visibility = View.VISIBLE
                        layoutError.tvError.text = list.message
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve theme at init
        StartupTheme.getTheme(this@HomeActivity)

        cardAdapter.onItemClick = { data ->
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        val imgWidth = resources.getDimension(R.dimen.image_width) + 2 * resources.getDimension(R.dimen.image_margin)

        with(binding.rvSearch) {
            layoutManager = GridLayoutManager(
                    this@HomeActivity,
                    Utility.getNumberOfColumn(this@HomeActivity, imgWidth)
            )
            setHasFixedSize(true)
            adapter = cardAdapter
        }

        binding.fabFavorite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("githubsearcher://favorite")))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_tool, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.toolSearch)?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        Keyboard.hide(this@with)
                        viewModel.getSearchList(query)
                        viewModel.list.observe(this@HomeActivity, observer)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toolSetting)
            startActivity(Intent(this@HomeActivity, SettingActivity::class.java))
        return super.onOptionsItemSelected(item)
    }
}