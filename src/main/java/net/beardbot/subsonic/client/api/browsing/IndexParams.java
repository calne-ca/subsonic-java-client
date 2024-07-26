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
import lombok.NoArgsConstructor;
import net.beardbot.subsonic.client.base.ApiParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexParams extends ApiParams {
    public static IndexParams create(){
        return new IndexParams();
    }

    public IndexParams musicFolderId(String musicFolderId) {
        setParam("musicFolderId", musicFolderId);
        return this;
    }

    public IndexParams ifModifiedSince(long millis) {
        setParam("ifModifiedSince", String.valueOf(millis));
        return this;
    }
}
