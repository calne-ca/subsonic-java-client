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

import feign.QueryMap;
import feign.RequestLine;
import org.subsonic.restapi.SubsonicResponse;

import java.util.List;
import java.util.Map;

public interface ListsClient {
    @RequestLine("GET /rest/getNowPlaying")
    SubsonicResponse getNowPlaying();

    @RequestLine("GET /rest/getStarred")
    SubsonicResponse getStarred();

    @RequestLine("GET /rest/getStarred2")
    SubsonicResponse getStarred2();

    @RequestLine("GET /rest/getAlbumList")
    SubsonicResponse getAlbumList(@QueryMap Map<String, List<String>> params);

    @RequestLine("GET /rest/getAlbumList2")
    SubsonicResponse getAlbumList2(@QueryMap Map<String,List<String>> params);
}
