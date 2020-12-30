package com.leafy.githubsearcher.core.domain.model

data class Detail(
    var username: String,
    var avatarUrl: String,
    var githubUrl: String,
    var name: String,
    var company: String,
    var location: String,
    var email: String,
    var repository: Int = 0,
    var follower: Int = 0,
    var following: Int = 0
)
