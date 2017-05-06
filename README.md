# SongRecommender Web application
```diff
- DISCLAIMER: THIS IS AN EDUCATIONAL PROJECT. SPOTIFY.COM DATA IS USED FOR EDUCATIONAL PURPOSES ONLY.
```
Recommendation systems play a key role in our everyday lives, and when it comes to music they have a goal to come up with new songs that a user would like to hear. Since the users are collecting huge amounts of digital music on their devices, finding new music is a problem bigger than ever. 
A good recommendation system should try to minimize users' efforts when it comes to choosing music, but at the same time it should fulfill their expectations. 
This Webapp is a Music Recommendation System which compares songs in terms of audio features and tries to find the most similar ones - instead of looking into user's playlist history to determine a recommendation, this app looks for the similarity in tempo, energy, instruments and so on. 

## Overview

How the recommendation works?
1. User enters a name of a song.
2. Application looks it up on Spotify.com
3. Application compares the song with other songs in the local database
4. Application returns "most similar" song from local database along with the preview link.

## The Big Picture 

This app is a central part of the SongRecommender system and it uses few other "supporting" applications which can also be found on GitHub.com:
1. Application that groups songs by audio feature similarity, it uses KMeans algorithm for clustering   https://github.com/Igor-Jovic/SongRecommenderClustering
2. Clojure library that implements machine learning algorithms for clustering, classification and recommendation https://github.com/Igor-Jovic/SongRecommender-Clojure
3. Since the app is not available on public network, there is an application that tweets private dynamic IP address to twitter account https://github.com/Igor-Jovic/SongPickerTwitterUpdater
4. Spotify proxy app for fetching the song info from Spotify.com https://github.com/SlavkoKomarica/SongRecommender-SpotifyProxy

## Starting up your own Song Recommendation server

To start your own Song Recommender server:
1. Clone, build and run Spotify Proxy Project.
2. Clone this project
3. Clone and build Clojure library for machine learning, put it in root directory of this project.
4. Build this project
5. Download the following .csv files: Min.csv, Max.csv, clusters (whole directory), Centroids.csv from https://drive.google.com/open?id=0B3MZlBNxyLNuQzd0bFIwbzN6LXM. 
6. Put the csv files into data folder and move them to the directory where this project's jar is. 
7. Run jar file from command line and go to http://localhost:8080 

```
Before running the jar file, project structure should look like this:
-SOMEDIRECTORY
  -songrecommender.jar
  -data
    -clusters
      -cluster1
      -cluster2
      -...
    -Min.csv
    -Max.csv
    -Centroids.csv
 ```
