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
package net.beardbot.subsonic.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.beardbot.subsonic.client.api.annotation.AnnotationService;
import net.beardbot.subsonic.client.api.browsing.BrowsingService;
import net.beardbot.subsonic.client.api.libraryscan.LibraryScanService;
import net.beardbot.subsonic.client.api.lists.ListsService;
import net.beardbot.subsonic.client.api.media.MediaService;
import net.beardbot.subsonic.client.api.playlist.PlaylistService;
import net.beardbot.subsonic.client.api.search.SearchService;
import net.beardbot.subsonic.client.api.system.SystemService;
import net.beardbot.subsonic.client.api.usermanagement.UserManagementService;
import net.beardbot.subsonic.client.base.SubsonicException;
import net.beardbot.subsonic.client.base.SubsonicIncompatibilityException;
import net.beardbot.subsonic.client.base.Version;
import org.subsonic.restapi.ErrorCode;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Subsonic {
    @Getter private final SubsonicPreferences preferences;

    @Getter private static final Version API_MIN_VERSION = Version.of("1.13.0");
    @Getter private static final Version API_MAX_VERSION = Version.of("1.15.0");

    @Getter @Setter(AccessLevel.PACKAGE)
    private Version apiVersion = API_MAX_VERSION;

    private final SystemService systemService;
    private final BrowsingService browsingService;
    private final MediaService mediaService;
    private final SearchService searchService;
    private final PlaylistService playlistService;
    private final UserManagementService userManagementService;
    private final LibraryScanService libraryScanService;
    private final ListsService listsService;
    private final AnnotationService annotationService;

    public Subsonic(SubsonicPreferences preferences) {
        this.preferences = preferences;

        this.systemService = new SystemService(this);
        this.browsingService = new BrowsingService(this);
        this.mediaService = new MediaService(this);
        this.searchService = new SearchService(this);
        this.playlistService = new PlaylistService(this);
        this.userManagementService = new UserManagementService(this);
        this.libraryScanService = new LibraryScanService(this);
        this.listsService = new ListsService(this);
        this.annotationService = new AnnotationService(this);
    }

    public boolean testConnection(){
        try {
            system().ping();
        } catch (SubsonicException e){
            if (e.getErrorCode() == ErrorCode.INCOMPATIBLE_VERSION_SERVER){
                return negotiateApiVersion(Version.of(e.getApiVersion()));
            } if (e.getErrorCode() == ErrorCode.INCOMPATIBLE_VERSION_CLIENT){
                throw new SubsonicIncompatibilityException(Version.of(e.getApiVersion()), apiVersion);
            }
            return false;
        } catch (Exception e){
            return false;
        }

        return true;
    }

    private boolean negotiateApiVersion(Version serverApiVersion){
        if (serverApiVersion.isLowerThan(API_MIN_VERSION)){
            throw new SubsonicIncompatibilityException(serverApiVersion, API_MIN_VERSION);
        }

        apiVersion = serverApiVersion;
        return testConnection();
    }

    public SystemService system() {
        return systemService;
    }

    public BrowsingService browsing() {
        return browsingService;
    }

    public MediaService media() {
        return mediaService;
    }

    public SearchService searching() {
        return searchService;
    }

    public PlaylistService playlists() {
        return playlistService;
    }

    public UserManagementService users() {
        return userManagementService;
    }

    public LibraryScanService libraryScan() {
        return libraryScanService;
    }

    public ListsService lists() {
        return listsService;
    }

    public AnnotationService annotation() {
        return annotationService;
    }

    @SneakyThrows
    public URL createUrl(String path, Map<String, List<String>> params){
        final StringBuilder sb = new StringBuilder(preferences.getServerUrl());

        sb.append("/rest/").append(path);
        sb.append("?u=").append(preferences.getUsername());
        sb.append("&s=").append(preferences.getAuthentication().getSalt());
        sb.append("&t=").append(preferences.getAuthentication().getToken());
        sb.append("&v=").append(getApiVersion().getVersionString());
        sb.append("&c=").append(preferences.getClientName());
        sb.append("&f=").append("xml");

        params.keySet().forEach(k-> params.get(k).forEach(v->sb.append("&").append(k).append("=").append(v)));

        return new URL(sb.toString().replace("//rest","/rest"));
    }
}
