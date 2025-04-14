package com.dadadadev.social_feed.domain.usecases

import android.util.Log
import com.dadadadev.result.ResponseResult
import com.dadadadev.social_feed.core.FireStoreError
import com.dadadadev.social_feed.data.SocialFeedLocalDataSource
import com.dadadadev.social_feed.data.SocialFeedRemoteDataSource
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import com.dadadadev.social_feed.domain.model.SocialFeedPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSocialFeedPostsUseCase(
    private val remoteDataSource: SocialFeedRemoteDataSource,
    private val localDataSource: SocialFeedLocalDataSource
) {
    operator fun invoke(): Flow<ResponseResult<List<SocialFeedPost>, FireStoreError>> {
        return remoteDataSource.getPosts().combineWithLocalData()
    }

    private fun Flow<ResponseResult<List<SocialFeedPostDto>, FireStoreError>>.combineWithLocalData() =
        combine(this, localDataSource.getLocalPosts()) { remoteResult, localPosts ->
            when (remoteResult) {
                is ResponseResult.Success -> {
                    ResponseResult.Success(
                        data = mergePosts(
                            remotePosts = remoteResult.data,
                            localPosts = localPosts
                        ).map {
                            it.toDomain()
                        }
                    )
                }
                is ResponseResult.Error -> {
                    val localFallback = localPosts
                        .map { it.toDomain() }

                    if (localFallback.isNotEmpty()) {
                        ResponseResult.Success(localFallback)
                    } else {
                        ResponseResult.Error(remoteResult.error)
                    }
                }
            }
        }

    private fun mergePosts(
        remotePosts: List<SocialFeedPostDto>,
        localPosts: List<SocialFeedPostDto>
    ): List<SocialFeedPostDto> {
        val localPostsMap = localPosts.associateBy { it.id }

        return remotePosts.map { remotePost ->
            localPostsMap[remotePost.id]?.let { localPost ->
                remotePost.mergeWithLocal(localPost)
            } ?: remotePost
        }
    }

    private fun SocialFeedPostDto.mergeWithLocal(local: SocialFeedPostDto) = copy(
        isFavorite = local.isFavorite,
        localImagesUris = local.localImagesUris
    )

    private fun SocialFeedPostDto.toDomain() = SocialFeedPost(
        id = id,
        tags = tags,
        timestamp = timestamp,
        webUrl = webUrl,
        title = title,
        abstract = abstract,
        imageUrl = imageUrl,
        userDescription = userDescription,
        isFavorite = isFavorite,
        localImagesUris = localImagesUris
    )
}