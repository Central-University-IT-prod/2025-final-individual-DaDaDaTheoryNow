package com.dadadadev.social_feed.di

import androidx.security.crypto.EncryptedSharedPreferences
import com.dadadadev.social_feed.data.SocialFeedLocalDataSource
import com.dadadadev.social_feed.data.SocialFeedRemoteDataSource
import com.dadadadev.social_feed.data.firestore.FireStoreSocialFeedImpl
import com.dadadadev.social_feed.data.secure_storage.SecureStorageSocialFeedImpl
import com.dadadadev.social_feed.domain.usecases.AddSocialFeedPostToFavoriteUseCase
import com.dadadadev.social_feed.domain.usecases.AddSocialFeedPostUseCase
import com.dadadadev.social_feed.domain.usecases.GetSocialFeedPostsUseCase
import com.dadadadev.social_feed.domain.usecases.RemoveSocialFeedPostFromFavoriteUseCase
import com.dadadadev.social_feed.presentation.view_model.SocialFeedViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val socialFeedModule = module {
    single { Json { encodeDefaults = true } }
    single { Firebase.firestore }
    single {
        @Suppress("DEPRECATION")
        EncryptedSharedPreferences.create(
            "secure_prefs",
            "secure_prefs_alias",
            get(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Repository
    singleOf(::FireStoreSocialFeedImpl).bind<SocialFeedRemoteDataSource>()
    singleOf(::SecureStorageSocialFeedImpl).bind<SocialFeedLocalDataSource>()

    // UseCases
    singleOf(::AddSocialFeedPostUseCase)
    singleOf(::AddSocialFeedPostToFavoriteUseCase)
    singleOf(::RemoveSocialFeedPostFromFavoriteUseCase)
    singleOf(::GetSocialFeedPostsUseCase)


    // ViewModel
    viewModelOf(::SocialFeedViewModel)
}