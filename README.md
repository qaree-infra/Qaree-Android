# Qaree - E-Book Reading App

Welcome to Qaree, the ultimate e-book reading app that enhances your reading experience with a variety of powerful features and a sleek, user-friendly interface. This README file will guide you through the app's features and architecture.

## Features


## Screenshots

Here are some screenshots from the Qaree app:

### Light Mode
<table>
  <tr>
    <td><img src="./images/home_screen.png" alt="Home Screen"></td>
   <td> <img src="./images/book_details.png" alt="Book Details"></td>
 <td>  <img src="./images/reading_view.png" alt="Reading View"></td>
<td>  <img src="./images/library.png" alt="Library Feature"></td>
 <td>  <img src="./images/inbox.png" alt="Chat Feature"></td>
 <td>   <img src="./images/chat.png" alt="Chat Feature"> </td>
  </tr>
</table>

### Dark Mode
<table>
  <tr>
    <td><img src="./images/home_screen.png" alt="Home Screen"></td>
   <td> <img src="./images/book_details.png" alt="Book Details"></td>
 <td>  <img src="./images/reading_view.png" alt="Reading View"></td>
<td>  <img src="./images/library.png" alt="Library Feature"></td>
 <td>  <img src="./images/inbox.png" alt="Chat Feature"></td>
 <td>   <img src="./images/chat.png" alt="Chat Feature"> </td>
  </tr>
</table>

## About MVVM arch
![MVVM](https://camo.githubusercontent.com/a0c965a9357f0704a1f5219cfec01510dd1014adba29f88f873e2d937c70336a/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f746f7069632f6c69627261726965732f6172636869746563747572652f696d616765732f66696e616c2d6172636869746563747572652e706e67)

### MVVM (Model-View-ViewModel)

- **Separation of Concerns**: Divides the application into three main components, promoting a clear separation of concerns.
- **Testability**: Facilitates unit testing of business logic.
- **Two-Way Data Binding**: Simplifies the synchronization between the UI and the underlying data.
- **Flexibility**: Adapts well to changing requirements and complex UI interactions.


## Libraries & Tools Used
- [Clean Architecture](https://developer.android.com/topic/architecture): A design principle that separates the app into layers to promote a scalable and maintainable codebase.
- [Multi-Module](https://developer.android.com/topic/modularization/patterns): A project structure that modularizes the app into separate modules to improve build times and maintainability.
- [View Binding](https://developer.android.com/topic/libraries/view-binding): A feature that allows you to more easily write code that interacts with views.
- [Navigation](https://developer.android.com/guide/navigation): A component that helps in implementing navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Part of Android Architecture Components, it is designed to store and manage UI-related data in a lifecycle conscious way.
- [Repository](https://developer.android.com/topic/architecture#fetch-data): A design pattern that mediates between different data sources, such as persistent models, web services, and caches.
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html): A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client for Android and Java.
- [GraphQL](https://graphql.org/): A data query and manipulation language for APIs, and a runtime for executing those queries with your existing data.
- [Shimmer Animation](https://facebook.github.io/shimmer-android/): An Android library that provides an easy way to add a shimmer effect to any view in your Android app.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore): A data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Dagger Hilt](https://dagger.dev/hilt/): A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [Coil](https://coil-kt.github.io/coil/): An image loading library for Android backed by Kotlin Coroutines.
- [FCM (Firebase Cloud Messaging)](https://firebase.google.com/docs/cloud-messaging): A cross-platform messaging solution that lets you reliably send messages at no cost.
- [Payment Integration(Paypal)](https://developer.paypal.com/docs/business/checkout/): A feature that allows the app to accept payments via PayPal.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): A library that helps you load and display pages of data from larger datasets from local storage or over network in a RecyclerView.
- [Socket-IO](https://socket.io/): A library for real-time web applications. It enables real-time, bidirectional and event-based communication between the browser and the server.

Each of these tools and libraries contributes to the functionality, performance, and user experience of the Qaree app. They are chosen based on their efficiency, ease of use, and community support. Happy reading with Qaree! 😊

## Project Structure

Qaree is structured as a multi-module project for better separation of concerns and scalability. The main modules include:

- `app`: Manages UI components and user interactions.
- `data`: Handles data management and repository implementations.
- `domain`: Contains use cases and business logic.

Happy reading with Qaree!
