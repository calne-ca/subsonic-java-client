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
package net.beardbot.subsonic.client.api.lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListsServiceTest {
    @Mock
    private ListsClient listsClient;

    private ListsService listsService;

    @BeforeEach
    void setUp() {
        listsService = new ListsService(listsClient);
    }

    @Test
    void getNowPlaying() {
        var nowPlayingResponse = nowPlayingResponse();

        when(listsClient.getNowPlaying()).thenReturn(nowPlayingResponse);

        var nowPlaying = listsService.getNowPlaying();
        assertThat(nowPlaying).isEqualTo(nowPlayingResponse.getNowPlaying().getEntries());
    }

    @Test
    void getNowPlaying_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(listsClient.getNowPlaying()).thenReturn(errorResponse);

        assertSubsonicError(()->listsService.getNowPlaying(), error);
    }

    @Test
    void getStarred() {
        var response = starredResponse();

        when(listsClient.getStarred()).thenReturn(response);

        var starred = listsService.getStarred();
        assertThat(starred).isEqualTo(response.getStarred());
    }

    @Test
    void getStarred_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(listsClient.getStarred()).thenReturn(errorResponse);

        assertSubsonicError(()->listsService.getStarred(), error);
    }

    @Test
    void getStarred2() {
        var response = starred2Response();

        when(listsClient.getStarred2()).thenReturn(response);

        var starred2 = listsService.getStarred2();
        assertThat(starred2).isEqualTo(response.getStarred2());
    }

    @Test
    void getStarred2_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(listsClient.getStarred2()).thenReturn(errorResponse);

        assertSubsonicError(()->listsService.getStarred2(), error);
    }

    @Test
    void getAlbumList_defaultParams() {
        var response = albumListResponse();

        var expectedParams = Map.of(
                "type", List.of(AlbumListType.RANDOM.getValue()),
                "size", List.of("10"),
                "offset", List.of("0"));

        when(listsClient.getAlbumList(expectedParams)).thenReturn(response);

        var albumList = listsService.getAlbumList();
        assertThat(albumList).isEqualTo(response.getAlbumList());
    }

    @Test
    void getAlbumList_allParams() {
        var response = albumListResponse();

        var expectedParams = Map.of(
                "type", List.of(AlbumListType.BY_YEAR.getValue()),
                "fromYear", List.of("2010"),
                "toYear", List.of("2020"),
                "musicFolderId", List.of("1"),
                "genre", List.of("Rock"),
                "size", List.of("20"),
                "offset", List.of("2"));

        when(listsClient.getAlbumList(expectedParams)).thenReturn(response);

        var albumList = listsService.getAlbumList(AlbumListParams.create()
                .offset(2).size(20).type(AlbumListType.BY_YEAR).fromYear(2010).toYear(2020).musicFolderId(1).genre("Rock")
        );
        assertThat(albumList).isEqualTo(response.getAlbumList());
    }

    @Test
    void getAlbumList_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(listsClient.getAlbumList(anyMap())).thenReturn(errorResponse);

        assertSubsonicError(()->listsService.getAlbumList(), error);
        assertSubsonicError(()->listsService.getAlbumList(AlbumListParams.create()), error);
    }

    @Test
    void getAlbumList2_defaultParams() {
        var response = albumList2Response();

        var expectedParams = Map.of(
                "type", List.of(AlbumListType.RANDOM.getValue()),
                "size", List.of("10"),
                "offset", List.of("0"));

        when(listsClient.getAlbumList2(expectedParams)).thenReturn(response);

        var albumList = listsService.getAlbumList2();
        assertThat(albumList).isEqualTo(response.getAlbumList2());
    }

    @Test
    void getAlbumList2_allParams() {
        var response = albumList2Response();

        var expectedParams = Map.of(
                "type", List.of(AlbumListType.BY_YEAR.getValue()),
                "fromYear", List.of("2010"),
                "toYear", List.of("2020"),
                "musicFolderId", List.of("1"),
                "genre", List.of("Rock"),
                "size", List.of("20"),
                "offset", List.of("2"));

        when(listsClient.getAlbumList2(expectedParams)).thenReturn(response);

        var albumList = listsService.getAlbumList2(AlbumListParams.create()
                .offset(2).size(20).type(AlbumListType.BY_YEAR).fromYear(2010).toYear(2020).musicFolderId(1).genre("Rock")
        );
        assertThat(albumList).isEqualTo(response.getAlbumList2());
    }

    @Test
    void getAlbumList2_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(listsClient.getAlbumList2(anyMap())).thenReturn(errorResponse);

        assertSubsonicError(()->listsService.getAlbumList2(), error);
        assertSubsonicError(()->listsService.getAlbumList2(AlbumListParams.create()), error);
    }
}