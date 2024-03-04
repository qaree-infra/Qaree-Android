package com.muhmmad.data

import com.muhmmad.domain.model.Author
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.Category
import com.muhmmad.domain.model.Cover
import com.muhmmad.domain.model.Offer
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.qaree.ForgetPasswordMutation
import com.muhmmad.qaree.GetOffersQuery
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

fun GetOffersQuery.GetAllOffers.toOffersResponse(): OffersResponse {
    val list = ArrayList<Offer>()
    offers?.map {
        it?.apply {
            list.add(
                Offer(
                    percent = it.percent ?: 0,
                    expireAt = it.expireAt ?: "",
                    book = Book(
                        price = it.book?.price ?: 0.0,
                        name = it.book?.name ?: "",
                        author = Author(
                            id = "", name = it.book?.author?.name ?: "", avatar = ""
                        ),
                        cover = Cover(
                            path = it.book?.cover?.path ?: "",
                            size = it.book?.cover?.size ?: 0.0
                        ),
                        categories = listOf(
                            Category(
                                id = "",
                                nameAr = "",
                                nameEn = "",
                                image = ""
                            )
                        ),
                        id = it.book?._id ?: ""
                    )
                )
            )
        }
    }
    return OffersResponse(list)
}