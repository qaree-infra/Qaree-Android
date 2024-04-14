package com.muhmmad.data.remote

import com.apollographql.apollo3.ApolloClient
import com.muhmmad.data.utils.toActivityResponse
import com.muhmmad.data.utils.toAuthorsResponse
import com.muhmmad.data.utils.toBaseResponse
import com.muhmmad.data.utils.toBookResponse
import com.muhmmad.data.utils.toBooksResponse
import com.muhmmad.data.utils.toCategoriesResponse
import com.muhmmad.data.utils.toLibraryResponse
import com.muhmmad.data.utils.toLoginResponse
import com.muhmmad.data.utils.toOffersResponse
import com.muhmmad.data.utils.toReviewsResponse
import com.muhmmad.data.utils.toShelfResponse
import com.muhmmad.data.utils.toUser
import com.muhmmad.data.utils.toValidateOTPResponse
import com.muhmmad.data.utils.toVerificationResponse
import com.muhmmad.data.utils.checkResponse
import com.muhmmad.data.utils.toBookContent
import com.muhmmad.data.utils.toBookStatus
import com.muhmmad.data.utils.toPaymentOrder
import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.NetworkResponse
import com.muhmmad.domain.model.NetworkResponse.Error
import com.muhmmad.domain.model.NetworkResponse.Success
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.PaymentOrder
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.model.ShelfResponse
import com.muhmmad.domain.model.User
import com.muhmmad.domain.remote.GraphQlDataSource
import com.muhmmad.qaree.AddBookToShelfMutation
import com.muhmmad.qaree.CompletePaymentOrderMutation
import com.muhmmad.qaree.CreatePaymentOrderMutation
import com.muhmmad.qaree.CreateShelfMutation
import com.muhmmad.qaree.ForgetPasswordMutation
import com.muhmmad.qaree.GetBestSellerBooksQuery
import com.muhmmad.qaree.GetBookContentQuery
import com.muhmmad.qaree.GetBookReviewsQuery
import com.muhmmad.qaree.GetBookStatusQuery
import com.muhmmad.qaree.GetBooksQuery
import com.muhmmad.qaree.GetCategoriesQuery
import com.muhmmad.qaree.GetLastActivityQuery
import com.muhmmad.qaree.GetLibraryQuery
import com.muhmmad.qaree.GetOffersQuery
import com.muhmmad.qaree.GetShelfDetailsQuery
import com.muhmmad.qaree.GetTopAuthorsQuery
import com.muhmmad.qaree.GetUserInfoQuery
import com.muhmmad.qaree.JoinCommunityMutation
import com.muhmmad.qaree.RemoveBookFromShelfMutation
import com.muhmmad.qaree.RemoveShelfMutation
import com.muhmmad.qaree.ResendPasswordOTPMutation
import com.muhmmad.qaree.ResendVerificationOTPMutation
import com.muhmmad.qaree.ResetPasswordMutation
import com.muhmmad.qaree.ReviewBookMutation
import com.muhmmad.qaree.SearchQuery
import com.muhmmad.qaree.SignInMutation
import com.muhmmad.qaree.SignUpMutation
import com.muhmmad.qaree.UpdatePasswordMutation
import com.muhmmad.qaree.UpdateUserBioMutation
import com.muhmmad.qaree.UpdateUserNameMutation
import com.muhmmad.qaree.ValidatePasswordOTPMutation
import com.muhmmad.qaree.VerifyAccountMutation

class GraphQlDataSourceImpl(
    private val apolloClient: ApolloClient
) : GraphQlDataSource {
    override suspend fun login(email: String, pass: String): NetworkResponse<LoginResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(SignInMutation(email, pass))
                    .execute()
            )

            when (response) {
                is Success -> Success(response.data?.signin?.toLoginResponse()!!)
                else -> Error(response.message!!)
            }
        } catch (exception: Exception) {
            Error(
                exception.localizedMessage?.toString()!!
            )
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        pass: String
    ): NetworkResponse<String> {
        return try {
            val response =
                checkResponse(apolloClient.mutation(SignUpMutation(name, email, pass)).execute())

            when (response) {
                is Success -> Success(response.data?.signup?.message.toString())
                else -> Error(response.message.toString())
            }

        } catch (ex: Exception) {
            Error(
                ex.localizedMessage?.toString()!!
            )
        }
    }

    override suspend fun verifyAccount(
        email: String,
        otp: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(VerifyAccountMutation(otp = otp, email = email)).execute()
            )

            when (response) {
                is Success -> Success(response.data?.verifyAccount?.toVerificationResponse()!!)
                else -> Error(response.message.toString())
            }

        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun resendVerifyOTP(email: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(ResendVerificationOTPMutation(email = email)).execute()
            )

            when (response) {
                is Success -> Success(response.data?.resendValidatingOTP?.toVerificationResponse()!!)
                else -> Error(
                    response.message.toString()
                )
            }
        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun forgotPassword(email: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(ForgetPasswordMutation(email = email)).execute()
            )

            when (response) {
                is Success -> Success(response.data?.forgetPassword?.toVerificationResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun validatePasswordOTP(
        email: String,
        otp: String
    ): NetworkResponse<ValidatePasswordOTPResponse> {
        try {
            val response = checkResponse(
                apolloClient.mutation(
                    ValidatePasswordOTPMutation(
                        email = email,
                        otp = otp
                    )
                ).execute()
            )

            return when (response) {
                is Success -> Success(response.data?.validateResetPasswordOTP?.toValidateOTPResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            return Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun resendPasswordOTP(email: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(ResendPasswordOTPMutation(email)).execute()
            )

            when (response) {
                is Success -> Success(response.data?.resendResetPasswordOTP?.toBaseResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun resetPassword(pass: String, token: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(ResetPasswordMutation(pass))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.resetPassword?.toBaseResponse()!!)
                else -> Error(response.message.toString())
            }

        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun getOffers(): NetworkResponse<OffersResponse> {
        return try {
            when (val response = checkResponse(apolloClient.query(GetOffersQuery()).execute())) {
                is Success -> Success(response.data?.getAllOffers?.toOffersResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.localizedMessage?.toString()!!)
        }
    }

    override suspend fun getLastActivity(token: String): NetworkResponse<ActivityResponse> {
        return try {
            val response = checkResponse(
                apolloClient.query(GetLastActivityQuery()).addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> Success(response.data?.getLastActivity?.toActivityResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Error("")
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getTopAuthors(): NetworkResponse<AuthorsResponse> {
        return try {
            val response = checkResponse(apolloClient.query(GetTopAuthorsQuery()).execute())

            when (response) {
                is Success -> Success(response.data?.getTopAuthors?.toAuthorsResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getNewReleaseBooks(): NetworkResponse<BooksResponse> {
        return try {
            when (val response = checkResponse(apolloClient.query(GetBooksQuery("")).execute())) {
                is Success -> Success(response.data?.toBooksResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getBestSellerBooks(): NetworkResponse<BooksResponse> {
        return try {
            val response = checkResponse(apolloClient.query(GetBestSellerBooksQuery()).execute())
            when (response) {
                is Success -> Success(response.data?.toBookResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getBooksByCategory(categoryId: String): NetworkResponse<BooksResponse> {
        return try {
            when (val response =
                checkResponse(apolloClient.query(GetBooksQuery(categoryId)).execute())) {
                is Success -> Success(response.data?.toBooksResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getCategories(): NetworkResponse<CategoriesResponse> {
        return try {
            val response = checkResponse(apolloClient.query(GetCategoriesQuery()).execute())

            when (response) {
                is Success -> Success(response.data?.getAllCategories?.toCategoriesResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getLibrary(token: String): NetworkResponse<LibraryResponse> {
        return try {
            val response = checkResponse(
                apolloClient.query(GetLibraryQuery(1)).addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.getLibrary?.toLibraryResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }

        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getShelfDetails(
        name: String,
        token: String
    ): NetworkResponse<ShelfResponse> {
        return try {
            val response = checkResponse(
                apolloClient.query(GetShelfDetailsQuery(name)).addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.getShelf?.toShelfResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun removeBookFromShelf(
        bookId: String,
        shelfId: String,
        token: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(
                    RemoveBookFromShelfMutation(
                        bookId = bookId,
                        shelfId = shelfId
                    )
                ).addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.removeBookFromShelf?.toBaseResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun createShelf(name: String, token: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(CreateShelfMutation(name))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.createShelf?.toBaseResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }

        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun removeShelf(id: String, token: String): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(RemoveShelfMutation(id)).addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.removeShelf?.toBaseResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun search(name: String): NetworkResponse<BooksResponse> {
        return try {
            when (val response = checkResponse(apolloClient.query(SearchQuery(name)).execute())) {
                is Success -> {
                    Success(response.data?.search?.toBooksResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getBookReviews(id: String): NetworkResponse<ReviewsResponse> {
        return try {
            val response = checkResponse(apolloClient.query(GetBookReviewsQuery(id)).execute())

            when (response) {
                is Success -> {
                    Success(response.data?.getBookReviews?.toReviewsResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun makeReview(
        token: String,
        bookId: String,
        rate: Float,
        content: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(
                    ReviewBookMutation(
                        bookId = bookId,
                        rate = rate.toDouble(),
                        content = content
                    )
                ).addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> {
                    Success(response.data?.reviewBook?.toBaseResponse()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getUserInfo(token: String): NetworkResponse<User> {
        return try {
            val response = checkResponse(
                apolloClient.query(GetUserInfoQuery()).addHttpHeader("Authorization", token)
                    .execute()
            )
            when (response) {
                is Success -> {
                    Success(response.data?.userInfo?.toUser()!!)
                }

                else -> {
                    Error(response.message.toString())
                }
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun updateUserName(token: String, name: String): NetworkResponse<User> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(UpdateUserNameMutation(name))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.updateUser?.toUser()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun updateUserBio(token: String, bio: String): NetworkResponse<User> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(UpdateUserBioMutation(bio))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.updateUser?.toUser()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): NetworkResponse<User> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(
                    UpdatePasswordMutation(
                        oldPassword,
                        newPassword
                    )
                ).addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.updateUser?.toUser()!!)
                else -> Error(response.message.toString())
            }

        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun getBookStatus(token: String, bookId: String): NetworkResponse<BookStatus> {
        return try {
            val response = checkResponse(
                apolloClient.query(GetBookStatusQuery(bookId)).addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> Success(response.data?.getBookStatus?.toBookStatus()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            if (ex.message.toString() == "null") Success(BookStatus()) else Error(ex.message.toString())
        }
    }

    override suspend fun getBookContent(id: String): NetworkResponse<BookContent> {
        return try {
            val response = checkResponse(apolloClient.query(GetBookContentQuery(id)).execute())

            when (response) {
                is Success -> Success(response.data?.getBookContent?.toBookContent()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun addBookToShelf(
        token: String,
        shelfId: String,
        bookId: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(AddBookToShelfMutation(shelfId, bookId))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.addBookToShelf?.toBaseResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun createPaymentOrder(
        token: String,
        bookId: String
    ): NetworkResponse<PaymentOrder> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(CreatePaymentOrderMutation(bookId))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.createPaymentOrder?.toPaymentOrder()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun joinCommunity(
        token: String,
        bookId: String
    ): NetworkResponse<BaseResponse> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(JoinCommunityMutation(bookId))
                    .addHttpHeader("Authorization", token).execute()
            )

            when (response) {
                is Success -> Success(response.data?.joinBookCommunity?.toBaseResponse()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }

    override suspend fun completePaymentOrder(
        token: String,
        bookId: String,
        orderId: String
    ): NetworkResponse<PaymentOrder> {
        return try {
            val response = checkResponse(
                apolloClient.mutation(CompletePaymentOrderMutation(bookId, orderId))
                    .addHttpHeader("Authorization", token)
                    .execute()
            )

            when (response) {
                is Success -> Success(response.data?.completePaymentOrder?.toPaymentOrder()!!)
                else -> Error(response.message.toString())
            }
        } catch (ex: Exception) {
            Error(ex.message.toString())
        }
    }
}