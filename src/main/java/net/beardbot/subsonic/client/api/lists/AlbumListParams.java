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
import lombok.NoArgsConstructor;
import net.beardbot.subsonic.client.base.ApiParams;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AlbumListParams extends ApiParams {
    public static AlbumListParams create(){
        return new AlbumListParams();
    }

    public AlbumListParams type(AlbumListType type) {
        setParam("type", type.getValue());
        return this;
    }

    public AlbumListParams size(int size) {
        setParam("size", String.valueOf(size));
        return this;
    }

    public AlbumListParams offset(int offset) {
        setParam("offset", String.valueOf(offset));
        return this;
    }

    public AlbumListParams fromYear(int fromYear) {
        setParam("fromYear", String.valueOf(fromYear));
        return this;
    }

    public AlbumListParams toYear(int toYear) {
        setParam("toYear", String.valueOf(toYear));
        return this;
    }

    public AlbumListParams genre(String genre) {
        setParam("genre", genre);
        return this;
    }

    public AlbumListParams musicFolderId(int musicFolderId) {
        setParam("musicFolderId", String.valueOf(musicFolderId));
        return this;
    }

    @Override
    protected Map<String, List<String>> defaultParams() {
        return Map.of(
                "type", List.of(AlbumListType.RANDOM.getValue()),
                "size", List.of("10"),
                "offset", List.of("0"));
    }
}
