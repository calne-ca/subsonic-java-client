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

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.subsonic.restapi.*;

import java.util.List;
import java.util.Map;

public interface BrowsingClient {

    @RequestLine("GET /rest/getMusicFolders")
    SubsonicResponse getMusicFolders();

    @RequestLine("GET /rest/getIndexes")
    SubsonicResponse getIndexes(@QueryMap Map<String, List<String>> params);

    @RequestLine("GET /rest/getMusicDirectory?id={id}")
    SubsonicResponse getMusicDirectory(@Param("id") String id);

    @RequestLine("GET /rest/getArtist?id={id}")
    SubsonicResponse getArtist(@Param("id") String id);

    @RequestLine("GET /rest/getArtists")
    SubsonicResponse getArtists(@QueryMap Map<String,List<String>> params);

    @RequestLine("GET /rest/getGenres")
    SubsonicResponse getGenres();

    @RequestLine("GET /rest/getAlbum?id={id}")
    SubsonicResponse getAlbum(@Param("id") String id);

    @RequestLine("GET /rest/getSong?id={id}")
    SubsonicResponse getSong(@Param("id") String id);

    @RequestLine("GET /rest/getArtistInfo")
    SubsonicResponse getArtistInfo(@QueryMap Map<String,List<String>> params);

    @RequestLine("GET /rest/getArtistInfo2")
    SubsonicResponse getArtistInfo2(@QueryMap Map<String,List<String>> params);

    @RequestLine("GET /rest/getAlbumInfo?id={id}")
    SubsonicResponse getAlbumInfo(@Param("id") String id);

    @RequestLine("GET /rest/getAlbumInfo2?id={id}")
    SubsonicResponse getAlbumInfo2(@Param("id") String id);
}
