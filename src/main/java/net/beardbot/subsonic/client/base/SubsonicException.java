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

import lombok.Getter;
import org.subsonic.restapi.ErrorCode;

@Getter
public class SubsonicException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String errorMessage;
    private final String apiVersion;

    public SubsonicException(ErrorCode errorCode, String errorMessage) {
        this(errorCode,errorMessage,null);
    }

    public SubsonicException(ErrorCode errorCode, String errorMessage, String apiVersion) {
        super(errorMessage);

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.apiVersion = apiVersion;
    }
}
