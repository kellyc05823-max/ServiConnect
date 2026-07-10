package com.example.autonomo.data.repository

import com.example.autonomo.domain.model.User
import com.example.autonomo.domain.model.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                trySend(null)
            } else {
                // Fetch full profile from Firestore
                firestore.collection("users").document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, _ ->
                        val user = snapshot?.toObject(User::class.java)
                        trySend(user)
                    }
            }
        }
        auth.addAuthStateListener(authListener)
        awaitClose { auth.removeAuthStateListener(authListener) }
    }

    override val isUserLoggedIn: Boolean get() = auth.currentUser != null
    override val currentUserId: String? get() = auth.currentUser?.uid

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Login failed")
            val user = fetchUser(firebaseUser.uid) ?: throw Exception("User profile not found")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerWithEmail(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Registration failed")
            val newUser = User(
                uid = firebaseUser.uid,
                firstName = firstName,
                lastName = lastName,
                email = email,
                role = UserRole.CLIENT
            )
            saveUser(newUser)
            Result.success(newUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val firebaseUser = result.user ?: throw Exception("Google login failed")
            
            var user = fetchUser(firebaseUser.uid)
            if (user == null) {
                user = User(
                    uid = firebaseUser.uid,
                    firstName = firebaseUser.displayName?.split(" ")?.firstOrNull() ?: "",
                    lastName = firebaseUser.displayName?.split(" ")?.lastOrNull() ?: "",
                    email = firebaseUser.email ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString()
                )
                saveUser(user)
            }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid ?: throw Exception("No user logged in")
            auth.currentUser?.delete()?.await()
            firestore.collection("users").document(uid).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(user: User): Result<User> {
        return try {
            saveUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun fetchUser(uid: String): User? {
        return firestore.collection("users").document(uid).get().await().toObject(User::class.java)
    }

    private suspend fun saveUser(user: User) {
        firestore.collection("users").document(user.uid).set(user).await()
    }
}
