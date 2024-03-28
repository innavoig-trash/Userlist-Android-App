package com.example.userlist.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.models.UserData
import com.example.userlist.utils.Constants.FIREBASE_USER_DB
import com.example.userlist.utils.SettingsPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val settingsPreferences: SettingsPreferences,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCreated = MutableLiveData(false)
    val isCreated: LiveData<Boolean> = _isCreated

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun registerUser(
        username: String,
        email: String,
        githubUsername: String,
        nik: String,
        password: String,
    ) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            val userData = UserData(
                username, email, githubUsername, nik, password
            )

            firebaseFirestore.collection(FIREBASE_USER_DB).document(email).get()
                .addOnSuccessListener { value ->
                    if (value == null) {
                        firebaseFirestore.collection(FIREBASE_USER_DB).document(email).set(
                            userData
                        ).addOnSuccessListener {
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    _isLoading.postValue(false)

                                    viewModelScope.launch {
                                        settingsPreferences.setCurrentUser(
                                            username, email, githubUsername, nik
                                        )
                                    }

                                    _message.postValue("Successfully registered user! Please Log In")
                                    _isCreated.postValue(true)
                                }
                                .addOnFailureListener {
                                    _isLoading.postValue(false)
                                    _message.postValue("Failed to register user! error : ${it.message}")
                                    _isCreated.postValue(false)
                                }
                        }.addOnFailureListener {
                            _isLoading.postValue(false)
                            _message.postValue("Failed to register user! error : ${it.message}")
                            _isCreated.postValue(false)
                        }
                    } else {
                        var flag = false

                        for (id in value.id) {
                            if (id.toString() == email) {
                                flag = true
                                break
                            }
                        }

                        if (!flag) {
                            firebaseFirestore.collection(FIREBASE_USER_DB).document(email).set(
                                userData
                            ).addOnSuccessListener {
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnSuccessListener {
                                        _isLoading.postValue(false)

                                        viewModelScope.launch {
                                            settingsPreferences.setCurrentUser(
                                                username, email, githubUsername, nik
                                            )
                                        }

                                        _message.postValue("Successfully registered user! Please Log In")
                                        _isCreated.postValue(true)
                                    }
                                    .addOnFailureListener {
                                        _isLoading.postValue(false)
                                        _message.postValue("Failed to register user! error : ${it.message}")
                                        _isCreated.postValue(false)
                                    }
                            }.addOnFailureListener {
                                _isLoading.postValue(false)
                                _message.postValue("Failed to register user! error : ${it.message}")
                                _isCreated.postValue(false)
                            }
                        } else {
                            _isLoading.postValue(false)
                            _message.postValue("User with email: $email is already registered!")
                            _isCreated.postValue(false)
                        }
                    }
                }
                .addOnFailureListener {
                    _isLoading.postValue(false)
                    _message.postValue("Failed to register user! error : ${it.message}")
                    _isCreated.postValue(false)
                }
        }
    }
}