/**
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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.SearchResult2;
import org.subsonic.restapi.SearchResult3;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SearchService {
    private final SearchClient searchClient;

    public SearchService(Subsonic subsonic) {
        this.searchClient = SubsonicClient.create(subsonic,SearchClient.class);
    }

    public SearchResult2 search2(String query){
        return search2(query, SearchParams.create());
    }

    public SearchResult2 search2(String query, SearchParams searchParams){
        log.debug("Searching using search2 with query '{}' and params '{}'", query, searchParams.getParamMapForLogging());

        var response = searchClient.search2(query, searchParams.getParamMap());
        handleError(response);

        return response.getSearchResult2();
    }

    public SearchResult3 search3(String query){
        return search3(query, SearchParams.create());
    }

    public SearchResult3 search3(String query, SearchParams searchParams){
        log.debug("Searching using search3 with query '{}' and params '{}'", query, searchParams.getParamMapForLogging());

        var response = searchClient.search3(query, searchParams.getParamMap());
        handleError(response);

        return response.getSearchResult3();
    }
}
