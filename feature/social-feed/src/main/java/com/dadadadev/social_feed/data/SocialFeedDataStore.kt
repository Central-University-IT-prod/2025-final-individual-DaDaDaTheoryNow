package com.dadadadev.social_feed.data

import com.dadadadev.result.ResponseResult
import com.dadadadev.social_feed.core.FireStoreError
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import kotlinx.coroutines.flow.Flow

interface SocialFeedRemoteDataSource {
    suspend fun addPost(post: SocialFeedPostDto): ResponseResult<String, FireStoreError>
    fun getPosts(): Flow<ResponseResult<List<SocialFeedPostDto>, FireStoreError>>
}

interface SocialFeedLocalDataSource {
    suspend fun savePostWithLocalUris(post: SocialFeedPostDto)
    suspend fun saveFavoritePost(post: SocialFeedPostDto)
    suspend fun removeFavoritePostWithId(postId: String)
    fun getLocalPosts(): Flow<List<SocialFeedPostDto>>
}
