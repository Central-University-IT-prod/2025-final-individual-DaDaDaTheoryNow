package com.dadadadev.social_feed.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.result.ResponseResult
import com.dadadadev.social_feed.domain.model.SocialFeedPost
import com.dadadadev.social_feed.domain.usecases.AddSocialFeedPostToFavoriteUseCase
import com.dadadadev.social_feed.domain.usecases.AddSocialFeedPostUseCase
import com.dadadadev.social_feed.domain.usecases.GetSocialFeedPostsUseCase
import com.dadadadev.social_feed.domain.usecases.RemoveSocialFeedPostFromFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SocialFeedViewModel(
    private val addSocialFeedPostUseCase: AddSocialFeedPostUseCase,
    private val addSocialFeedPostToFavoriteUseCase: AddSocialFeedPostToFavoriteUseCase,
    private val removeSocialFeedPostFromFavoriteUseCase: RemoveSocialFeedPostFromFavoriteUseCase,
    private val getSocialFeedPostsUseCase: GetSocialFeedPostsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SocialFeedState())
    val state = _state.asStateFlow()

    init {
        observePosts()
    }

    private fun observePosts() {
        _state.update { it.copy(isLoading = true) }

        getSocialFeedPostsUseCase()
            .onEach { result ->
                _state.update { it.copy(isLoading = false) }
                if (result is ResponseResult.Success) {
                    _state.update { it.copy(posts = result.data.sortedByDescending { post ->
                        post.timestamp
                    }) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SocialFeedAction) {
        when (action) {
            is SocialFeedAction.ShareArticle -> {
                viewModelScope.launch {
                    addSocialFeedPostUseCase(
                        SocialFeedPost(
                            tags = action.listOfTags,
                            title = action.article.title ?: "Без заголовка",
                            webUrl = action.article.url,
                            abstract = action.article.abstract,
                            imageUrl = action.article.image ?: "",
                            userDescription = action.userDescription,
                            localImagesUris = action.listOfUris
                        )
                    )
                }
            }

            is SocialFeedAction.TogglePostToFavorite -> {
                viewModelScope.launch {
                    if (action.post.isFavorite) {
                        removeSocialFeedPostFromFavoriteUseCase(action.post.id)
                    } else {
                        addSocialFeedPostToFavoriteUseCase(action.post)
                    }
                }
            }
        }
    }
}