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
package net.beardbot.subsonic.client.api.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnnotationsServiceTest {
    @Mock
    private AnnotationClient annotationClient;

    private AnnotationService annotationService;

    @BeforeEach
    void setUp() {
        annotationService = new AnnotationService(annotationClient);
    }

    @Test
    void scrobble() {
        when(annotationClient.scrobble("1", true)).thenReturn(subsonicResponse());
        annotationService.scrobble("1");

        when(annotationClient.scrobble("1", true, 123456789)).thenReturn(subsonicResponse());
        annotationService.scrobble("1", 123456789);
    }

    @Test
    void scrobble_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(annotationClient.scrobble("1", true)).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.scrobble("1"), error);

        when(annotationClient.scrobble("1", true, 123456789)).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.scrobble("1", 123456789), error);
    }

    @Test
    void scrobbleNowPlaying() {
        when(annotationClient.scrobble("1", false)).thenReturn(subsonicResponse());
        annotationService.scrobbleNowPlaying("1");
    }

    @Test
    void scrobbleNowPlaying_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(annotationClient.scrobble("1", false)).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.scrobbleNowPlaying("1"), error);
    }

    @Test
    void star() {
        when(annotationClient.starById("1")).thenReturn(subsonicResponse());
        annotationService.star("1");

        when(annotationClient.starByAlbumId("1")).thenReturn(subsonicResponse());
        annotationService.starAlbumID3("1");

        when(annotationClient.starByArtistId("1")).thenReturn(subsonicResponse());
        annotationService.starArtistID3("1");
    }

    @Test
    void star_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(annotationClient.starById("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.star("1"), error);

        when(annotationClient.starByAlbumId("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.starAlbumID3("1"), error);

        when(annotationClient.starByArtistId("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.starArtistID3("1"), error);
    }

    @Test
    void unstar() {
        when(annotationClient.unstarById("1")).thenReturn(subsonicResponse());
        annotationService.unstar("1");

        when(annotationClient.unstarByAlbumId("1")).thenReturn(subsonicResponse());
        annotationService.unstarAlbumID3("1");

        when(annotationClient.unstarByArtistId("1")).thenReturn(subsonicResponse());
        annotationService.unstarArtistID3("1");
    }

    @Test
    void unstar_error() {
        var error = subsonicError();
        var errorResponse = subsonicResponse();
        errorResponse.setError(error);

        when(annotationClient.unstarById("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.unstar("1"), error);

        when(annotationClient.unstarByAlbumId("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.unstarAlbumID3("1"), error);

        when(annotationClient.unstarByArtistId("1")).thenReturn(errorResponse);
        assertSubsonicError(()->annotationService.unstarArtistID3("1"), error);
    }
}