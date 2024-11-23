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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;

import java.util.Collections;

@Slf4j
public class MediaService {
    private final Subsonic subsonic;

    public MediaService(Subsonic subsonic) {
        this.subsonic = subsonic;
    }

    @SneakyThrows
    public MediaStream stream(String id){
        var params = StreamParams.create()
                .format(subsonic.getPreferences().getStreamFormat())
                .maxBitRate(subsonic.getPreferences().getStreamBitRate())
                .estimateContentLength(subsonic.getPreferences().isEstimateContentLength());

        return stream(id, params);
    }

    public MediaStream stream(String id, StreamParams streamParams){
        var params = streamParams.getParamMap();
        params.put("id", Collections.singletonList(id));

        var url = subsonic.createUrl("stream", params);

        log.debug("Fetching audio stream '{}'.", url);

        return new MediaStream(url);
    }

    @SneakyThrows
    public MediaStream download(String id){
        var params = DownloadParams.create().id(id);
        log.debug("Downloading song with params '{}'.", params.getParamMapForLogging());

        var url = subsonic.createUrl("download", params.getParamMap());

        log.debug("Fetching audio stream '{}'.", url);

        return new MediaStream(url);
    }

    public MediaStream getCoverArt(String id){
        return getCoverArt(id, CoverArtParams.create());
    }

    @SneakyThrows
    public MediaStream getCoverArt(String id, CoverArtParams coverArtParams){
        var params = coverArtParams.getParamMap();
        params.put("id",Collections.singletonList(id));

        var url = subsonic.createUrl("getCoverArt", params);

        log.debug("Fetching cover art with params '{}'.", coverArtParams);
        return new MediaStream(url);
    }

    public MediaStream getAvatar(){
        return getAvatar(subsonic.getPreferences().getUsername());
    }

    @SneakyThrows
    public MediaStream getAvatar(String username){
        var params = AvatarParams.create().username(username);

        log.debug("Downloading avatar with params '{}'.", params.getParamMapForLogging());

        var url = subsonic.createUrl("getAvatar", params.getParamMap());

        return new MediaStream(url);
    }
}
