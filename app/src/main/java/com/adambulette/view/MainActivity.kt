package com.adambulette.view

import android.app.FragmentManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.adambulette.R
import com.adambulette.databinding.ActivityMainBinding
import com.adambulette.utils.KEY_REMEMBER_ME
import com.adambulette.utils.Loading
import com.adambulette.utils.addFragment
import com.adambulette.utils.getCurrentFragment
import com.adambulette.utils.hideKeyboard
import com.adambulette.utils.replaceFragment
import com.adambulette.view.fragments.LoginFragment
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val loading: Loading by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.hideKeyboard(binding.root)
        setContentView(binding.root)
        val rememberMe = Hawk.get<Boolean>(KEY_REMEMBER_ME) ?: false
        if (rememberMe) {
            this.moveToFragment(LoginFragment())
        } else {
            this.moveToFragment(LoginFragment())
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fm: FragmentManager = fragmentManager
        if (getCurrentFragment() is LoginFragment /*|| getCurrentFragment() is DashboardFragment*/) {
            finish()
        } else {
            fm.popBackStack()
        }
        super.onBackPressed()
    }

    fun moveToFragment(fragment: Fragment, containerId: Int = R.id.fragmentContainerView) {
        replaceFragment(fragment, containerId, true)
    }

    fun moveToFragmentWithClear(fragment: Fragment, containerId: Int = R.id.fragmentContainerView) {
        replaceFragment(fragment, containerId, addToStack = true, clearBackStack = true)
    }

    fun moveToFragmentAfterAdding(
        fragment: Fragment, containerId: Int = R.id.fragmentContainerView
    ) {
        addFragment(fragment, containerId, true)
    }

    fun showLoader() {
        try {
            loading.hide(this@MainActivity)
            loading.show(this@MainActivity)
        } catch (_: Exception) {

        }
    }

    fun hideLoader() {
        try {
            loading.hide(this@MainActivity)
        } catch (_: Exception) {

        }

    }
}