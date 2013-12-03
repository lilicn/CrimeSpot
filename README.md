CrimeSpot
========
An Android app to provide users the safety information in Gmap.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/android.png)

Users stories
--------------
- It can locate users current location via GPS.
- Users can search location in Gmap.
- Users can see both the general and detail safety information of anywhere.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/currentlocation.png)
- Users can see the recent nearby crimes in Gmap.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/nearbycrimes.png)
- Users can send warning message to users nearby.
- Users can subscribe warning channel for nearby crimes.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/sendWarn.png)
- Users can review others' reviews.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/sendReview.png)
- Users can see the safety analysis (heapmap, crime trend, crime type, etc).

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/heapmap.png)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/historyanalysis.png)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/crimetype.png)

Tools / framework used
------------------------
- Google App Engine Server (Receive/send request from/to Android client)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/gae.png)

- Jenkins server (Collect crime data from offical website periodically and integration test)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/jenkins.png)

- HTML that interacts with native code via Javascript (Android webView)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/dhtml.png)

- Android test framework

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/androidtest.png)

- Pubnub

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/pubnub.png)

- Selenium

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/se.png)

Tests
------
- Junit test
- Integration test
- Automation test

Pivotaltracker
------------------
https://www.pivotaltracker.com/s/projects/953026
