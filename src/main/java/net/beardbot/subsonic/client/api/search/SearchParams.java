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
import lombok.NoArgsConstructor;
import net.beardbot.subsonic.client.api.usermanagement.UserRole;
import net.beardbot.subsonic.client.base.ApiParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchParams extends ApiParams {
    public static SearchParams create(){
        return new SearchParams();
    }

    public SearchParams artistCount(int artistCount) {
        setParam("artistCount", String.valueOf(artistCount));
        return this;
    }

    public SearchParams artistOffset(int artistOffset) {
        setParam("artistOffset", String.valueOf(artistOffset));
        return this;
    }

    public SearchParams albumCount(int albumCount) {
        setParam("albumCount", String.valueOf(albumCount));
        return this;
    }

    public SearchParams albumOffset(int albumOffset) {
        setParam("albumOffset", String.valueOf(albumOffset));
        return this;
    }

    public SearchParams songCount(int songCount) {
        setParam("songCount", String.valueOf(songCount));
        return this;
    }

    public SearchParams songOffset(int songOffset) {
        setParam("songOffset", String.valueOf(songOffset));
        return this;
    }

    public SearchParams musicFolderId(String musicFolderId) {
        setParam("musicFolderId", musicFolderId);
        return this;
    }
}
