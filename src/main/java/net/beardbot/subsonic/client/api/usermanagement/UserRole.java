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
package net.beardbot.subsonic.client.api.usermanagement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("adminRole"),
    SETTINGS("settingsRole"),
    STREAM("streamRole"),
    JUKEBOX("jukeboxRole"),
    DOWNLOAD("downloadRole"),
    UPLOAD("uploadRole"),
    PLAYLISTS("playlistRole"),
    COVER_ART("coverArtRole"),
    COMMENT("commentRole"),
    PODCAST("podcastRole"),
    SHARE("shareRole"),
    VIDEO_CONVERSION("videoConversionRole");

    public static UserRole[] all(){
        return UserRole.values();
    }

    public static UserRole[] allExcept(UserRole... userRole){
        return Arrays.stream(all())
                .filter(u -> !Arrays.asList(userRole).contains(u)).toArray(UserRole[]::new);
    }

    private final String roleName;
}
