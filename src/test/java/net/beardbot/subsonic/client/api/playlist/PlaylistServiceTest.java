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
package net.beardbot.subsonic.client.api.playlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.singletonList;
import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
    @Mock
    private PlaylistClient playlistClient;

    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        playlistService = new PlaylistService(playlistClient);
    }

    @Test
    void getPlaylists() {
        var playlistsResponse = playlistsResponse();

        when(playlistClient.getPlaylists()).thenReturn(playlistsResponse);

        var playlists = playlistService.getPlaylists();
        assertThat(playlists).isEqualTo(playlistsResponse.getPlaylists().getPlaylists());
    }

    @Test
    void getPlaylists_withUsername() {
        var playlistsResponse = playlistsResponse();

        when(playlistClient.getPlaylists("testUser")).thenReturn(playlistsResponse);

        var playlists = playlistService.getPlaylists("testUser");
        assertThat(playlists).isEqualTo(playlistsResponse.getPlaylists().getPlaylists());
    }

    @Test
    void getPlaylists_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(playlistClient.getPlaylists()).thenReturn(errorResponse);

        assertSubsonicError(playlistService::getPlaylists, error);
    }

    @Test
    void getPlaylist() {
        var playlistResponse = playlistResponse();

        when(playlistClient.getPlaylist("1")).thenReturn(playlistResponse);

        var playlist = playlistService.getPlaylist("1");
        assertThat(playlist).isEqualTo(playlistResponse.getPlaylist());
    }

    @Test
    void getPlaylist_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(playlistClient.getPlaylist("1")).thenReturn(errorResponse);

        assertSubsonicError(()->playlistService.getPlaylist("1"), error);
    }

    @Test
    void createPlaylist() {
        when(playlistClient.createPlaylist("1", Map.of("songId", Arrays.asList("2", "3")))).thenReturn(subsonicResponse());
        playlistService.createPlaylist("1", "2", "3");

        when(playlistClient.createPlaylist("1", Map.of("songId", Arrays.asList("4", "5")))).thenReturn(subsonicResponse());
        playlistService.createPlaylist("1", Arrays.asList("4","5"));
    }

    @Test
    void createPlaylist_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(playlistClient.createPlaylist("1", Map.of("songId", Arrays.asList("2", "3")))).thenReturn(errorResponse);
        assertSubsonicError(()->playlistService.createPlaylist("1", "2", "3"), error);

        when(playlistClient.createPlaylist("1", Map.of("songId", Arrays.asList("4", "5")))).thenReturn(errorResponse);
        assertSubsonicError(()->playlistService.createPlaylist("1", Arrays.asList("4", "5")), error);
    }

    @Test
    void updatePlaylist() {
        when(playlistClient.updatePlaylist("0", Map.of(
                "name", singletonList("1"),"comment", singletonList("2"), "public", singletonList("true"),
                "songIdToAdd", Arrays.asList("3","4"), "songIndexToRemove", Arrays.asList("5","6")))).thenReturn(subsonicResponse());

        playlistService.updatePlaylist("0", UpdatePlaylistParams.create().addSong("3").addSong("4")
                .name("1").comment("2").makePublic().removeSong(5).removeSong(6));
    }

    @Test
    void updatePlaylist_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(playlistClient.updatePlaylist(anyString(), anyMap())).thenReturn(errorResponse);

        assertSubsonicError(()->playlistService.updatePlaylist("0", UpdatePlaylistParams.create().addSong("3")), error);
    }

    @Test
    void deletePlaylist() {
        when(playlistClient.deletePlaylist("1")).thenReturn(subsonicResponse());
        playlistService.deletePlaylist("1");
    }

    @Test
    void deletePlaylist_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(playlistClient.deletePlaylist("1")).thenReturn(errorResponse);
        assertSubsonicError(()->playlistService.deletePlaylist("1"), error);
    }
}