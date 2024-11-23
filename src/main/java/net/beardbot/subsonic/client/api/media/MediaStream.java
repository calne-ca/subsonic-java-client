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
package net.beardbot.subsonic.client.api.media;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.beardbot.subsonic.client.base.SubsonicException;
import net.beardbot.subsonic.client.utils.JaxbUtil;
import org.subsonic.restapi.ErrorCode;
import org.subsonic.restapi.SubsonicResponse;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@RequiredArgsConstructor
public class MediaStream {
    @Getter private final URL url;
    @Getter private Integer contentLength;
    private InputStream inputStream;

    public InputStream getInputStream(){
        if (inputStream == null) {
            inputStream = createInputStream();
        }

        return inputStream;
    }

    private InputStream createInputStream() {
        try {
            var connection =  url.openConnection();
            var inputStream = connection.getInputStream();
            if (connection.getContentType().contains("xml")){
                handleError(JaxbUtil.unmarshall(inputStream, SubsonicResponse.class));
            }
            contentLength = connection.getContentLength();
            return new BufferedInputStream(inputStream);
        } catch (FileNotFoundException e) {
            throw new SubsonicException(ErrorCode.DATA_NOT_FOUND, "The requested data was not found.");
        } catch (IOException e) {
            throw new SubsonicException(ErrorCode.GENERIC_ERROR, "Unknown error.");
        }
    }
}
