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
package net.beardbot.subsonic.client.api.annotation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.SubsonicClient;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnnotationService {
    private final AnnotationClient annotationClient;

    public AnnotationService(Subsonic subsonic) {
        this.annotationClient = SubsonicClient.create(subsonic, AnnotationClient.class);
    }

    public void scrobble(String id){
        log.debug("Scrobbling song id with '{}'.", id);

        var response = annotationClient.scrobble(id, true);
        handleError(response);
    }

    public void scrobble(String id, long timeInMillis){
        log.debug("Scrobbling song id with '{}' at time {}.", id, timeInMillis);

        var response = annotationClient.scrobble(id, true, timeInMillis);
        handleError(response);
    }

    public void scrobbleNowPlaying(String id){
        log.debug("Scrobbling now playing for song id '{}'.", id);

        var response = annotationClient.scrobble(id, false);
        handleError(response);
    }
}
