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
package net.beardbot.subsonic.client.api.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static net.beardbot.subsonic.client.api.TestUtil.search3Response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private SearchClient searchClient;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(searchClient);
    }

    @Test
    void search2_noParams() {
        var search2Response = search2Response();

        when(searchClient.search2("test", Collections.emptyMap())).thenReturn(search2Response);

        var result = searchService.search2("test");
        assertThat(result.getSongs()).isEqualTo(search2Response.getSearchResult2().getSongs());
        assertThat(result.getAlbums()).isEqualTo(search2Response.getSearchResult2().getAlbums());
        assertThat(result.getArtists()).isEqualTo(search2Response.getSearchResult2().getArtists());
    }

    @Test
    void search2_allParams() {
        var search2Response = search2Response();
        var params = Map.of(
                "songCount",Collections.singletonList("1"),
                "albumCount",Collections.singletonList("2"),
                "artistCount",Collections.singletonList("3"),
                "albumOffset",Collections.singletonList("4"),
                "artistOffset",Collections.singletonList("5"),
                "songOffset",Collections.singletonList("6"),
                "musicFolderId",Collections.singletonList("7"));

        when(searchClient.search2("test", params)).thenReturn(search2Response);

        var result = searchService.search2("test",
                SearchParams.create().songCount(1).albumCount(2).artistCount(3).albumOffset(4).artistOffset(5).songOffset(6).musicFolderId("7"));

        assertThat(result.getSongs()).isEqualTo(search2Response.getSearchResult2().getSongs());
        assertThat(result.getAlbums()).isEqualTo(search2Response.getSearchResult2().getAlbums());
        assertThat(result.getArtists()).isEqualTo(search2Response.getSearchResult2().getArtists());
    }

    @Test
    void search2_error() {
        var error = subsonicError();
        var search2Response = search2Response();
        search2Response.setError(error);

        when(searchClient.search2("test", Collections.emptyMap())).thenReturn(search2Response);

        assertSubsonicError(()->searchService.search2("test"), error);
    }

    @Test
    void search3_noParams() {
        var search3Response = search3Response();

        when(searchClient.search3("test", Collections.emptyMap())).thenReturn(search3Response);

        var result = searchService.search3("test");
        assertThat(result.getSongs()).isEqualTo(search3Response.getSearchResult3().getSongs());
        assertThat(result.getAlbums()).isEqualTo(search3Response.getSearchResult3().getAlbums());
        assertThat(result.getArtists()).isEqualTo(search3Response.getSearchResult3().getArtists());
    }

    @Test
    void search3_allParams() {
        var search3Response = search3Response();
        var params = Map.of(
                "songCount",Collections.singletonList("1"),
                "albumCount",Collections.singletonList("2"),
                "artistCount",Collections.singletonList("3"),
                "albumOffset",Collections.singletonList("4"),
                "artistOffset",Collections.singletonList("5"),
                "songOffset",Collections.singletonList("6"),
                "musicFolderId",Collections.singletonList("7"));

        when(searchClient.search3("test", params)).thenReturn(search3Response);

        var result = searchService.search3("test",
                SearchParams.create().songCount(1).albumCount(2).artistCount(3).albumOffset(4).artistOffset(5).songOffset(6).musicFolderId("7"));

        assertThat(result.getSongs()).isEqualTo(search3Response.getSearchResult3().getSongs());
        assertThat(result.getAlbums()).isEqualTo(search3Response.getSearchResult3().getAlbums());
        assertThat(result.getSongs()).isEqualTo(search3Response.getSearchResult3().getSongs());
    }

    @Test
    void search3_error() {
        var error = subsonicError();
        var search3Response = search3Response();
        search3Response.setError(error);

        when(searchClient.search3("test", Collections.emptyMap())).thenReturn(search3Response);

        assertSubsonicError(()->searchService.search3("test"), error);
    }
}