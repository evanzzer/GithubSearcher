package com.leafy.githubsearcher.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.leafy.githubsearcher.R
import com.leafy.githubsearcher.ui.detail.follower.FollowerFragment
import com.leafy.githubsearcher.ui.detail.following.FollowingFragment
import com.leafy.githubsearcher.ui.detail.repository.RepositoryFragment

class SectionPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val username: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tabRepository, R.string.tabFollower, R.string.tabFollowing
        )
    }

    override fun getCount(): Int = TAB_TITLE.size

    override fun getItem(position: Int): Fragment {
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

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLE[position])
}