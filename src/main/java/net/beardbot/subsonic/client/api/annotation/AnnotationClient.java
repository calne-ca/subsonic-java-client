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

import feign.Param;
import feign.RequestLine;
import org.subsonic.restapi.SubsonicResponse;

public interface AnnotationClient {
    @RequestLine("GET /rest/scrobble?id={id}&submission={submission}")
    SubsonicResponse scrobble(@Param("id") String id, @Param("submission") boolean submission);

    @RequestLine("GET /rest/scrobble?id={id}&submission={submission}&time={time}")
    SubsonicResponse scrobble(@Param("id") String id, @Param("submission") boolean submission, @Param("time") long timeInMillis);

    @RequestLine("GET /rest/star?id={id}")
    SubsonicResponse starById(@Param("id") String id);

    @RequestLine("GET /rest/star?albumId={albumId}")
    SubsonicResponse starByAlbumId(@Param("albumId") String albumId);

    @RequestLine("GET /rest/star?artistId={artistId}")
    SubsonicResponse starByArtistId(@Param("artistId") String artistId);

    @RequestLine("GET /rest/unstar?id={id}")
    SubsonicResponse unstarById(@Param("id") String id);

    @RequestLine("GET /rest/unstar?albumId={albumId}")
    SubsonicResponse unstarByAlbumId(@Param("albumId") String albumId);

    @RequestLine("GET /rest/unstar?artistId={artistId}")
    SubsonicResponse unstarByArtistId(@Param("artistId") String artistId);

    @RequestLine("GET /rest/setRating?id={id}&rating={rating}")
    SubsonicResponse setRating(@Param("id") String id, @Param("rating") int rating);
}
