package com.example.userlist.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.models.UserData
import com.example.userlist.utils.Constants
import com.example.userlist.utils.SettingsPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel(
    private val settingsPreferences: SettingsPreferences,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _isLoggedIn.postValue(false)

            firebaseFirestore.collection(Constants.FIREBASE_USER_DB).document(email).get()
                .addOnSuccessListener { value ->
                    if (value == null) {
                        _isLoading.postValue(false)
                        _message.postValue("User isn't registered yet!")
                        _isLoggedIn.postValue(false)
                    } else {
                        var flag = false
                        var userData: UserData? = null

                        if (value.id == email) {
                            flag = true
                            userData = value.toObject(UserData::class.java)
                        }

                        if (flag) {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    _isLoading.postValue(false)

                                    viewModelScope.launch {
                                        settingsPreferences.setCurrentUser(
                                            userData!!.username,
                                            email,
                                            userData.githubUsername,
                                            userData.nik
                                        )
                                        settingsPreferences.setIsUserLoggedIn(true)
                                    }

                                    _isLoggedIn.postValue(true)
                                }
                                .addOnFailureListener {
                                    _isLoading.postValue(false)
                                    _message.postValue("Failed to login user! error : ${it.message}")
                                    _isLoggedIn.postValue(false)
                                }
                        } else {
                            _isLoading.postValue(false)
                            _message.postValue("User isn't registered yet!")
                            _isLoggedIn.postValue(false)
                        }
                    }
                }
                .addOnFailureListener {
                    _isLoading.postValue(false)
                    _message.postValue("Failed to login user! error : ${it.message}")
                    _isLoggedIn.postValue(false)
                }
        }
    }
}