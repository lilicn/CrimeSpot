CrimeSpot
===========
Introdunction
--------------
An Android app to provide users the safety information in Gmap and a warning system.

1. Users can see both the general and detail safety information of current location.
2. Users can see the safety information of anywhere by inputting the location.
3. Users can send/get warning message to/from other users nearby.
4. Users can rate the safety for the current location

server
------------------------
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

Pivotaltracker
------------------
https://www.pivotaltracker.com/s/projects/953026
