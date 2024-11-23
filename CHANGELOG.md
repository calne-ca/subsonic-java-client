# Changelog

## [0.6.0] - 2024-11-23

### Added

- Support for *estimateContentLength* parameter in stream operation
  - Can be set either globally in the *SubsonicPreferences* or passed with a *StreamParams* object to the stream method.
    Params passed directly to a method will override the global preferences though, so be careful when doing so.

### Breaking Changes

- All signatures of the  methods in *media()* have changed:
  - All methods now return an instance of a new class *MediaStream* instead if an InputStream or URL.
  - URL methods have been removed all-together because the logic is now implemented in *MediaStream*.
  - Before:
    ````java
      InputStream stream = subsonic.media().stream(1);
      URL streamUrl = subsonic.media().streamUrl(1);
    ````
  - Now:
    ````java
      MediaStream stream = subsonic.media().stream(1);
  
      InputStream inputStream = stream.getInputStream();
      URL streamUrl = stream.getUrl();
      int contentLength = stream.getContentLength(); // This value changes based on the estimateContentLength param
    ````

## [0.5.1] - 2024-08-24

### Fixed

- Fixed wrong access modifiers for classes:
  - net.beardbot.subsonic.client.api.browsing.ArtistParams
  - net.beardbot.subsonic.client.api.browsing.ArtistInfoParams
  - net.beardbot.subsonic.client.api.lists.AlbumListParams

## [0.5.0] - 2024-08-23

### Added

- New API implementations:
  - Lists
    - getAlbumList
    - getAlbumList2
  - Annotation
    - setRating
  - Browsing
    - getAlbumInfo
    - getAlbumInfo2
    - getArtistInfo
    - getArtistInfo2

## [0.4.0] - 2024-07-27

### Added

- Migration to Java 17
- New API implementations:
  - Lists
    - getStarred
    - getStarred2
  - Annotation
    - star
    - unstar

### Fixed

- Incompatibility with time-zoned date time strings in created/starred fields
- Possible XXE vulnerability in media API operations

## [0.3.0] - 2021-09-26

### Added 

- New method *subsonic.media().getCoverArtUrl(songId)* that returns a URL without reading from it.

## [0.2.0] - 2021-04-08

### Added 

- New method *subsonic.media().streamUrl(songId)* that returns a stream URL without reading from it.

### Fixed 

- Fixed *InputStream* returned by *subsonic.media().stream(songId)* not readable as audio stream.

## [0.1.0] - 2020-11-30

Initial Release