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

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
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
}