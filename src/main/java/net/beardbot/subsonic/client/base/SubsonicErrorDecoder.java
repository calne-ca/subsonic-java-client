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
package net.beardbot.subsonic.client.base;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.subsonic.restapi.ErrorCode;

public class SubsonicErrorDecoder extends ErrorDecoder.Default {
    @Override
    public Exception decode(String s, Response response) {
        // Airsonic responds with a Spring 403 error response instead of the intended error code, so we fix it here.
        if (response.status() == 403){
            return new SubsonicException(ErrorCode.USER_NOT_AUTHORIZED, "User not authorized.");
        }

        return super.decode(s,response);
    }
}
