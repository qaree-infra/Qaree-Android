package com.muhmmad.data

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.VerificationResponse
import com.muhmmad.qaree.ResendVerificationOTPMutation
import com.muhmmad.qaree.SignInMutation
import com.muhmmad.qaree.VerifyAccountMutation

fun SignInMutation.Signin.toLoginResponse(): LoginResponse = LoginResponse(
    message = message ?: "",
    token = access_token ?: "",
    error = ""
)

fun VerifyAccountMutation.VerifyAccount.toVerificationResponse(): VerificationResponse =
    VerificationResponse(
        message = message ?: "",
        success = success ?: false
    )

fun ResendVerificationOTPMutation.ResendValidatingOTP.toVerificationResponse(): VerificationResponse =
    VerificationResponse(
        message = message ?: "",
        success = success ?: false
    )