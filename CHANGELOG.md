# Changelog

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

- Fixed incompatibility with time-zoned date time strings in created/starred fields

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