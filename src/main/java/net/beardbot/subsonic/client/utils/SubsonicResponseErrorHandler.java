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
package net.beardbot.subsonic.client.utils;

import net.beardbot.subsonic.client.base.SubsonicException;
import org.subsonic.restapi.SubsonicResponse;

public class SubsonicResponseErrorHandler {
    public static void handleError(SubsonicResponse response){
        var error = response.getError();

        if (error != null){
            throw new SubsonicException(error.getCode(), error.getMessage(), response.getVersion());
        }
    }
}
