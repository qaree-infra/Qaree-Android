package com.muhmmad.data.utils

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.Category
import com.muhmmad.domain.model.Cover
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.Offer
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.Review
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.model.Shelf
import com.muhmmad.domain.model.ShelfResponse
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.CreateShelfMutation
import com.muhmmad.qaree.ForgetPasswordMutation
import com.muhmmad.qaree.GetBestSellerBooksQuery
import com.muhmmad.qaree.GetBookReviewsQuery
import com.muhmmad.qaree.GetBooksQuery
import com.muhmmad.qaree.GetCategoriesQuery
import com.muhmmad.qaree.GetLastActivityQuery
import com.muhmmad.qaree.GetLibraryQuery
import com.muhmmad.qaree.GetOffersQuery
import com.muhmmad.qaree.GetShelfDetailsQuery
import com.muhmmad.qaree.GetTopAuthorsQuery
import com.muhmmad.qaree.GetUserInfoQuery
import com.muhmmad.qaree.RemoveBookFromShelfMutation
import com.muhmmad.qaree.RemoveShelfMutation
import com.muhmmad.qaree.ResendPasswordOTPMutation
import com.muhmmad.qaree.ResendVerificationOTPMutation
import com.muhmmad.qaree.ResetPasswordMutation
import com.muhmmad.qaree.ReviewBookMutation
import com.muhmmad.qaree.SearchQuery
import com.muhmmad.qaree.SignInMutation
import com.muhmmad.qaree.UpdatePasswordMutation
import com.muhmmad.qaree.UpdateUserBioMutation
import com.muhmmad.qaree.UpdateUserNameMutation
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

fun GetOffersQuery.GetAllOffers.toOffersResponse(): OffersResponse = OffersResponse(
    data = offers?.map {
        Offer(
            percent = it?.percent ?: 0,
            expireAt = it?.expireAt ?: "",
            book = Book(
                createdAt = it?.book?.createdAt ?: "",
                language = it?.book?.language ?: "",
                avgRating = it?.book?.avgRate ?: 0,
                description = it?.book?.description ?: "",
                price = it?.book?.price ?: 0.0,
                name = it?.book?.name ?: "",
                author = User(
                    name = it?.book?.author?.name ?: "",
                    id = it?.book?.author?._id ?: "",
                    avatar = Cover()
                ),
                cover = Cover(
                    path = it?.book?.cover?.path ?: "",
                    size = it?.book?.cover?.size ?: 0.0
                ),
                categories = it?.book?.categories?.map {
                    Category(
                        id = "",
                        nameAr = it?.name_ar ?: "",
                        nameEn = it?.name_en ?: "", image = ""
                    )
                },
                id = it?.book?._id ?: "",
                isbn = ""
            ),
        )
    } ?: listOf(Offer(book = Book()))
)

fun GetLastActivityQuery.GetLastActivity.toActivityResponse(): ActivityResponse = ActivityResponse(
    book = Book(
        id = book?._id ?: "",
        isbn = book?.isbn ?: "",
        price = 0.0,
        name = book?.name ?: "",
        author = null,
        cover = Cover(id = book?.cover?._id ?: "", path = book?.cover?.path ?: "", size = 0.0),
        categories = null,
    ),
    createdAt = createdAt ?: "",
    status = status ?: "",
    updatedAt = updatedAt ?: "",
    readingProgress = readingProgress ?: 0
)

fun GetTopAuthorsQuery.GetTopAuthors.toAuthorsResponse(): AuthorsResponse =
    AuthorsResponse(authors?.map {
        User(
            id = it?._id ?: "",
            name = it?.name ?: "",
            avatar = Cover(path = it?.avatar?.path ?: "")
        )
    }!!)

fun GetBooksQuery.Data.toBooksResponse(): BooksResponse =
    BooksResponse(
        getBooks?.books?.map {
            Book(
                avgRating = it?.avgRate ?: 0,
                id = it?._id ?: "",
                name = it?.name ?: "",
                cover = Cover(path = it?.cover?.path ?: ""),
                author = User(id = it?.author?._id ?: "", name = it?.author?.name ?: ""),
                categories = it?.categories?.map {
                    Category(
                        id = it?._id ?: "",
                        nameAr = it?.name_ar ?: "",
                        nameEn = it?.name_en ?: "",
                        image = ""
                    )
                },
                language = it?.language ?: "",
                description = it?.description ?: "",
                createdAt = it?.createdAt ?: ""
            )
        }!!
    )

fun GetBestSellerBooksQuery.Data.toBookResponse(): BooksResponse = BooksResponse(
    getBestSellerBooks?.books?.map {
        Book(
            avgRating = it?.avgRate ?: 0,
            id = it?._id ?: "",
            name = it?.name ?: "",
            cover = Cover(path = it?.cover?.path ?: ""),
            author = User(
                id = it?.author?._id ?: "", name = it?.author?.name ?: ""
            )
        )
    }!!
)

fun GetCategoriesQuery.GetAllCategories.toCategoriesResponse(): CategoriesResponse =
    CategoriesResponse(categories?.map {
        Category(
            id = it?._id ?: "",
            nameEn = it?.name_en ?: "",
            nameAr = it?.name_ar ?: "",
            image = it?.icon?.path ?: ""
        )
    }!!)

fun GetLibraryQuery.GetLibrary.toLibraryResponse(): LibraryResponse = LibraryResponse(
    data = shelves?.map {
        Shelf(
            id = it?._id ?: "",
            name = it?.name ?: "",
            books = it?.books?.map {
                Book(
                    id = it?.book?._id ?: "",
                    name = it?.book?.name ?: "",
                    author = User(
                        id = it?.book?.author?._id ?: "",
                        name = it?.book?.author?.name ?: ""
                    ),
                    cover = Cover(path = it?.book?.cover?.path ?: ""),
                    readingProgress = it?.readingProgress ?: 0,
                    status = it?.status ?: ""
                )
            }!!
        )
    }!!, total = total ?: 0, currentPage = currentPage ?: 0, numberOfPages = numberOfPages ?: 0
)

fun GetShelfDetailsQuery.GetShelf.toShelfResponse(): ShelfResponse = ShelfResponse(
    id = _id ?: "",
    name = name ?: "",
    books = books?.map {
        Book(
            id = it?.book?._id ?: "",
            cover = Cover(path = it?.book?.cover?.path ?: ""),
            name = it?.book?.name ?: "",
            avgRating = it?.book?.avgRate ?: 0,
            author = User(
                id = it?.book?.author?._id ?: "", name = it?.book?.author?.name ?: ""
            ), readingProgress = it?.readingProgress ?: 0, status = it?.status ?: ""
        )
    }!!,
    total = totalBooks ?: 0,
    currentPage = 0, numberOfPages = numberOfBooksPages ?: 0
)

fun CreateShelfMutation.CreateShelf.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = success ?: false
)

fun RemoveShelfMutation.RemoveShelf.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = false
)

fun SearchQuery.Search.toBooksResponse(): BooksResponse = BooksResponse(
    total = total ?: 0,
    data = books?.map {
        Book(
            id = it?._id ?: "",
            cover = Cover(path = it?.cover?.path ?: ""),
            name = it?.name ?: "",
            avgRating = it?.avgRate ?: 0,
            author = User(id = it?.author?._id ?: "", name = it?.author?.name ?: "")
        )
    }!!
)

fun RemoveBookFromShelfMutation.RemoveBookFromShelf.toBaseResponse(): BaseResponse = BaseResponse(
    success = success ?: false,
    message = message ?: ""
)

fun GetBookReviewsQuery.GetBookReviews.toReviewsResponse(): ReviewsResponse = ReviewsResponse(
    data = reviews?.map {
        Review(
            id = it?._id ?: "",
            content = it?.content ?: "",
            user = User(id = it?.user?._id ?: "", name = it?.user?.name ?: "", avatar = null),
            rate = it?.rate?.toFloat() ?: 0.0F, createdAt = it?.createdAt ?: "",
            updatedAt = it?.updatedAt ?: ""
        )
    }!!, total = total ?: 0, currentPage = currentPage ?: 0, numberOfPages = numberOfPages ?: 0
)

fun ReviewBookMutation.ReviewBook.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = true
)

fun GetUserInfoQuery.UserInfo.toUser(): User = User(
    id = _id ?: "",
    name = name ?: "",
    avatar = Cover(path = avatar?.path ?: ""),
    email = email ?: "",
    bio = bio ?: ""
)

fun UpdateUserNameMutation.UpdateUser.toUser(): User = User(
    id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)

fun UpdateUserBioMutation.UpdateUser.toUser(): User = User(
    id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)

fun UpdatePasswordMutation.UpdateUser.toUser(): User = User(
    id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)