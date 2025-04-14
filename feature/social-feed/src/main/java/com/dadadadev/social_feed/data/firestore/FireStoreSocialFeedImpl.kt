package com.dadadadev.social_feed.data.firestore

import com.dadadadev.result.ResponseResult
import com.dadadadev.social_feed.core.FireStoreError
import com.dadadadev.social_feed.data.SocialFeedRemoteDataSource
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FireStoreSocialFeedImpl(
    private val firestore: FirebaseFirestore
) : SocialFeedRemoteDataSource {
    override suspend fun addPost(post: SocialFeedPostDto): ResponseResult<String, FireStoreError>  {
        return try {
            val result = firestore
                .collection(SOCIAL_POSTS_COLLECTION)
                .add(post)
                .await()

            ResponseResult.Success(result.id)
        } catch (e: Exception) {
            ResponseResult.Error(
                when (e) {
                    is FirebaseFirestoreException -> when (e.code) {
                        FirebaseFirestoreException.Code.PERMISSION_DENIED -> FireStoreError.PermissionDenied(e.message ?: "")
                        else -> FireStoreError.CustomError(e.message)
                    }
                    else -> FireStoreError.CustomError(e.localizedMessage)
                }
            )
        }
    }

    override fun getPosts(): Flow<ResponseResult<List<SocialFeedPostDto>, FireStoreError>> = callbackFlow {
        val listener = try {
            firestore.collection(SOCIAL_POSTS_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    try {
                        when {
                            error != null -> {
                                val firestoreError = FireStoreError.CustomError(error.message ?: "Unknown Firestore error")
                                trySend(ResponseResult.Error(firestoreError)).isSuccess
                            }
                            snapshot != null -> {
                                val posts = snapshot.documents.mapNotNull {
                                    it.toObject(SocialFeedPostDto::class.java)
                                }
                                trySend(ResponseResult.Success(posts)).isSuccess
                            }
                        }
                    } catch (e: Exception) {
                        trySend(ResponseResult.Error(FireStoreError.CustomError(e.message ?: "Unknown error"))).isSuccess
                    }
                }
        } catch (e: Exception) {
            trySend(ResponseResult.Error(FireStoreError.CustomError(e.message ?: "Firestore initialization failed"))).isSuccess
            null
        }

        awaitClose { listener?.remove() }
    }


    companion object {
        private const val SOCIAL_POSTS_COLLECTION = "posts"
    }
}