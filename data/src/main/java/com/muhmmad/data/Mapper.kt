package com.muhmmad.data

import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.qaree.ForgetPasswordMutation
import com.muhmmad.qaree.ResendPasswordOTPMutation
import com.muhmmad.qaree.ResendVerificationOTPMutation
import com.muhmmad.qaree.ResetPasswordMutation
import com.muhmmad.qaree.SignInMutation
import com.muhmmad.qaree.ValidatePasswordOTPMutation
import com.muhmmad.qaree.VerifyAccountMutation

fun SignInMutation.Signin.toLoginResponse(): LoginResponse = LoginResponse(
    message = message ?: "",
    token = access_token ?: "",
    error = ""
)

fun VerifyAccountMutation.VerifyAccount.toVerificationResponse(): BaseResponse =
    BaseResponse(
        message = message ?: "",
        success = success ?: false
    )

fun ResendVerificationOTPMutation.ResendValidatingOTP.toVerificationResponse(): BaseResponse =
    BaseResponse(
        message = message ?: "",
        success = success ?: false
    )

fun ForgetPasswordMutation.ForgetPassword.toVerificationResponse(): BaseResponse =
    BaseResponse(
        message = message ?: "",
        success = success ?: false
    )

fun ValidatePasswordOTPMutation.ValidateResetPasswordOTP.toValidateOTPResponse(): ValidatePasswordOTPResponse =
    ValidatePasswordOTPResponse(
        message = message ?: "",
        success = success ?: false,
        reset_token = reset_token ?: ""
    )

fun ResendPasswordOTPMutation.ResendResetPasswordOTP.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = success ?: false
)

fun ResetPasswordMutation.ResetPassword.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = false
)