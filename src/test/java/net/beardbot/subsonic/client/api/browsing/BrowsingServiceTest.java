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
package net.beardbot.subsonic.client.api.browsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static net.beardbot.subsonic.client.api.TestUtil.songResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrowsingServiceTest {

    @Mock
    private BrowsingClient browsingClient;

    private BrowsingService browsingService;

    @BeforeEach
    void setUp() {
        browsingService = new BrowsingService(browsingClient);
    }

    @Test
    void getMusicFolders() {
        var musicFolderResponse = musicFolderResponse();

        when(browsingClient.getMusicFolders()).thenReturn(musicFolderResponse);

        var result = browsingService.getMusicFolders();
        assertThat(result).isEqualTo(musicFolderResponse.getMusicFolders().getMusicFolders());
    }

    @Test
    void getMusicFolders_error() {
        var error = subsonicError();
        var musicFolderResponse = musicFolderResponse();
        musicFolderResponse.setError(error);

        when(browsingClient.getMusicFolders()).thenReturn(musicFolderResponse);

        assertSubsonicError(browsingService::getMusicFolders, error);
    }

    @Test
    void getMusicDirectory() {
        var musicDirectoryResponse = musicDirectoryResponse();

        when(browsingClient.getMusicDirectory("1")).thenReturn(musicDirectoryResponse);

        var result = browsingService.getMusicDirectory("1");
        assertThat(result).isEqualTo(musicDirectoryResponse.getDirectory());
    }

    @Test
    void getMusicDirectory_error() {
        var error = subsonicError();
        var musicDirectoryResponse = musicDirectoryResponse();
        musicDirectoryResponse.setError(error);

        when(browsingClient.getMusicDirectory("1")).thenReturn(musicDirectoryResponse);

        assertSubsonicError(()->browsingService.getMusicDirectory("1"), error);
    }

    @Test
    void getGenres() {
        var genresResponse = genresResponse();

        when(browsingClient.getGenres()).thenReturn(genresResponse);

        var result = browsingService.getGenres();
        assertThat(result).isEqualTo(genresResponse.getGenres().getGenres());
    }

    @Test
    void getGenres_error() {
        var error = subsonicError();
        var genresResponse = genresResponse();
        genresResponse.setError(error);

        when(browsingClient.getGenres()).thenReturn(genresResponse);

        assertSubsonicError(browsingService::getGenres, error);
    }

    @Test
    void getArtists_noParams() {
        var artistsResponse = artistsResponse();

        when(browsingClient.getArtists(Collections.emptyMap())).thenReturn(artistsResponse);

        var result = browsingService.getArtists();
        assertThat(result).isEqualTo(artistsResponse.getArtists().getIndices());
    }

    @Test
    void getArtists_withMusicFolderId() {
        var artistsResponse = artistsResponse();

        when(browsingClient.getArtists(Map.of("musicFolderId",Collections.singletonList("1")))).thenReturn(artistsResponse);

        var result = browsingService.getArtists(ArtistParams.create().musicFolderId("1"));
        assertThat(result).isEqualTo(artistsResponse.getArtists().getIndices());
    }

    @Test
    void getArtists_error() {
        var error = subsonicError();
        var artistsResponse = artistsResponse();
        artistsResponse.setError(error);

        when(browsingClient.getArtists(Collections.emptyMap())).thenReturn(artistsResponse);

        assertSubsonicError(browsingService::getArtists, error);
    }

    @Test
    void getArtist() {
        var artistResponse = artistResponse();

        when(browsingClient.getArtist("1")).thenReturn(artistResponse);

        var result = browsingService.getArtist("1");
        assertThat(result).isEqualTo(artistResponse.getArtist());
    }

    @Test
    void getArtist_error() {
        var error = subsonicError();
        var artistResponse = artistResponse();
        artistResponse.setError(error);

        when(browsingClient.getArtist("1")).thenReturn(artistResponse);

        assertSubsonicError(()->browsingService.getArtist("1"), error);
    }

    @Test
    void getIndexes_noParams() {
        var indexesResponse = indexesResponse();

        when(browsingClient.getIndexes(Collections.emptyMap())).thenReturn(indexesResponse);

        var result = browsingService.getIndexes();
        assertThat(result).isEqualTo(indexesResponse.getIndexes().getIndices());
    }

    @Test
    void getIndexes_allParams() {
        var indexesResponse = indexesResponse();

        when(browsingClient.getIndexes(Map.of("musicFolderId",Collections.singletonList("1"),"ifModifiedSince",Collections.singletonList("123")))).thenReturn(indexesResponse);

        var result = browsingService.getIndexes(IndexParams.create().musicFolderId("1").ifModifiedSince(123));
        assertThat(result).isEqualTo(indexesResponse.getIndexes().getIndices());
    }

    @Test
    void getIndexes_error() {
        var error = subsonicError();
        var indexesResponse = indexesResponse();
        indexesResponse.setError(error);

        when(browsingClient.getIndexes(Collections.emptyMap())).thenReturn(indexesResponse);

        assertSubsonicError(browsingService::getIndexes, error);
    }

    @Test
    void getAlbum() {
        var albumResponse = albumResponse();

        when(browsingClient.getAlbum("1")).thenReturn(albumResponse);

        var result = browsingService.getAlbum("1");
        assertThat(result).isEqualTo(albumResponse.getAlbum());
    }

    @Test
    void getAlbum_error() {
        var error = subsonicError();
        var albumResponse = albumResponse();
        albumResponse.setError(error);

        when(browsingClient.getAlbum("1")).thenReturn(albumResponse);

        assertSubsonicError(()->browsingService.getAlbum("1"), error);
    }

    @Test
    void getSong() {
        var songResponse = songResponse();

        when(browsingClient.getSong("1")).thenReturn(songResponse);

        var result = browsingService.getSong("1");
        assertThat(result).isEqualTo(songResponse.getSong());
    }

    @Test
    void getSong_error() {
        var error = subsonicError();
        var songResponse = songResponse();
        songResponse.setError(error);

        when(browsingClient.getSong("1")).thenReturn(songResponse);

        assertSubsonicError(()->browsingService.getSong("1"), error);
    }

    @Test
    void getArtistInfo() {
        var artistInfoResponse = artistInfoResponse();

        when(browsingClient.getArtistInfo(Map.of(
                "id", List.of("1"),
                "count", List.of("20"),
                "includeNotPresent", List.of("false")
        ))).thenReturn(artistInfoResponse);

        var result = browsingService.getArtistInfo("1");
        assertThat(result).isEqualTo(artistInfoResponse.getArtistInfo());
    }

    @Test
    void getArtistInfo_withCountParam() {
        var artistInfoResponse = artistInfoResponse();

        when(browsingClient.getArtistInfo(Map.of(
                "id", List.of("1"),
                "count", List.of("15"),
                "includeNotPresent", List.of("false")
        ))).thenReturn(artistInfoResponse);

        var result = browsingService.getArtistInfo("1", ArtistInfoParams.create().count(15));
        assertThat(result).isEqualTo(artistInfoResponse.getArtistInfo());
    }

    @Test
    void getArtistInfo_withIncludeNotPresentParam() {
        var artistInfoResponse = artistInfoResponse();

        when(browsingClient.getArtistInfo(Map.of(
                "id", List.of("1"),
                "count", List.of("20"),
                "includeNotPresent", List.of("true")
        ))).thenReturn(artistInfoResponse);

        var result = browsingService.getArtistInfo("1", ArtistInfoParams.create().includeNotPresent(true));
        assertThat(result).isEqualTo(artistInfoResponse.getArtistInfo());
    }

    @Test
    void getArtistInfo_withAllParams() {
        var artistInfoResponse = artistInfoResponse();

        when(browsingClient.getArtistInfo(Map.of(
                "id", List.of("1"),
                "count", List.of("15"),
                "includeNotPresent", List.of("true")
        ))).thenReturn(artistInfoResponse);

        var result = browsingService.getArtistInfo("1", ArtistInfoParams.create().count(15).includeNotPresent(true));
        assertThat(result).isEqualTo(artistInfoResponse.getArtistInfo());
    }

    @Test
    void getArtistInfo_error() {
        var error = subsonicError();
        var artistInfoResponse = artistInfoResponse();
        artistInfoResponse.setError(error);

        when(browsingClient.getArtistInfo(anyMap())).thenReturn(artistInfoResponse);

        assertSubsonicError(()->browsingService.getArtistInfo("1"), error);
    }

    @Test
    void getArtistInfo2() {
        var artistInfo2Response = artistInfo2Response();

        when(browsingClient.getArtistInfo2(Map.of(
                "id", List.of("1"),
                "count", List.of("20"),
                "includeNotPresent", List.of("false")
        ))).thenReturn(artistInfo2Response);

        var result = browsingService.getArtistInfo2("1");
        assertThat(result).isEqualTo(artistInfo2Response.getArtistInfo2());
    }

    @Test
    void getArtistInfo2_withCountParam() {
        var artistInfo2Response = artistInfo2Response();

        when(browsingClient.getArtistInfo2(Map.of(
                "id", List.of("1"),
                "count", List.of("15"),
                "includeNotPresent", List.of("false")
        ))).thenReturn(artistInfo2Response);

        var result = browsingService.getArtistInfo2("1", ArtistInfoParams.create().count(15));
        assertThat(result).isEqualTo(artistInfo2Response.getArtistInfo2());
    }

    @Test
    void getArtistInfo2_withIncludeNotPresentParam() {
        var artistInfo2Response = artistInfo2Response();

        when(browsingClient.getArtistInfo2(Map.of(
                "id", List.of("1"),
                "count", List.of("20"),
                "includeNotPresent", List.of("true")
        ))).thenReturn(artistInfo2Response);

        var result = browsingService.getArtistInfo2("1", ArtistInfoParams.create().includeNotPresent(true));
        assertThat(result).isEqualTo(artistInfo2Response.getArtistInfo2());
    }

    @Test
    void getArtistInfo2_withAllParams() {
        var artistInfo2Response = artistInfo2Response();

        when(browsingClient.getArtistInfo2(Map.of(
                "id", List.of("1"),
                "count", List.of("15"),
                "includeNotPresent", List.of("true")
        ))).thenReturn(artistInfo2Response);

        var result = browsingService.getArtistInfo2("1", ArtistInfoParams.create().count(15).includeNotPresent(true));
        assertThat(result).isEqualTo(artistInfo2Response.getArtistInfo2());
    }

    @Test
    void getArtistInfo2_error() {
        var error = subsonicError();
        var artistInfo2Response = artistInfo2Response();
        artistInfo2Response.setError(error);

        when(browsingClient.getArtistInfo2(anyMap())).thenReturn(artistInfo2Response);

        assertSubsonicError(()->browsingService.getArtistInfo2("1"), error);
    }

    @Test
    void getAlbumInfo() {
        var albumInfoResponse = albumInfoResponse();

        when(browsingClient.getAlbumInfo("1")).thenReturn(albumInfoResponse);

        var result = browsingService.getAlbumInfo("1");
        assertThat(result).isEqualTo(albumInfoResponse.getAlbumInfo());
    }

    @Test
    void getAlbumInfo_error() {
        var error = subsonicError();
        var albumInfoResponse = albumInfoResponse();
        albumInfoResponse.setError(error);

        when(browsingClient.getAlbumInfo("1")).thenReturn(albumInfoResponse);

        assertSubsonicError(()->browsingService.getAlbumInfo("1"), error);
    }
}