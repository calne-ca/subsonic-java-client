/**
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
package net.beardbot.subsonic.client.integration.base;

import lombok.SneakyThrows;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.SubsonicPreferences;
import net.beardbot.subsonic.client.api.playlist.UpdatePlaylistParams;
import net.beardbot.subsonic.client.api.search.SearchParams;
import net.beardbot.subsonic.client.api.usermanagement.CreateUserParams;
import net.beardbot.subsonic.client.api.usermanagement.UpdateUserParams;
import net.beardbot.subsonic.client.api.usermanagement.UserRole;
import net.beardbot.subsonic.client.base.SubsonicException;
import org.subsonic.restapi.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.awaitility.Awaitility.await;

public class CommonIntegrationTestCode {
    public static void ping(SubsonicBaseContainer container){
        subsonic(container, "admin","admin").system().ping();
    }

    public static void getLicense(SubsonicBaseContainer container){
        var subsonic = subsonic(container, "admin","admin");
        var license = subsonic.system().getLicense();

        assertThat(license.getTrialExpires()).isNotNull();

        if (license.isValid()){
            assertThat(license.getLicenseExpires()).isNotNull();
            assertThat(license.getEmail()).isNotNull();
        } else {
            assertThat(license.getLicenseExpires()).isNull();
            assertThat(license.getEmail()).isNull();
        }
    }

    public static void browsing(SubsonicBaseContainer container){
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/test");
        container.addMusicFolder("/misc");
        scanLibrary(subsonic);

        // Get Indexes
        var indexes = subsonic.browsing().getIndexes();
        assertThat(indexes).hasSize(2);

        // Get Index
        var index = indexes.get(1);
        assertThat(index.getName()).isEqualTo("T");

        var artistDirectory = index.getArtists().get(0);
        var musicDirectory = subsonic.browsing().getMusicDirectory(artistDirectory.getId());
        var songs = musicDirectory.getchildren();

        var song2 = songs.get(0);
        assertThat(song2.getPath()).endsWith("2-seconds-of-silence.mp3");

        // Get Artist Indexes
        var artistIndexes = subsonic.browsing().getArtists();
        assertThat(artistIndexes).hasSize(2);

        var firstPartialArtist = artistIndexes.get(0).getArtists().get(0);
        assertThat(firstPartialArtist.getName()).isEqualTo("Misc Artist");

        var secondPartialArtist = artistIndexes.get(1).getArtists().get(0);
        assertThat(secondPartialArtist.getName()).isEqualTo("Test Artist");

        // Get Artist
        var firstArtist = subsonic.browsing().getArtist(firstPartialArtist.getId());
        var firstArtistPartialAlbum = firstArtist.getAlbums().get(0);
        assertThat(firstArtistPartialAlbum.getSongCount()).isEqualTo(2);
        var firstArtistAlbum = subsonic.browsing().getAlbum(firstArtistPartialAlbum.getId());

        assertThat(firstArtistAlbum.getSongs()).anyMatch(s->s.getTitle().equals("Misc Title 1"));

        // Get Genres
        assertThat(subsonic.browsing().getGenres().get(0).getName()).isEqualTo("Silence");
    }

    public static void search(SubsonicBaseContainer container){
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/misc");
        container.addMusicFolder("/test");
        scanLibrary(subsonic);

        var search2Result = subsonic.searching().search2("5 seconds", SearchParams.create().artistCount(0).albumCount(0));
        assertThat(search2Result.getSongs()).hasSize(1);
        var song5 = search2Result.getSongs().get(0);
        assertThat(song5.getPath()).isEqualTo("misc/5-seconds-of-silence.mp3");
        assertThat(song5.getAlbum()).isEqualTo("Misc Album");
        assertThat(song5.getArtist()).isEqualTo("Misc Artist");
        assertThat(song5.getTitle()).endsWith("Misc Title 5");

        var search3Result = subsonic.searching().search3("Test Title 2", SearchParams.create().artistCount(0).albumCount(0));
        var song2 = search3Result.getSongs().get(0);
        assertThat(song2.getPath()).isEqualTo("test/2-seconds-of-silence.mp3");
        assertThat(song2.getAlbum()).isEqualTo("Test Album");
        assertThat(song2.getArtist()).isEqualTo("Test Artist");
        assertThat(song2.getTitle()).endsWith("Test Title 2");
    }

    public static void playlist(SubsonicBaseContainer container){
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/misc");
        container.addMusicFolder("/test");
        scanLibrary(subsonic);

        var songs = subsonic.searching().search2("test misc").getSongs();
        assertThat(songs).hasSize(3);

        // Get Playlists
        assertThat(subsonic.playlists().getPlaylists()).hasSize(0);

        // Create Playlist
        var songId5Seconds = songs.stream().filter(s -> s.getTitle().equals("Misc Title 5")).findFirst().get().getId();
        var songIds = songs.stream().filter(s->!s.getId().equals(songId5Seconds)).map(Child::getId).collect(Collectors.toList());
        subsonic.playlists().createPlaylist("test1",songIds);

        var playlists = subsonic.playlists().getPlaylists();
        assertThat(playlists).hasSize(1);

        // Get Playlist
        var playlist = playlists.get(0);
        assertThat(playlist.getName()).isEqualTo("test1");
        assertThat(playlist.getSongCount()).isEqualTo(2);

        var playlistWithSongs = subsonic.playlists().getPlaylist(playlist.getId());
        assertThat(playlistWithSongs.getEntries()).hasSize(2);

        var playlistSongIds = playlistWithSongs.getEntries().stream().map(Child::getId).collect(Collectors.toList());
        assertThat(playlistSongIds).containsExactlyElementsOf(songIds);

        // Update Playlist
        subsonic.playlists().updatePlaylist(playlist.getId(), UpdatePlaylistParams.create().addSong(songId5Seconds).removeSong(0));

        playlistWithSongs = subsonic.playlists().getPlaylist(playlist.getId());
        assertThat(playlistWithSongs.getEntries()).hasSize(2);

        var updatedPlaylistSongIds = new ArrayList<>(songIds);
        updatedPlaylistSongIds.add(songId5Seconds);
        updatedPlaylistSongIds.remove(playlistSongIds.get(0));

        playlistSongIds = playlistWithSongs.getEntries().stream().map(Child::getId).collect(Collectors.toList());
        assertThat(playlistSongIds).containsExactlyElementsOf(updatedPlaylistSongIds);

        // Delete Playlist
        subsonic.playlists().deletePlaylist(playlist.getId());
        assertThat(subsonic.playlists().getPlaylists()).isEmpty();
    }

    @SneakyThrows
    public static void songDownloadAndStream(SubsonicBaseContainer container) {
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/test");
        scanLibrary(subsonic);

        byte[] songBytes = CommonIntegrationTestCode.class.getClassLoader().getResourceAsStream("media/test/2-seconds-of-silence.mp3").readAllBytes();

        var song = subsonic.searching().search3("2 seconds").getSongs().get(0);

        byte[] downloadedSongBytes = subsonic.media().download(song.getId()).readAllBytes();
        assertThat(downloadedSongBytes).isNotEmpty();
        assertThat(downloadedSongBytes).containsExactly(songBytes);

        assertThat(subsonic.lists().getNowPlaying()).isEmpty();

        byte[] streamedSongBytes = subsonic.media().stream(song.getId()).readAllBytes();
        assertThat(streamedSongBytes).isNotEmpty();
        assertThat(streamedSongBytes).containsExactly(songBytes);

        var nowPlaying = subsonic.lists().getNowPlaying();
        assertThat(nowPlaying).hasSize(1);

        var nowPlayingEntry = nowPlaying.get(0);
        assertThat(nowPlayingEntry.getId()).isEqualTo(song.getId());
        assertThat(nowPlayingEntry.getTitle()).isEqualTo(song.getTitle());
        assertThat(nowPlayingEntry.getAlbum()).isEqualTo(song.getAlbum());
    }

    @SneakyThrows
    public static void imageDownload(SubsonicBaseContainer container) {
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/test");
        scanLibrary(subsonic);

        byte[] coverArtBytes = CommonIntegrationTestCode.class.getClassLoader().getResourceAsStream("media/test/cover.jpg").readAllBytes();

        var song = subsonic.searching().search3("2 seconds").getSongs().get(0);

        byte[] downloadCoverArBytes = subsonic.media().getCoverArt(song.getCoverArtId()).readAllBytes();
        assertThat(downloadCoverArBytes).isNotEmpty();
        assertThat(downloadCoverArBytes).containsExactly(coverArtBytes);
    }

    @SneakyThrows
    public static void annotation(SubsonicBaseContainer container) {
        var subsonic = subsonic(container, "admin", "admin");

        container.addMusicFolder("/test");
        scanLibrary(subsonic);

        var song = subsonic.searching().search3("2 seconds").getSongs().get(0);
        subsonic.annotation().scrobble(song.getId(), System.currentTimeMillis());
    }

    public static void userFlow(SubsonicBaseContainer container, boolean supportsVideo) {
        var subsonicAdmin = subsonic(container,"admin", "admin");

        // Change admin user password
        subsonicAdmin.users().changePassword("admin","test");
        subsonicAdmin.getPreferences().setPassword("test");

        // Create new user
        subsonicAdmin.users().createUser("testUser", "testPassword", "test@test.com",
                CreateUserParams.create().roles(UserRole.allExcept(UserRole.ADMIN)));

        // Fetch newly created user
        var testUser = subsonicAdmin.users().getUser("testUser");
        assertThat(testUser.getUsername()).isEqualTo("testUser");
        assertThat(testUser.getEmail()).isEqualTo("test@test.com");
        assertThat(testUser.isAdminRole()).isFalse();
        assertThat(testUser.isVideoConversionRole()).isEqualTo(supportsVideo); // AirSonic does not support videos and defaults to false for this role
        assertThat(testUser.isStreamRole()).isTrue();
        assertThat(testUser.isShareRole()).isTrue();
        assertThat(testUser.isSettingsRole()).isTrue();
        assertThat(testUser.isCommentRole()).isTrue();
        assertThat(testUser.isPodcastRole()).isTrue();
        assertThat(testUser.isCoverArtRole()).isTrue();
        assertThat(testUser.isDownloadRole()).isTrue();
        assertThat(testUser.isUploadRole()).isTrue();
        assertThat(testUser.isJukeboxRole()).isTrue();

        // Update user
        subsonicAdmin.users().updateUser("testUser", UpdateUserParams.create().email("changed@email.com"));
        testUser = subsonicAdmin.users().getUser("testUser");
        assertThat(testUser.getEmail()).isEqualTo("changed@email.com");

        // Fetch all users
        var users = subsonicAdmin.users().getAllUsers();
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getUsername()).isEqualTo("admin");
        assertThat(users.get(1).getUsername()).isEqualTo("testUser");

        // Delete user
        subsonicAdmin.users().deleteUser("testUser");
        assertThatCode(()->subsonicAdmin.users().getUser("testUser"))
                .isExactlyInstanceOf(SubsonicException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DATA_NOT_FOUND);
    }

    private static void scanLibrary(Subsonic subsonic){
        assertThat(subsonic.libraryScan().startScan().isScanning()).isTrue();
        await().atMost(Duration.ofSeconds(5)).until(()-> !subsonic.libraryScan().getScanStatus().isScanning());
    }

    private static Subsonic subsonic(SubsonicBaseContainer container, String user, String password){
        SubsonicPreferences preferences = new SubsonicPreferences(container.baseUrl(), user,password);
        return new Subsonic(preferences);
    }
}
