# HeliosStoreFinder
Sample Application to demonstrate MVP pattern with RxJava, Retrofit, & Room (for data cache) using Kotlin. It also has corresponding UNIT TEST for interactor, presenter and model. I used  mockito & junit for unit testing. I implemented travis ci, tool for continuous integration on this project. Below are the complete features of the application:

<ul>
  <li>Retrofit with RxJava -> used to handle network calls using observable</li>
  <li>Room Library -> used to handle database transaction</li>
  <li>SharedPreference -> used to hold last date sync value for data caching</li>
  <li>Fresco (Facebook library) -> used to download images</li>
  <li>Gson (Google Library) -> used to parse json response from the service</li>
  <li>ViewNavigator -> Base Activity Class to handle the navigation between</li>
  <li>Constants class -> used to store all const variables</li>
  <li>Delivering Object/Data to a fragment using Interface Callback</li>
  <li>DividerSpaceItemDecoration -> used to add spaces between items on recyclerview</li>
  <li>GoogleMap implementation on Fragment</li>
  <li>Handle User-Permission-Request</li>
  <li>Dimens.xml -> used to store all view sizes</li>
  <li>colors.xml -> used to store all custom-colors that is needed by the application</li>
  <li>RecyclerView with SwipeRefreshLayout</li>
  <li>Horizontal RecyclerView -> used to display merchant branch locations</li>
  <li>RecyclerView with Headerview -> used to display merchants by section with header</li>
  <li>Implementing multiple viewholder on recyclerView adapter</li>
  <li>ConstraintLayout (Android Support)</li>
  <li>CardView (Android Support)</li>
  <li>SearchView (Android Support)</li>
</ul>

<b>App Screenshots:</b>

<b>Mobile:</b><br />
<img src="https://raw.githubusercontent.com/HeliosSoftwareDeveloper/StoreFinder/master/screenshots/view_list.png" width="30%" /> &nbsp;&nbsp; <img src="https://raw.githubusercontent.com/HeliosSoftwareDeveloper/StoreFinder/master/screenshots/view_details.png" width="30%" /><br /><br />
