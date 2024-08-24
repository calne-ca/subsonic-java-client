# Subsonic Java Client
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.beardbot/subsonic-client/badge.svg)](https://search.maven.org/artifact/net.beardbot/subsonic-client) [![Build](https://github.com/calne-ca/subsonic-java-client/workflows/Build/badge.svg)](https://github.com/calne-ca/subsonic-java-client/actions?query=workflow%3ABuild) [![Airsonic Integration](https://github.com/calne-ca/subsonic-java-client/workflows/Airsonic%20Integration/badge.svg)](https://github.com/calne-ca/subsonic-java-client/actions?query=workflow%3A%22Airsonic+Integration%22) [![Subsonic Integration](https://github.com/calne-ca/subsonic-java-client/workflows/Subsonic%20Integration/badge.svg)](https://github.com/calne-ca/subsonic-java-client/actions?query=workflow%3A%22Subsonic+Integration%22) [![codecov](https://codecov.io/gh/calne-ca/subsonic-java-client/branch/master/graph/badge.svg?token=7TSQV4HGAF)](https://codecov.io/gh/calne-ca/subsonic-java-client)

A Java Client for the [Subsonic API](http://www.subsonic.org/pages/api.jsp).

```xml
<dependency>
    <groupId>net.beardbot</groupId>
    <artifactId>subsonic-client</artifactId>
    <version>0.5.1</version>
</dependency>
```

## Compatibility
The client is based on the Subsonic API version 1.15.0.
The used API version will automatically be adapted to match the server's API version if the server uses an older API version than 1.15.0.
In this case only operations supported by the server's API version will work.
A minimum server API version of 1.13.0 is still required.

The client is automatically being tested against the latest [airsonic](https://airsonic.github.io/) and [subsonic](http://www.subsonic.org/) servers during its build process.
It should work with all servers that implement the Subsonic API.

## Currently Supported Operations
Here's an overview of which operations this client currently supports.
Note that depending on the implementation some servers may not support specific operations even it should be supported by the API version of the server.
For example airsonic does not support any chat operations while subsonic does.

| API                    | Operation                  | Supported Since |
|------------------------|----------------------------|-----------------|
| System                 | **ping**                   | **0.1.0**       |
|                        | **getLicense**             | **0.1.0**       |
| Browsing               | **getMusicFolders**        | **0.1.0**       |
|                        | **getIndexes**             | **0.1.0**       |
|                        | **getMusicDirectory**      | **0.1.0**       |
|                        | **getGenres**              | **0.1.0**       |
|                        | **getArtists**             | **0.1.0**       |
|                        | **getArtist**              | **0.1.0**       |
|                        | **getAlbum**               | **0.1.0**       |
|                        | **getSong**                | **0.1.0**       |
|                        | getVideos                  | -               |
|                        | getVideoInfo               | -               |
|                        | **getArtistInfo**          | **0.5.0**       |
|                        | **getArtistInfo2**         | **0.5.0**       |
|                        | **getAlbumInfo**           | **0.5.0**       |
|                        | **getAlbumInfo2**          | **0.5.0**       |
|                        | getSimilarSongs            | -               |
|                        | getSimilarSongs2           | -               |
|                        | getTopSongs                | -               |
| Album/song lists       | **getAlbumList**           | **0.5.0**       |
|                        | **getAlbumList2**          | **0.5.0**       |
|                        | getRandomSongs             | -               |
|                        | getSongsByGenre            | -               |
|                        | **getNowPlaying**          | **0.1.0**       |
|                        | **getStarred**             | **0.4.0**       |
|                        | **getStarred2**            | **0.4.0**       |
| Searching              | search                     | -               |
|                        | **search2**                | **0.1.0**       |
|                        | **search3**                | **0.1.0**       |
| Playlists              | **getPlaylists**           | **0.1.0**       |
|                        | **getPlaylist**            | **0.1.0**       |
|                        | **createPlaylist**         | **0.1.0**       |
|                        | **updatePlaylist**         | **0.1.0**       |
|                        | **deletePlaylist**         | **0.1.0**       |
| Media retrieval        | **stream**                 | **0.1.0**       |
|                        | **download**               | **0.1.0**       |
|                        | hls                        | -               |
|                        | getCaptions                | -               |
|                        | **getCoverArt**            | **0.1.0**       |
|                        | getLyrics                  | -               |
|                        | **getAvatar**              | **0.1.0**       |
| Media annotation       | **star**                   | **0.4.0**       |
|                        | **unstar**                 | **0.4.0**       |
|                        | **setRating**              | **0.5.0**       |
|                        | **scrobble**               | **0.1.0**       |
| Sharing                | getShares                  | -               |
|                        | createShare                | -               |
|                        | updateShare                | -               |
|                        | deleteShare                | -               |
| Podcasts               | getPodcasts                | -               |
|                        | getNewestPodcasts          | -               |
|                        | refreshPodcasts            | -               |
|                        | createPodcastChannel       | -               |
|                        | deletePodcastChannel       | -               |
|                        | deletePodcastEpisode       | -               |
|                        | downloadPodcastEpisode     | -               |
| Jukebox                | jukeboxControl             | -               |
| Internet radio         | getInternetRadioStations   | -               |
|                        | createInternetRadioStation | -               |
|                        | createInternetRadioStation | -               |
|                        | updateInternetRadioStation | -               |
|                        | deleteInternetRadioStation | -               |
| Chat                   | getChatMessages            | -               |
|                        | addChatMessage             | -               |
| User management        | **getUser**                | **0.1.0**       |
|                        | **getUsers**               | **0.1.0**       |
|                        | **createUser**             | **0.1.0**       |
|                        | **updateUser**             | **0.1.0**       |
|                        | **deleteUser**             | **0.1.0**       |
|                        | **changePassword**         | **0.1.0**       |
| Bookmarks              | getBookmarks               | -               |
|                        | createBookmark             | -               |
|                        | deleteBookmark             | -               |
|                        | getPlayQueue               | -               |
|                        | savePlayQueue              | -               |
| Media library scanning | **getScanStatus**          | **0.1.0**       |
|                        | **startScan**              | **0.1.0**       |

## Usage
### Initialization
```java
SubsonicPreferences preferences = new SubsonicPreferences("http://localhost:13013/airsonic", "username", "password");
preferences.setStreamBitRate(192);
preferences.setClientName("MySubsonicClient");

Subsonic subsonic = new Subsonic(preferences);
```

### Test connection
```java
try {
    if(subsonic.testConnection()){
        log.info("Succesfully connected to server!")
    } else {
        log.error("Failed to connect to server!")
    }   
} catch(SubsonicIncompatibilityException e) {
    log.error("The server is not compatible with the client! Please upgrade you server!")
}
```

### Using the API
Every Subsonic API group has a corresponding method in the Subsonic class.
For example to use the search3 operation from the *Searching* API group you do the following:

```java
SearchResult3 searchResult = subsonic.searching().search3("Caravan Palace",
    SearchParams.create().artistCount(0).albumCount(0).songCount(3));
```

Some API operations have optional parameters.
Their values will, in some cases, be directly passed as arguments but usually the method expects a param class.
These param classes always have a static *create* method and multiple additional methods representing the parameters.

#### More examples
```java
// Scan library and wait until finished
subsonic.libraryScan().startScan();

while (subsonic.libraryScan().getScanStatus().isScanning()){
    Thread.sleep(1000);
}
```

```java
// Fetch index and print it
List<Index> indexes = subsonic.browsing().getIndexes();

for (Index index : indexes) {
    System.out.println(index.getName());
    for (Artist artist : index.getArtists()) {
        System.out.println("-- " + artist.getName());
    }
}
```

```java
// Stream first song from index
String firstArtistDirectoryId = indexes.get(0).getArtists().get(0).getId();

Directory directory = subsonic.browsing().getMusicDirectory(firstArtistDirectoryId);
Child child = directory.getchildren().get(0);

while (child.isDir()){
    directory = subsonic.browsing().getMusicDirectory(child.getId());
    child = directory.getchildren().get(0);
}

InputStream stream = subsonic.media().stream(child.getId());
someAudioPlayer.play(stream);
```

```java
// Create a new user with stream and download roles
subsonic.users().createUser("testUser", "password", "test@example.com", 
    CreateUserParams.create().roles(UserRole.STREAM,UserRole.DOWNLOAD));
```
Every other operation supported by this client can be used in a similar way.
