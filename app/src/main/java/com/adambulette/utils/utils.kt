@file:Suppress("DEPRECATION")

package com.adambulette.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsControllerCompat
import com.adambulette.R
import com.adambulette.databinding.LogoutBinding


fun toast(context: Context, message: String) {
    Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show()
}

fun isConnected(mContext: Context): Boolean {
    val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun isTablet(context: Context): Boolean {
    return (context.resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun Activity.changeStatusBarColor(color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}

object KeyboardUtils {
    @Suppress("unused")
    val TAG = KeyboardUtils::class.simpleName

    fun hideKeyboard(context: Context?) {
        if (context is Activity) {
            val focusedView = context.currentFocus
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                focusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

interface NewYesNoListener {
    fun onAffirmative()
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun returnPermission13(): ArrayList<String> {
    val permissionList = ArrayList<String>()
    permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
    permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
    permissionList.add(Manifest.permission.INTERNET)
    return permissionList
}

@RequiresApi(Build.VERSION_CODES.P)
fun returnPermission(): ArrayList<String> {
    val permissionList = ArrayList<String>()
    permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
    permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
    permissionList.add(Manifest.permission.INTERNET)
    return permissionList
}

class LogoutDialog(context: Context, var listner: NewYesNoListener) : Dialog(context, R.style.Theme_Dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = LogoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        val window = window
        val wlp = window!!.attributes
        window.attributes = wlp
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            btLogout.setOnClickListener {
                listner.onAffirmative()
                dismiss()
            }
            tvDismiss.setOnClickListener {
                dismiss()
            }
        }
    }
}






