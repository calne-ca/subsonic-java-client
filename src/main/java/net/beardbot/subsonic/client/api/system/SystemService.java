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
package net.beardbot.subsonic.client.api.system;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.License;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SystemService {
    private final SystemClient systemClient;

    public SystemService(Subsonic subsonic) {
        systemClient = SubsonicClient.create(subsonic,SystemClient.class);
    }

    public void ping(){
        log.debug("Requesting ping");

        var response = systemClient.ping();
        log.debug("Status: '{}' Version: '{}' ", response.getStatus(), response.getVersion());

        handleError(response);
    }

    public License getLicense(){
        log.debug("Fetching license");

        var response = systemClient.getLicense();
        handleError(response);

        return response.getLicense();
    }
}
