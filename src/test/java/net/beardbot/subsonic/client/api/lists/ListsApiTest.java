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
package net.beardbot.subsonic.client.api.lists;

import com.github.tomakehurst.wiremock.WireMockServer;
import net.beardbot.subsonic.client.api.playlist.UpdatePlaylistParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.subsonic.restapi.MediaType;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static net.beardbot.subsonic.client.api.TestUtil.subsonic;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class ListsApiTest {

    @Test
    void getNowPlaying(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var nowPlayingEntries = subsonic(uri).lists().getNowPlaying();
        assertThat(nowPlayingEntries).hasSize(1);

        var nowPlayingEntry = nowPlayingEntries.get(0);
        assertThat(nowPlayingEntry.getId()).isEqualTo("5551");
        assertThat(nowPlayingEntry.getParentId()).isEqualTo("5547");
        assertThat(nowPlayingEntry.isDir()).isFalse();
        assertThat(nowPlayingEntry.getTitle()).isEqualTo("Crystal Dream");
        assertThat(nowPlayingEntry.getAlbum()).isEqualTo("Power Flower");
        assertThat(nowPlayingEntry.getArtist()).isEqualTo("Scythe of Luna");
        assertThat(nowPlayingEntry.getTrack()).isEqualTo(1);
        assertThat(nowPlayingEntry.getYear()).isEqualTo(2016);
        assertThat(nowPlayingEntry.getCoverArtId()).isEqualTo("5547");
        assertThat(nowPlayingEntry.getSize()).isEqualTo(41690068);
        assertThat(nowPlayingEntry.getContentType()).isEqualTo("audio/flac");
        assertThat(nowPlayingEntry.getSuffix()).isEqualTo("flac");
        assertThat(nowPlayingEntry.getSuffix()).isEqualTo("flac");
        assertThat(nowPlayingEntry.getTranscodedContentType()).isEqualTo("audio/mpeg");
        assertThat(nowPlayingEntry.getTranscodedSuffix()).isEqualTo("mp3");
        assertThat(nowPlayingEntry.getDuration()).isEqualTo(333);
        assertThat(nowPlayingEntry.getBitRate()).isEqualTo(998);
        assertThat(nowPlayingEntry.getPath()).isEqualTo("Vocaloid/Scythe of Luna/[2016] Power Flower/01. Crystal Dream.flac");
        assertThat(nowPlayingEntry.isVideo()).isFalse();
        assertThat(nowPlayingEntry.getPlayCount()).isEqualTo(29L);
        assertThat(nowPlayingEntry.getCreated()).isEqualTo("2018-05-20T15:02:36.000");
        assertThat(nowPlayingEntry.getAlbumId()).isEqualTo("425");
        assertThat(nowPlayingEntry.getArtistId()).isEqualTo("154");
        assertThat(nowPlayingEntry.getType()).isEqualTo(MediaType.MUSIC);
        assertThat(nowPlayingEntry.getUsername()).isEqualTo("testUser");
        assertThat(nowPlayingEntry.getMinutesAgo()).isEqualTo(0);
        assertThat(nowPlayingEntry.getPlayerId()).isEqualTo(27);
        assertThat(nowPlayingEntry.getPlayerName()).isEqualTo("testPlayer");
    }
}
