package com.dadadadev.social_feed.domain.usecases

import com.dadadadev.result.ResponseResult
import com.dadadadev.social_feed.data.SocialFeedLocalDataSource
import com.dadadadev.social_feed.data.SocialFeedRemoteDataSource
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import com.dadadadev.social_feed.domain.model.SocialFeedPost

class AddSocialFeedPostUseCase(
    private val socialFeedRemoteDataStore: SocialFeedRemoteDataSource,
    private val socialFeedLocalDataSource: SocialFeedLocalDataSource
) {
    suspend operator fun invoke(post: SocialFeedPost): Boolean {
        val postDto = SocialFeedPostDto(
            webUrl = post.webUrl,
            tags = post.tags,
            title = post.title,
            abstract = post.abstract,
            imageUrl = post.imageUrl,
            userDescription = post.userDescription,
            timestamp = post.timestamp,
            localImagesUris = post.localImagesUris
        )

        val remoteResult = socialFeedRemoteDataStore.addPost(postDto)
        if (remoteResult is ResponseResult.Success) {
            socialFeedLocalDataSource.savePostWithLocalUris(postDto.copy(
                id = remoteResult.data
            ))
        }

        return when (remoteResult) {
            is ResponseResult.Success -> true
            is ResponseResult.Error -> false
        }
    }
}