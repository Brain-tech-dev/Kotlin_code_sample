package com.adambulette.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.adambulette.R
import com.adambulette.databinding.FragmentLoginBinding
import com.adambulette.network.apiRequest.LoginRequest
import com.adambulette.utils.KEY_REMEMBER_ME
import com.adambulette.utils.KEY_USER_DATA
import com.adambulette.utils.Resource
import com.adambulette.utils.displayTopSnackBar
import com.adambulette.utils.handleApiErrors
import com.adambulette.utils.hideKeyboard
import com.adambulette.utils.isConnected
import com.adambulette.utils.toast
import com.adambulette.utils.toggleFieldVisibility
import com.adambulette.view.MainActivity
import com.adambulette.viewmodel.LoginViewModel
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by inject()

    private var isRemember = true
    private var isShowPassword = false

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        manageClickListener()
        this.hideKeyboard()
        return binding.root
    }

    private fun manageClickListener() {
        binding.rememberMeCB.setOnClickListener { isRemember = (it as CheckBox).isChecked }
        binding.btnContinue.setOnClickListener { onLoginButtonClick() }
        binding.passwordEyeIV.setOnClickListener { togglePasswordVisibility() }
        binding.forgotPasswordTV.setOnClickListener {
            toast("Forgot Password Screen Will Open On This Click")
        }
    }

    private fun onLoginButtonClick() {
        val userName = binding.emailET.text.toString().trim()
        val password = binding.passwordET.text.toString().trim()
        val companyId = binding.companyCodeET.text.toString().trim()
        val vehicleNo = binding.vehicleET.text.toString().trim()

        hideKeyboard()
        validateInputs(userName, password, companyId, vehicleNo)
    }

    private fun togglePasswordVisibility() {
        isShowPassword = !isShowPassword
        toggleFieldVisibility(binding.passwordET, binding.passwordEyeIV, isShowPassword)
    }

    private fun validateInputs(
        userName: String, password: String, companyId: String, vehicleNo: String
    ) {
        when {
            companyId.isEmpty() -> displayTopSnackBar(
                binding.root, R.string.valid_company_code, requireContext()
            )

            userName.isEmpty() -> displayTopSnackBar(
                binding.root, R.string.enter_username, requireContext()
            )

            password.isEmpty() -> displayTopSnackBar(
                binding.root, R.string.valid_password, requireContext()
            )

            vehicleNo.isEmpty() -> displayTopSnackBar(
                binding.root, R.string.valid_vehicle_number, requireContext()
            )

            else -> loginRequest(userName, password, companyId, vehicleNo)
        }
    }

    private fun loginRequest(
        userName: String, password: String, companyId: String, vehicleNo: String
    ) {
        if (isConnected(requireContext())) {
            val requestData = LoginRequest(
                username = userName.replace("\\s".toRegex(), ""),
                password = password,
                companyId = companyId,
                vehicle_no = vehicleNo
            )
            loginApi(requestData)
        } else {
            displayTopSnackBar(binding.root, R.string.no_internet_connection, requireContext())
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loginApi(requestData: LoginRequest) {
        viewModel.userLogin(requestData)
        viewModel.loginData.observe(this@LoginFragment) {
            when (it) {
                is Resource.Loading -> (requireActivity() as MainActivity).showLoader()
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideLoader()
                    if (it.values.status) {
                        Hawk.put(KEY_REMEMBER_ME, isRemember)
                        displayTopSnackBar(
                            binding.root, it.values.message, requireContext(), color = R.color.green
                        )
                        Hawk.put(KEY_USER_DATA, it.values)
                    } else {
                        displayTopSnackBar(binding.root, it.values.message, requireContext())
                        viewModel.loginData.removeObservers(this)
                    }
                    viewModel.loginData.removeObservers(this)
                }

                is Resource.Failure -> handleApiErrors(it) {
                    viewModel.loginData.removeObservers(this)
                }
            }
        }
    }
}