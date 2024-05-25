package com.muhmmad.data.utils

import com.muhmmad.domain.model.ActivityResponse
import com.muhmmad.domain.model.AuthorsResponse
import com.muhmmad.domain.model.LoginResponse
import com.muhmmad.domain.model.ValidatePasswordOTPResponse
import com.muhmmad.domain.model.BaseResponse
import com.muhmmad.domain.model.Book
import com.muhmmad.domain.model.BookContent
import com.muhmmad.domain.model.BookStatus
import com.muhmmad.domain.model.BooksResponse
import com.muhmmad.domain.model.CategoriesResponse
import com.muhmmad.domain.model.Category
import com.muhmmad.domain.model.CommunityMembers
import com.muhmmad.domain.model.ContentItem
import com.muhmmad.domain.model.Cover
import com.muhmmad.domain.model.LibraryResponse
import com.muhmmad.domain.model.Notification
import com.muhmmad.domain.model.NotificationData
import com.muhmmad.domain.model.NotificationsResponse
import com.muhmmad.domain.model.Offer
import com.muhmmad.domain.model.OffersResponse
import com.muhmmad.domain.model.PaymentOrder
import com.muhmmad.domain.model.Review
import com.muhmmad.domain.model.ReviewsResponse
import com.muhmmad.domain.model.Shelf
import com.muhmmad.domain.model.ShelfResponse
import com.muhmmad.domain.model.User
import com.muhmmad.qaree.AddBookToShelfMutation
import com.muhmmad.qaree.CompletePaymentOrderMutation
import com.muhmmad.qaree.CreatePaymentOrderMutation
import com.muhmmad.qaree.CreateShelfMutation
import com.muhmmad.qaree.FollowUserMutation
import com.muhmmad.qaree.ForgetPasswordMutation
import com.muhmmad.qaree.GetAuthorInfoQuery
import com.muhmmad.qaree.GetBestSellerBooksQuery
import com.muhmmad.qaree.GetBookContentQuery
import com.muhmmad.qaree.GetBookReviewsQuery
import com.muhmmad.qaree.GetBookStatusQuery
import com.muhmmad.qaree.GetBooksQuery
import com.muhmmad.qaree.GetCategoriesQuery
import com.muhmmad.qaree.GetCommunityMembersQuery
import com.muhmmad.qaree.GetLastActivityQuery
import com.muhmmad.qaree.GetLibraryQuery
import com.muhmmad.qaree.GetNotificationsQuery
import com.muhmmad.qaree.GetOffersQuery
import com.muhmmad.qaree.GetShelfDetailsQuery
import com.muhmmad.qaree.GetTopAuthorsQuery
import com.muhmmad.qaree.GetUserInfoQuery
import com.muhmmad.qaree.JoinCommunityMutation
import com.muhmmad.qaree.LoginWithGoogleMutation
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

fun LoginWithGoogleMutation.GoogleLogin.toLoginResponse(): LoginResponse = LoginResponse(
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
                avgRating = it?.book?.avgRate?.toInt() ?: 0,
                description = it?.book?.description ?: "",
                price = it?.book?.price ?: 0.0,
                name = it?.book?.name ?: "",
                author = User(
                    name = it?.book?.author?.name ?: "",
                    _id = it?.book?.author?._id ?: "",
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
    readingProgress = readingProgress ?: 0.0
)

fun GetTopAuthorsQuery.GetTopAuthors.toAuthorsResponse(): AuthorsResponse =
    AuthorsResponse(authors?.map {
        User(
            _id = it?._id ?: "",
            name = it?.name ?: "",
            avatar = Cover(path = it?.avatar?.path ?: "")
        )
    }!!)

fun GetBooksQuery.Data.toBooksResponse(): BooksResponse =
    BooksResponse(
        getBooks?.books?.map {
            Book(
                avgRating = it?.avgRate?.toInt() ?: 0,
                id = it?._id ?: "",
                name = it?.name ?: "",
                cover = Cover(path = it?.cover?.path ?: ""),
                author = User(_id = it?.author?._id ?: "", name = it?.author?.name ?: ""),
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
                createdAt = it?.createdAt ?: "",
                price = it?.price ?: 0.0
            )
        }!!
    )

fun GetBestSellerBooksQuery.Data.toBookResponse(): BooksResponse = BooksResponse(
    getBestSellerBooks?.books?.map {
        Book(
            price = it?.price ?: 0.0,
            avgRating = it?.avgRate?.toInt() ?: 0,
            id = it?._id ?: "",
            name = it?.name ?: "",
            cover = Cover(path = it?.cover?.path ?: ""),
            author = User(
                _id = it?.author?._id ?: "", name = it?.author?.name ?: ""
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
                        _id = it?.book?.author?._id ?: "",
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
            avgRating = it?.book?.avgRate?.toInt() ?: 0,
            author = User(
                _id = it?.book?.author?._id ?: "", name = it?.book?.author?.name ?: ""
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
            avgRating = it?.avgRate?.toInt() ?: 0,
            author = User(_id = it?.author?._id ?: "", name = it?.author?.name ?: "")
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
            user = User(_id = it?.user?._id ?: "", name = it?.user?.name ?: "", avatar = null),
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
    _id = _id ?: "",
    name = name ?: "",
    avatar = Cover(path = avatar?.path ?: ""),
    email = email ?: "",
    bio = bio ?: ""
)

fun UpdateUserNameMutation.UpdateUser.toUser(): User = User(
    _id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)

fun UpdateUserBioMutation.UpdateUser.toUser(): User = User(
    _id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)

fun UpdatePasswordMutation.UpdateUser.toUser(): User = User(
    _id = _id ?: "",
    name = name ?: "",
    email = email ?: "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: "")
)

fun GetBookStatusQuery.GetBookStatus.toBookStatus(): BookStatus = BookStatus(
    status = status ?: "",
    readingProgress = readingProgress ?: 0.0,
    updatedAt = updatedAt ?: "",
    createdAt = createdAt ?: ""
)

fun GetBookContentQuery.GetBookContent.toBookContent(): BookContent = BookContent(
    allHTML = allHTML?.map {
        ContentItem(
            id = it?.id ?: "",
            mediaType = it?.mediaType ?: "",
            title = it?.title ?: "",
            order = it?.order ?: 0,
            level = it?.level ?: 0
        )
    }!!,
    content = content?.map {
        ContentItem(
            id = it?.id ?: "",
            mediaType = it?.mediaType ?: "",
            title = it?.title ?: "",
            order = it?.order ?: 0,
            level = it?.level ?: 0
        )
    }!!
)

fun AddBookToShelfMutation.AddBookToShelf.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = success ?: false
)

fun CreatePaymentOrderMutation.CreatePaymentOrder.toPaymentOrder(): PaymentOrder = PaymentOrder(
    id = id ?: "",
    status = status ?: ""
)

fun JoinCommunityMutation.JoinBookCommunity.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = success ?: false
)

fun CompletePaymentOrderMutation.CompletePaymentOrder.toPaymentOrder(): PaymentOrder = PaymentOrder(
    id = capturedOrder?.id ?: "",
    status = capturedOrder?.status ?: ""
)

fun GetAuthorInfoQuery.GetAuthorInfo.toUser(): User = User(
    _id = _id ?: "",
    name = name ?: "",
    email = "",
    bio = bio ?: "",
    avatar = Cover(path = avatar?.path ?: ""),
    isFollowed = isFollowed ?: false
)

fun GetCommunityMembersQuery.GetCommunityMembers.toCommunityMembers(): CommunityMembers =
    CommunityMembers(
        totalMembers = totalMembers ?: 0,
        numberOfPages = numberOfPages ?: 0,
        currentPage = currentPage ?: 0,
        members = members?.map {
            User(
                _id = it?._id ?: "",
                name = it?.name ?: "",
                avatar = Cover(path = it?.avatar?.path ?: ""),
                bio = it?.bio ?: "",
                email = it?.email ?: ""
            )
        }!!
    )

fun FollowUserMutation.FollowUser.toBaseResponse(): BaseResponse = BaseResponse(
    message = message ?: "",
    success = success ?: false
)

fun GetNotificationsQuery.GetNotifications.toNotificationsResponse(): NotificationsResponse =
    NotificationsResponse(
        numberOfPages = numberOfPages ?: 0,
        currentPage = currentPage ?: 0,
        notifications = notifications?.map {
            Notification(
                id = it?._id ?: "",
                title = it?.title ?: "",
                body = it?.body ?: "",
                createdAt = it?.createdAt ?: "",
                updatedAt = it?.updatedAt ?: "",
                type = it?.type ?: "",
                data = NotificationData(
                    userId = it?.data?.userId ?: "",
                    bookId = it?.data?.bookId ?: "",
                    reviewId = it?.data?.reviewId ?: "",
                    roomId = it?.data?.roomId ?: ""
                ),
                image = it?.image ?: ""
            )
        }!!
    )