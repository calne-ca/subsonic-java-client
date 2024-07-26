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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.Playlist;
import org.subsonic.restapi.PlaylistWithSongs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PlaylistService {
    private final PlaylistClient playlistClient;

    public PlaylistService(Subsonic subsonic) {
        this.playlistClient = SubsonicClient.create(subsonic, PlaylistClient.class);
    }

    public List<Playlist> getPlaylists(){
        log.debug("Fetching all playlists.");

        var response = playlistClient.getPlaylists();
        handleError(response);

        return response.getPlaylists().getPlaylists();
    }

    public List<Playlist> getPlaylists(String username){
        log.debug("Fetching playlists for user '{}'.", username);

        var response = playlistClient.getPlaylists(username);
        handleError(response);

        return response.getPlaylists().getPlaylists();
    }

    public PlaylistWithSongs getPlaylist(String id){
        log.debug("Fetching playlist with id '{}'.", id);

        var response = playlistClient.getPlaylist(id);
        handleError(response);

        return response.getPlaylist();
    }

    public void createPlaylist(String name, String... songIds){
        createPlaylist(name, Arrays.asList(songIds));
    }

    public void createPlaylist(String name, List<String> songIds){
        log.debug("Creating playlist with name '{}' and songs ids '{}'.", name, songIds);

        var response = playlistClient.createPlaylist(name, Map.of("songId",songIds));
        handleError(response);
    }

    public void updatePlaylist(String id, UpdatePlaylistParams params){
        log.debug("Updating playlist with id '{}' and params '{}'.", id, params.getParamMapForLogging());

        var response = playlistClient.updatePlaylist(id, params.getParamMap());
        handleError(response);
    }

    public void deletePlaylist(String id){
        log.debug("Deleting playlist with id '{}'.", id);

        var response = playlistClient.deletePlaylist(id);
        handleError(response);
    }
}
