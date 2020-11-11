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
package net.beardbot.subsonic.client.api.libraryscan;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.ScanStatus;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LibraryScanService {
    private final LibraryScanClient libraryScanClient;

    public LibraryScanService(Subsonic subsonic) {
        libraryScanClient = SubsonicClient.create(subsonic, LibraryScanClient.class);
    }

    public ScanStatus startScan(){
        log.debug("Starting library scan.");

        var response = libraryScanClient.startScan();
        handleError(response);

        return response.getScanStatus();
    }

    public ScanStatus getScanStatus(){
        log.debug("Fetching library scan status.");

        var response = libraryScanClient.getScanStatus();
        handleError(response);

        return response.getScanStatus();
    }
}
