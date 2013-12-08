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
- Users can send review and rating star to server.
- Users can review others' reviews.

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/sendReview.png)
- Users can see the safety analysis (heapmap, crime trend, crime type, etc).
=======
===========
Introdunction
--------------
An Android app to provide users the safety information in Gmap and a warning system.

1. Users can see both the general and detail safety information of current location.
2. Users can see the safety information of anywhere by inputting the location.
3. Users can send/get warning message to/from other users nearby.
4. Users can rate the safety for the current location
>>>>>>> origin/master

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/heapmap.png)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/historyanalysis.png)

![Image Alt](https://github.com/skyw932/CrimeSpot/raw/dev/demo/crimetype.png)

Tools / framework used
------------------------
<<<<<<< HEAD
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
=======
- Google App Engine, AWS RDS, Jenkins server ...
- Receive request from clients and send response to clients (fast and accurate)
- Collect crime data from official website periodically (vast coverage and non-repetitive)
- ...

Client
---------
- Android App, PubNub ...
- Show current place in Gmap
- Show safety information in general/details
- Send/Get warning message to/from nearby users
- Rate the safety for the current location
- ...

Data collection and processing
--------------------------------
- http://www.crimemapping.com/map.aspx
- http://spotcrime.com/
- Where and how to collect the crime data?
- What's the basis of the safety score?
- ...
>>>>>>> origin/master

Pivotaltracker
------------------
https://www.pivotaltracker.com/s/projects/953026
