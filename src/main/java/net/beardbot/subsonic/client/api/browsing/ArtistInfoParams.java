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

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistInfoParams extends ApiParams {
    public static ArtistInfoParams create(){
        return new ArtistInfoParams();
    }

    public ArtistInfoParams count(int count) {
        setParam("count", String.valueOf(count));
        return this;
    }

    public ArtistInfoParams includeNotPresent(boolean includeNotPresent) {
        setParam("includeNotPresent", String.valueOf(includeNotPresent));
        return this;
    }

    @Override
    protected Map<String, List<String>> defaultParams() {
        return Map.of("count", List.of("20"),
                "includeNotPresent", List.of("false"));
    }
}
