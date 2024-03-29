package com.leafy.githubsearcher.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.ui.detail.follower.FollowerFragment
import com.leafy.githubsearcher.ui.detail.following.FollowingFragment
import com.leafy.githubsearcher.ui.detail.repository.RepositoryFragment

class SectionPagerAdapter(
    activity: AppCompatActivity,
    private val username: String
) : FragmentStateAdapter(activity) {

    companion object {
        @StringRes
        val TAB_TITLE = intArrayOf(
            R.string.tabRepository, R.string.tabFollower, R.string.tabFollowing
        )
    }

    override fun getItemCount(): Int = TAB_TITLE.size

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        val fragment = when (position) {
            0 -> {
                bundle.putString(RepositoryFragment.EXTRA_DATA, username)
                RepositoryFragment()
            }
            1 -> {
                bundle.putString(FollowerFragment.EXTRA_DATA, username)
                FollowerFragment()
            }
            2 -> {
                bundle.putString(FollowingFragment.EXTRA_DATA, username)
                FollowingFragment()
            }
            else -> throw Throwable("Invalid position: $position")
        }
        fragment.arguments = bundle
        return fragment
    }
}