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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.*;

import java.util.List;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BrowsingService {
    private final BrowsingClient browsingClient;

    public BrowsingService(Subsonic subsonic) {
        browsingClient = SubsonicClient.create(subsonic, BrowsingClient.class);
    }

    public List<MusicFolder> getMusicFolders(){
        log.debug("Fetching all music folders.");

        var response = browsingClient.getMusicFolders();
        handleError(response);
        
        return response.getMusicFolders().getMusicFolders();
    }

    public List<Index> getIndexes(){
        return getIndexes(IndexParams.create());
    }

    public List<Index> getIndexes(IndexParams indexParams){
        log.debug("Fetching index with params '{}'.", indexParams.getParamMapForLogging());

        var response = browsingClient.getIndexes(indexParams.getParamMap());
        handleError(response);

        return response.getIndexes().getIndices();
    }

    public Directory getMusicDirectory(String id){
        log.debug("Fetching music directory with id '{}'.", id);

        var response = browsingClient.getMusicDirectory(id);
        handleError(response);

        return response.getDirectory();
    }

    public ArtistWithAlbumsID3 getArtist(String id){
        log.debug("Fetching artist with id '{}'.", id);

        var response = browsingClient.getArtist(id);
        handleError(response);

        return response.getArtist();
    }

    public List<IndexID3> getArtists(){
        return getArtists(ArtistParams.create());
    }

    public List<IndexID3> getArtists(ArtistParams artistParams){
        log.debug("Fetching artists with params '{}'.", artistParams.getParamMapForLogging());

        var response = browsingClient.getArtists(artistParams.getParamMap());
        handleError(response);

        return response.getArtists().getIndices();
    }

    public List<Genre> getGenres(){
        log.debug("Fetching all genres.");

        var response = browsingClient.getGenres();
        handleError(response);

        return response.getGenres().getGenres();
    }

    public AlbumWithSongsID3 getAlbum(String id){
        log.debug("Fetching albums with id '{}'.", id);

        var response = browsingClient.getAlbum(id);
        handleError(response);

        return response.getAlbum();
    }

    public Child getSong(String id){
        log.debug("Fetching song with id '{}'.", id);

        var response = browsingClient.getSong(id);
        handleError(response);

        return response.getSong();
    }
}
