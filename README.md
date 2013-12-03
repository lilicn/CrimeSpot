CrimeSpot
========
An Android app to provide users the safety information in Gmap.

Users stories
--------------
1. It can locate users current location via GPS.
2. Users can search location in Gmap.
3. Users can see both the general and detail safety information of anywhere.
4. Users can see the recent nearby crimes in Gmap.
5. Users can send warning message to users nearby.
6. Users can subscribe warning channel for nearby crimes.
7. Users can send both text review and rate star for the specific zone.
8. Users can review others' reviews.
9. Users can see the safety analysis (heapmap, crime trend, crime type, etc).

Tools / framework used
------------------------
- Google App Engine Server (Receive/send request from/to Android client)
- Jenkins server (Collect crime data from offical website periodically and integration test)
- HTML5 that interacts with native code via Javascript (Android webView)
- Android test framework
- Pubnub
- Selenium

Tests
------
- Junit test
- Integration test
- Automation test

Pivotaltracker
------------------
https://www.pivotaltracker.com/s/projects/953026
