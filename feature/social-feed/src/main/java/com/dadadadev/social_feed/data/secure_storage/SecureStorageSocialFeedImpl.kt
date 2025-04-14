package com.dadadadev.social_feed.data.secure_storage

import android.content.SharedPreferences
import android.util.Log
import com.dadadadev.social_feed.data.SocialFeedLocalDataSource
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json

class SecureStorageSocialFeedImpl(
    private val prefs: SharedPreferences,
    private val json: Json,
) : SocialFeedLocalDataSource {
    override suspend fun savePostWithLocalUris(post: SocialFeedPostDto) {
        val currentPosts = getCurrentPosts()
        val updatedPosts = currentPosts
            .filterNot { it.id == post.id } + post.copyFromExisting(currentPosts.find { it.id == post.id })

        savePosts(updatedPosts)
    }

    override suspend fun saveFavoritePost(post: SocialFeedPostDto) {
        val currentPosts = getCurrentPosts()
        val updatedPosts = currentPosts.map {
            if (it.id == post.id) it.copy(isFavorite = true) else it
        }

        if (updatedPosts.none { it.id == post.id }) {
            savePosts(updatedPosts + post.copy(isFavorite = true))
        } else {
            savePosts(updatedPosts)
        }
    }

    override suspend fun removeFavoritePostWithId(postId: String) {
        val currentPosts = getCurrentPosts()
        savePosts(
            currentPosts.map {
                if (it.id == postId) it.copy(isFavorite = false) else it
            }
        )
    }

    override fun getLocalPosts(): Flow<List<SocialFeedPostDto>> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            trySend(getCurrentPosts()).isSuccess
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        send(getCurrentPosts())
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    private fun SocialFeedPostDto.copyFromExisting(existing: SocialFeedPostDto?): SocialFeedPostDto =
        existing?.let {
            this.copy(
                isFavorite = it.isFavorite,
                localImagesUris = (it.localImagesUris + this.localImagesUris).distinct()
            )
        } ?: this

    private fun getCurrentPosts(): List<SocialFeedPostDto> =
        prefs.getString(ALL_POSTS_KEY, null)
            ?.let { json.decodeFromString(it) }
            ?: emptyList()

    private fun savePosts(posts: List<SocialFeedPostDto>) {
        prefs.edit().putString(ALL_POSTS_KEY, json.encodeToString(posts)).apply()
    }

    private companion object {
        const val ALL_POSTS_KEY = "social_feed_posts"
    }
}