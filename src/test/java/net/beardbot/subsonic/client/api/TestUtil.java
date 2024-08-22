/*
 * Copyright (C) 2020 Joscha Düringer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.beardbot.subsonic.client.api;

import lombok.SneakyThrows;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.SubsonicPreferences;
import net.beardbot.subsonic.client.base.SubsonicException;
import org.subsonic.restapi.Error;
import org.subsonic.restapi.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;

public class TestUtil {

    public static Subsonic subsonic(String uri){
        var preferences = new SubsonicPreferences(uri, "testUser", "testPassword");
        preferences.setClientName("testClient");
        preferences.setStreamBitRate(128);
        preferences.setStreamFormat("mp3");
        return new Subsonic(preferences);
    }

    @SneakyThrows
    public static byte[] toByteArray(InputStream inputStream){
        var byteArrayOutputStream = new ByteArrayOutputStream();
        inputStream.transferTo(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public static void assertSubsonicError(Runnable method, Error error){
        assertThatCode(method::run).isExactlyInstanceOf(SubsonicException.class)
                .hasFieldOrPropertyWithValue("errorCode", error.getCode())
                .hasFieldOrPropertyWithValue("errorMessage", error.getMessage());
    }

    public static SubsonicResponse subsonicResponse(){
        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        return response;
    }

    public static Error subsonicError(){
        var error = new Error();
        error.setCode(ErrorCode.DATA_NOT_FOUND);
        error.setMessage("Not found");
        return error;
    }

    public static SubsonicResponse pingResponse(){
        var pingResponse = new SubsonicResponse();
        pingResponse.setStatus(ResponseStatus.OK);
        pingResponse.setVersion("1.15.0");
        return pingResponse;
    }

    public static SubsonicResponse licenseResponse(){
        var license = new License();
        license.setEmail("email@example.com");
        license.setLicenseExpires(LocalDateTime.now());
        license.setTrialExpires(LocalDateTime.now());

        var licenseResponse = new SubsonicResponse();
        licenseResponse.setLicense(license);
        licenseResponse.setStatus(ResponseStatus.OK);
        licenseResponse.setVersion("1.15.0");

        return licenseResponse;
    }

    public static SubsonicResponse nowPlayingResponse(){
        var nowPlayingEntry = new NowPlayingEntry();
        nowPlayingEntry.setPlayerId(1);
        nowPlayingEntry.setTitle("testTitle");
        nowPlayingEntry.setAlbum("testAlbum");

        var nowPlaying = new NowPlaying();
        nowPlaying.getEntries().add(nowPlayingEntry);

        var nowPlayingResponse = new SubsonicResponse();
        nowPlayingResponse.setNowPlaying(nowPlaying);
        nowPlayingResponse.setStatus(ResponseStatus.OK);
        nowPlayingResponse.setVersion("1.15.0");

        return nowPlayingResponse;
    }

    public static SubsonicResponse starredResponse(){
        var starred = new Starred();
        starred.getAlbums().add(album());
        starred.getArtists().add(artist());
        starred.getSongs().add(song());

        var starredResponse = new SubsonicResponse();
        starredResponse.setStarred(starred);
        starredResponse.setStatus(ResponseStatus.OK);
        starredResponse.setVersion("1.15.0");

        return starredResponse;
    }

    public static SubsonicResponse starred2Response(){
        var starred2 = new Starred2();
        starred2.getAlbums().add(albumID3());
        starred2.getArtists().add(artistID3());
        starred2.getSongs().add(song());

        var starred2Response = new SubsonicResponse();
        starred2Response.setStarred2(starred2);
        starred2Response.setStatus(ResponseStatus.OK);
        starred2Response.setVersion("1.15.0");

        return starred2Response;
    }

    public static SubsonicResponse scanStatusResponse(){
        var response = new SubsonicResponse();
        response.setScanStatus(scanStatus());
        return response;
    }

    public static ScanStatus scanStatus(){
        var scanStatus = new ScanStatus();
        scanStatus.setScanning(true);
        scanStatus.setCount(123L);
        return scanStatus;
    }

    public static SubsonicResponse playlistsResponse(){
        var entry = new Playlist();
        entry.setId("1");
        entry.setName("Test");

        var playlists = new Playlists();
        playlists.getPlaylists().addAll(Collections.singletonList(entry));

        var playlistResponse = new SubsonicResponse();
        playlistResponse.setStatus(ResponseStatus.OK);
        playlistResponse.setVersion("1.15.0");
        playlistResponse.setPlaylists(playlists);

        return playlistResponse;
    }

    public static SubsonicResponse userResponse(){
        var userResponse = new SubsonicResponse();
        userResponse.setUser(user());
        return userResponse;
    }

    public static SubsonicResponse usersResponse(){
        var users = new Users();
        users.getUsers().add(user());

        var userResponse = new SubsonicResponse();
        userResponse.setUsers(users);
        return userResponse;
    }

    public static SubsonicResponse playlistResponse(){
        var playlist = new PlaylistWithSongs();
        playlist.setId("1");
        playlist.setName("Test");
        playlist.getEntries().add(song());

        var playlistResponse = new SubsonicResponse();
        playlistResponse.setStatus(ResponseStatus.OK);
        playlistResponse.setVersion("1.15.0");
        playlistResponse.setPlaylist(playlist);

        return playlistResponse;
    }

    public static SubsonicResponse search2Response(){
        var searchResult = new SearchResult2();
        searchResult.getAlbums().addAll(Collections.singletonList(album()));
        searchResult.getArtists().addAll(Collections.singletonList(artist()));
        searchResult.getSongs().addAll(Collections.singletonList(song()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setSearchResult2(searchResult);

        return response;
    }

    public static SubsonicResponse search3Response(){
        var searchResult = new SearchResult3();
        searchResult.getAlbums().addAll(Collections.singletonList(albumID3()));
        searchResult.getArtists().addAll(Collections.singletonList(artistID3()));
        searchResult.getSongs().addAll(Collections.singletonList(song()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setSearchResult3(searchResult);

        return response;
    }

    public static SubsonicResponse musicFolderResponse(){
        var musicFolder = new MusicFolder();
        musicFolder.setId(1);
        musicFolder.setName("testFolder");

        var musicFolders = new MusicFolders();
        musicFolders.getMusicFolders().addAll(Collections.singletonList(musicFolder));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setMusicFolders(musicFolders);

        return response;
    }

    public static SubsonicResponse musicDirectoryResponse(){
        var musicDirectory = new Directory();
        musicDirectory.setId("1");
        musicDirectory.setName("testDirectory");
        musicDirectory.getchildren().addAll(Collections.singletonList(song()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setDirectory(musicDirectory);

        return response;
    }

    public static SubsonicResponse genresResponse(){
        var genres = new Genres();
        genres.getGenres().addAll(Collections.singletonList(genre()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setGenres(genres);

        return response;
    }

    public static SubsonicResponse artistsResponse(){
        var artists = new ArtistsID3();
        artists.getIndices().addAll(Collections.singletonList(indexID3()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setArtists(artists);

        return response;
    }

    public static SubsonicResponse artistResponse(){
        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setArtist(artistWithAlbumsID3());

        return response;
    }

    public static SubsonicResponse albumResponse(){
        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setAlbum(albumWithSongsID3());

        return response;
    }

    public static SubsonicResponse songResponse(){
        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setSong(song());

        return response;
    }

    public static SubsonicResponse artistInfo2Response(){
        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setArtistInfo2(artistInfo2());

        return response;
    }

    public static SubsonicResponse indexesResponse(){
        var indexes = new Indexes();
        indexes.getIndices().addAll(Collections.singletonList(index()));

        var response = new SubsonicResponse();
        response.setStatus(ResponseStatus.OK);
        response.setVersion("1.15.0");
        response.setIndexes(indexes);

        return response;
    }

    public static User user(){
        var user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setStreamRole(true);
        user.setEmail("test@example.com");
        return user;
    }

    public static Index index(){
        var index = new Index();
        index.setName("testIndex");
        index.getArtists().addAll(Collections.singletonList(artist()));
        return index;
    }

    public static IndexID3 indexID3(){
        var index = new IndexID3();
        index.setName("testIndex");
        index.getArtists().addAll(Collections.singletonList(artistID3()));
        return index;
    }

    public static Child album(){
        var albumDirectory = new Child();
        albumDirectory.setId("1");
        albumDirectory.setAlbum("testAlbum");
        return albumDirectory;
    }

    public static Artist artist(){
        var artistDirectory = new Artist();
        artistDirectory.setId("1");
        artistDirectory.setName("testArtist");
        return artistDirectory;
    }

    public static ArtistID3 artistID3(){
        var partialArtist = new ArtistID3();
        partialArtist.setId("1");
        partialArtist.setName("testArtist");
        partialArtist.setCoverArtId("testCoverId");
        partialArtist.setAlbumCount(2);
        return partialArtist;
    }

    public static ArtistWithAlbumsID3 artistWithAlbumsID3(){
        var artist = new ArtistWithAlbumsID3();
        artist.setId("1");
        artist.setName("testArtist");
        artist.getAlbums().addAll(Collections.singletonList(albumID3()));
        return artist;
    }

    public static AlbumWithSongsID3 albumWithSongsID3(){
        var album = new AlbumWithSongsID3();
        album.setArtist("artist");
        album.setName("testAlbum");
        album.getSongs().addAll(Collections.singletonList(song()));
        return album;
    }

    public static AlbumID3 albumID3(){
        var album = new AlbumID3();
        album.setArtist("artist");
        album.setName("testAlbum");
        return album;
    }

    public static Child song(){
        var song = new Child();
        song.setId("1");
        song.setTitle("testSong");
        return song;
    }

    public static Genre genre(){
        var genre = new Genre();
        genre.setName("testGenre");
        genre.setAlbumCount(1);
        genre.setSongCount(2);
        return genre;
    }

    public static ArtistInfo2 artistInfo2(){
        var artistInfo2 = new ArtistInfo2();
        artistInfo2.setBiography("abc");
        artistInfo2.setSmallImageUrl("http://localhost/small");
        artistInfo2.setMediumImageUrl("http://localhost/medium");
        artistInfo2.setLargeImageUrl("http://localhost/large");
        artistInfo2.setLastFmUrl("http://localhost/lastfm");
        artistInfo2.setMusicBrainzId("brainz");
        artistInfo2.getSimilarArtists().add(artistID3());
        return artistInfo2;
    }
}
