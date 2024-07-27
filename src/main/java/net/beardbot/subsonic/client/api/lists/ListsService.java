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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.NowPlayingEntry;
import org.subsonic.restapi.Starred;
import org.subsonic.restapi.Starred2;

import java.util.List;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ListsService {
    private final ListsClient listsClient;

    public ListsService(Subsonic subsonic) {
        this.listsClient = SubsonicClient.create(subsonic, ListsClient.class);
    }

    public List<NowPlayingEntry> getNowPlaying(){
        log.debug("Fetching now playing.");

        var response = listsClient.getNowPlaying();
        handleError(response);

        return response.getNowPlaying().getEntries();
    }

    public Starred getStarred(){
        log.debug("Fetching starred.");

        var response = listsClient.getStarred();
        handleError(response);

        return response.getStarred();
    }

    public Starred2 getStarred2(){
        log.debug("Fetching starred2.");

        var response = listsClient.getStarred2();
        handleError(response);

        return response.getStarred2();
    }
}
