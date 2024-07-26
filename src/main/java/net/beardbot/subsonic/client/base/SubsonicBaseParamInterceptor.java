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
package net.beardbot.subsonic.client.base;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import net.beardbot.subsonic.client.Subsonic;

@RequiredArgsConstructor
public class SubsonicBaseParamInterceptor implements RequestInterceptor {
    private final Subsonic subsonic;

    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("u",subsonic.getPreferences().getUsername());
        requestTemplate.query("s",subsonic.getPreferences().getAuthentication().getSalt());
        requestTemplate.query("t",subsonic.getPreferences().getAuthentication().getToken());
        requestTemplate.query("v",subsonic.getApiVersion().getVersionString());
        requestTemplate.query("c",subsonic.getPreferences().getClientName());
        requestTemplate.query("f","xml");
    }
}
