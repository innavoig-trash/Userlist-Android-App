package com.example.userlist.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

object Constants {
    fun showSnackbar(activity: Activity, view: View, message: String) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    // FIRESTORE
    const val FIREBASE_USER_DB = "users"
}