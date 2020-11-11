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
package net.beardbot.subsonic.client.api.search;

import com.github.tomakehurst.wiremock.WireMockServer;
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
public class SearchingApiTest {

    @Test
    void search2(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var result = subsonic(uri).searching().search2("Reol", SearchParams.create().artistCount(1).albumCount(1).songCount(1));

        assertThat(result.getArtists().size()).isOne();
        var artist = result.getArtists().get(0);
        assertThat(artist.getId()).isEqualTo("40");
        assertThat(artist.getName()).isEqualTo("REOL");

        assertThat(result.getAlbums().size()).isOne();
        var album = result.getAlbums().get(0);
        assertThat(album.getId()).isEqualTo("6258");
        assertThat(album.getParentId()).isEqualTo("40");
        assertThat(album.isDir()).isTrue();
        assertThat(album.getTitle()).isEqualTo("[2016] Σ");
        assertThat(album.getAlbum()).isEqualTo("Σ");
        assertThat(album.getArtist()).isEqualTo("REOL");
        assertThat(album.getYear()).isEqualTo(2016);
        assertThat(album.getCoverArtId()).isEqualTo("6258");
        assertThat(album.getPlayCount()).isEqualTo(352);
        assertThat(album.getCreated()).isEqualTo("2018-07-17T17:48:07.000");

        assertThat(result.getSongs().size()).isOne();
        var song = result.getSongs().get(0);
        assertThat(song.getId()).isEqualTo("6287");
        assertThat(song.getParentId()).isEqualTo("6258");
        assertThat(song.isDir()).isFalse();
        assertThat(song.getTitle()).isEqualTo("VIP KID");
        assertThat(song.getAlbum()).isEqualTo("Σ");
        assertThat(song.getArtist()).isEqualTo("REOL");
        assertThat(song.getTrack()).isEqualTo(1);
        assertThat(song.getYear()).isEqualTo(2016);
        assertThat(song.getCoverArtId()).isEqualTo("6258");
        assertThat(song.getSize()).isEqualTo(58501827);
        assertThat(song.getContentType()).isEqualTo("audio/flac");
        assertThat(song.getSuffix()).isEqualTo("flac");
        assertThat(song.getTranscodedContentType()).isEqualTo("audio/mpeg");
        assertThat(song.getTranscodedSuffix()).isEqualTo("mp3");
        assertThat(song.getDuration()).isEqualTo(232);
        assertThat(song.getBitRate()).isEqualTo(1999);
        assertThat(song.getPath()).isEqualTo("REOL/[2016] Σ/01. VIP KID.flac");
        assertThat(song.isVideo()).isFalse();
        assertThat(song.getPlayCount()).isEqualTo(31);
        assertThat(song.getAlbumId()).isEqualTo("507");
        assertThat(song.getArtistId()).isEqualTo("186");
        assertThat(song.getType()).isEqualTo(MediaType.MUSIC);
        assertThat(song.getCreated()).isEqualTo("2018-04-27T05:36:59.000");
    }

    @Test
    void search3(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var result = subsonic(uri).searching().search3("Reol",SearchParams.create().artistCount(1).albumCount(1).songCount(1));

        assertThat(result.getArtists().size()).isOne();
        var artist = result.getArtists().get(0);
        assertThat(artist.getId()).isEqualTo("186");
        assertThat(artist.getName()).isEqualTo("REOL");
        assertThat(artist.getCoverArtId()).isEqualTo("ar-186");
        assertThat(artist.getAlbumCount()).isEqualTo(2);

        assertThat(result.getAlbums().size()).isOne();
        var album = result.getAlbums().get(0);
        assertThat(album.getId()).isEqualTo("507");
        assertThat(album.getName()).isEqualTo("Σ");
        assertThat(album.getArtist()).isEqualTo("REOL");
        assertThat(album.getArtistId()).isEqualTo("186");
        assertThat(album.getCoverArtId()).isEqualTo("al-507");
        assertThat(album.getSongCount()).isEqualTo(13);
        assertThat(album.getDuration()).isEqualTo(2799);
        assertThat(album.getCreated()).isEqualTo("2018-04-27T05:36:59.000");
        assertThat(album.getYear()).isEqualTo(2016);

        assertThat(result.getSongs().size()).isOne();
        var song = result.getSongs().get(0);
        assertThat(song.getId()).isEqualTo("6287");
        assertThat(song.getParentId()).isEqualTo("6258");
        assertThat(song.isDir()).isFalse();
        assertThat(song.getTitle()).isEqualTo("VIP KID");
        assertThat(song.getAlbum()).isEqualTo("Σ");
        assertThat(song.getArtist()).isEqualTo("REOL");
        assertThat(song.getTrack()).isEqualTo(1);
        assertThat(song.getYear()).isEqualTo(2016);
        assertThat(song.getCoverArtId()).isEqualTo("6258");
        assertThat(song.getSize()).isEqualTo(58501827);
        assertThat(song.getContentType()).isEqualTo("audio/flac");
        assertThat(song.getSuffix()).isEqualTo("flac");
        assertThat(song.getTranscodedContentType()).isEqualTo("audio/mpeg");
        assertThat(song.getTranscodedSuffix()).isEqualTo("mp3");
        assertThat(song.getDuration()).isEqualTo(232);
        assertThat(song.getBitRate()).isEqualTo(1999);
        assertThat(song.getPath()).isEqualTo("REOL/[2016] Σ/01. VIP KID.flac");
        assertThat(song.isVideo()).isFalse();
        assertThat(song.getPlayCount()).isEqualTo(31);
        assertThat(song.getAlbumId()).isEqualTo("507");
        assertThat(song.getArtistId()).isEqualTo("186");
        assertThat(song.getType()).isEqualTo(MediaType.MUSIC);
        assertThat(song.getCreated()).isEqualTo("2018-04-27T05:36:59.000");
    }
}
