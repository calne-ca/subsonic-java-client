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
package net.beardbot.subsonic.client.api.media;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.base.*;
import net.beardbot.subsonic.client.utils.JaxbUtil;
import org.subsonic.restapi.ErrorCode;
import org.subsonic.restapi.SubsonicResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
public class MediaService {
    private final Subsonic subsonic;

    public MediaService(Subsonic subsonic) {
        this.subsonic = subsonic;
    }

    @SneakyThrows
    public InputStream stream(String id){
        var params = StreamParams.create()
                .format(subsonic.getPreferences().getStreamFormat())
                .maxBitRate(subsonic.getPreferences().getStreamBitRate()).getParamMap();

        params.put("id", Collections.singletonList(id));

        log.debug("Fetching audio stream with params '{}'.", params);

        URL url = subsonic.createUrl("stream", params);

        return safeOpenStream(url);
    }

    @SneakyThrows
    public InputStream download(String id){
        var params = DownloadParams.create().id(id);
        log.debug("Downloading song with params '{}'.", params.getParamMapForLogging());

        URL url = subsonic.createUrl("download", params.getParamMap());

        return safeOpenStream(url);
    }

    public InputStream getCoverArt(String id){
        return getCoverArt(id, CoverArtParams.create());
    }

    @SneakyThrows
    public InputStream getCoverArt(String id, CoverArtParams coverArtParams){
        var params = coverArtParams.getParamMap();
        params.put("id",Collections.singletonList(id));

        log.debug("Downloading cover art with params '{}'.", params);

        URL url = subsonic.createUrl("getCoverArt", params);

        return safeOpenStream(url);
    }

    public InputStream getAvatar(){
        return getAvatar(subsonic.getPreferences().getUsername());
    }

    @SneakyThrows
    public InputStream getAvatar(String username){
        var params = AvatarParams.create().username(username);

        log.debug("Downloading avatar with params '{}'.", params.getParamMapForLogging());

        URL url = subsonic.createUrl("getAvatar", params.getParamMap());

        return safeOpenStream(url);
    }

    private InputStream safeOpenStream(URL url){
        log.debug("Downloading resource {}", url);
        
        try {
            var connection =  url.openConnection();
            var inputStream = connection.getInputStream();
            if (connection.getContentType().contains("xml")){
                handleError(JaxbUtil.unmarshall(inputStream, SubsonicResponse.class));
            }
            return inputStream;
        } catch (FileNotFoundException e) {
            throw new SubsonicException(ErrorCode.DATA_NOT_FOUND, "The requested data was not found.");
        } catch (IOException e) {
            throw new SubsonicException(ErrorCode.GENERIC_ERROR, "Unknown error.");
        }
    }
}