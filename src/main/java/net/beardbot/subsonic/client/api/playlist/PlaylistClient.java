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
package net.beardbot.subsonic.client.api.playlist;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.subsonic.restapi.SubsonicResponse;

import java.util.List;
import java.util.Map;

public interface PlaylistClient {
    @RequestLine("GET /rest/getPlaylists")
    SubsonicResponse getPlaylists();

    @RequestLine("GET /rest/getPlaylists?username={username}")
    SubsonicResponse getPlaylists(@Param("username") String username);

    @RequestLine("GET /rest/getPlaylist?id={id}")
    SubsonicResponse getPlaylist(@Param("id") String id);

    @RequestLine("GET /rest/createPlaylist?name={name}")
    SubsonicResponse createPlaylist(@Param("name") String name, @QueryMap Map<String, List<String>> params);

    @RequestLine("GET /rest/updatePlaylist?playlistId={playlistId}")
    SubsonicResponse updatePlaylist(@Param("playlistId") String id, @QueryMap Map<String, List<String>> params);

    @RequestLine("GET /rest/deletePlaylist?id={id}")
    SubsonicResponse deletePlaylist(@Param("id") String id);
}
