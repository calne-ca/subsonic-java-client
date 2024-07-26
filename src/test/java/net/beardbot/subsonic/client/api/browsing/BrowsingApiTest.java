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
package net.beardbot.subsonic.client.api.browsing;

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
public class BrowsingApiTest {

    @Test
    void getMusicFolders(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var musicFolders = subsonic(uri).browsing().getMusicFolders();
        assertThat(musicFolders).hasSize(4);

        assertThat(musicFolders.get(0).getId()).isEqualTo(2);
        assertThat(musicFolders.get(0).getName()).isEqualTo("Anime");

        assertThat(musicFolders.get(1).getId()).isEqualTo(3);
        assertThat(musicFolders.get(1).getName()).isEqualTo("K-Pop");
    }

    @Test
    void getIndexes(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var indexes = subsonic(uri).browsing().getIndexes();
        assertThat(indexes).hasSize(2);

        var firstIndex = indexes.get(0);
        assertThat(firstIndex.getName()).isEqualTo("A");

        var artists = firstIndex.getArtists();
        assertThat(artists).hasSize(2);
        assertThat(artists.get(1).getId()).isEqualTo("15453");
        assertThat(artists.get(1).getName()).isEqualTo("Aimer");
    }

    @Test
    void getMusicDirectory(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var musicDirectory = subsonic(uri).browsing().getMusicDirectory("14584");
        assertThat(musicDirectory.getId()).isEqualTo("14584");
        assertThat(musicDirectory.getName()).isEqualTo("BLACKPINK");
        assertThat(musicDirectory.getPlayCount()).isEqualTo(12);

        var songs = musicDirectory.getchildren();
        assertThat(songs).hasSize(2);

        var song = songs.get(1);
        assertThat(song.getId()).isEqualTo("14586");
        assertThat(song.getParentId()).isEqualTo("14584");
        assertThat(song.isDir()).isTrue();
        assertThat(song.getTitle()).isEqualTo("[2018] SQUARE UP");
        assertThat(song.getAlbum()).isEqualTo("SQUARE UP");
        assertThat(song.getArtist()).isEqualTo("BLACKPINK");
        assertThat(song.getYear()).isEqualTo(2018);
        assertThat(song.getGenre()).isEqualTo("K-Pop");
        assertThat(song.getCoverArtId()).isEqualTo("14586");
        assertThat(song.getPlayCount()).isEqualTo(294);
        assertThat(song.getCreated()).isEqualTo("2018-07-24T16:59:55.000");
    }

    @Test
    void getGenres(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var genres = subsonic(uri).browsing().getGenres();
        assertThat(genres).hasSize(3);

        var secondGenre = genres.get(1);
        assertThat(secondGenre.getSongCount()).isEqualTo(97);
        assertThat(secondGenre.getAlbumCount()).isEqualTo(7);
        assertThat(secondGenre.getName()).isEqualTo("Rap");
    }

    @Test
    void getArtists(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var artistIndexes = subsonic(uri).browsing().getArtists();
        assertThat(artistIndexes).hasSize(2);

        var secondIndex = artistIndexes.get(1);
        assertThat(secondIndex.getName()).isEqualTo("#");

        var firstArtist = secondIndex.getArtists().get(0);
        assertThat(firstArtist.getId()).isEqualTo("301");
        assertThat(firstArtist.getName()).isEqualTo("µ's");
        assertThat(firstArtist.getCoverArtId()).isEqualTo("ar-301");
        assertThat(firstArtist.getAlbumCount()).isEqualTo(2);
    }

    @Test
    void getArtist(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var artist = subsonic(uri).browsing().getArtist("154");
        assertThat(artist.getId()).isEqualTo("154");
        assertThat(artist.getName()).isEqualTo("Scythe of Luna");
        assertThat(artist.getCoverArtId()).isEqualTo("ar-154");
        assertThat(artist.getAlbumCount()).isEqualTo(2);

        var albums = artist.getAlbums();
        assertThat(albums).hasSize(2);

        var firstAlbum = albums.get(0);
        assertThat(firstAlbum.getId()).isEqualTo("425");
        assertThat(firstAlbum.getName()).isEqualTo("Power Flower");
        assertThat(firstAlbum.getArtist()).isEqualTo("Scythe of Luna");
        assertThat(firstAlbum.getArtistId()).isEqualTo("154");
        assertThat(firstAlbum.getCoverArtId()).isEqualTo("al-425");
        assertThat(firstAlbum.getSongCount()).isEqualTo(15);
        assertThat(firstAlbum.getDuration()).isEqualTo(4031);
        assertThat(firstAlbum.getCreated()).isEqualTo("2018-05-20T15:02:36.000");
        assertThat(firstAlbum.getYear()).isEqualTo(2016);
    }

    @Test
    void getAlbum(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var album = subsonic(uri).browsing().getAlbum("1860");
        assertThat(album.getId()).isEqualTo("1860");
        assertThat(album.getArtist()).isEqualTo("BABYMETAL");
        assertThat(album.getArtistId()).isEqualTo("692");
        assertThat(album.getCoverArtId()).isEqualTo("al-1860");
        assertThat(album.getSongCount()).isEqualTo(13);
        assertThat(album.getDuration()).isEqualTo(3343);
        assertThat(album.getCreated()).isEqualTo("2020-03-03T22:04:13.000");
        assertThat(album.getYear()).isEqualTo(2014);
        assertThat(album.getGenre()).isEqualTo("Metal");

        var secondSong = album.getSongs().get(1);
        assertThat(secondSong.getId()).isEqualTo("20955");
        assertThat(secondSong.getParentId()).isEqualTo("20953");
        assertThat(secondSong.isDir()).isFalse();
        assertThat(secondSong.getTitle()).isEqualTo("メギツネ");
        assertThat(secondSong.getAlbum()).isEqualTo("BABYMETAL");
        assertThat(secondSong.getArtist()).isEqualTo("BABYMETAL");
        assertThat(secondSong.getTrack()).isEqualTo(2);
        assertThat(secondSong.getYear()).isEqualTo(2014);
        assertThat(secondSong.getGenre()).isEqualTo("Metal");
        assertThat(secondSong.getCoverArtId()).isEqualTo("20953");
        assertThat(secondSong.getSize()).isEqualTo(35280358);
        assertThat(secondSong.getContentType()).isEqualTo("audio/flac");
        assertThat(secondSong.getSuffix()).isEqualTo("flac");
        assertThat(secondSong.getTranscodedContentType()).isEqualTo("audio/mpeg");
        assertThat(secondSong.getTranscodedSuffix()).isEqualTo("mp3");
        assertThat(secondSong.getDuration()).isEqualTo(249);
        assertThat(secondSong.getBitRate()).isEqualTo(1129);
        assertThat(secondSong.getPath()).isEqualTo("Babymetal/[2014] BABYMETAL/02. メギツネ.flac");
        assertThat(secondSong.isVideo()).isFalse();
        assertThat(secondSong.getPlayCount()).isEqualTo(12);
        assertThat(secondSong.getDiscNumber()).isEqualTo(1);
        assertThat(secondSong.getCreated()).isEqualTo("2020-03-03T22:02:31.000");
        assertThat(secondSong.getAlbumId()).isEqualTo("1860");
        assertThat(secondSong.getArtistId()).isEqualTo("692");
        assertThat(secondSong.getType()).isEqualTo(MediaType.MUSIC);
    }

    @Test
    void getSong(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var song = subsonic(uri).browsing().getSong("5551");
        assertThat(song.getId()).isEqualTo("5551");
        assertThat(song.getParentId()).isEqualTo("5547");
        assertThat(song.isDir()).isFalse();
        assertThat(song.getTitle()).isEqualTo("Crystal Dream");
        assertThat(song.getAlbum()).isEqualTo("Power Flower");
        assertThat(song.getArtist()).isEqualTo("Scythe of Luna");
        assertThat(song.getTrack()).isEqualTo(1);
        assertThat(song.getYear()).isEqualTo(2016);
        assertThat(song.getCoverArtId()).isEqualTo("5547");
        assertThat(song.getSize()).isEqualTo(41690068);
        assertThat(song.getContentType()).isEqualTo("audio/flac");
        assertThat(song.getSuffix()).isEqualTo("flac");
        assertThat(song.getTranscodedContentType()).isEqualTo("audio/mpeg");
        assertThat(song.getTranscodedSuffix()).isEqualTo("mp3");
        assertThat(song.getDuration()).isEqualTo(333);
        assertThat(song.getBitRate()).isEqualTo(998);
        assertThat(song.getPath()).isEqualTo("Vocaloid/Scythe of Luna/[2016] Power Flower/01. Crystal Dream.flac");
        assertThat(song.isVideo()).isFalse();
        assertThat(song.getPlayCount()).isEqualTo(27);
        assertThat(song.getCreated()).isEqualTo("2018-05-20T15:02:36.000");
        assertThat(song.getAlbumId()).isEqualTo("425");
        assertThat(song.getArtistId()).isEqualTo("154");
        assertThat(song.getType()).isEqualTo(MediaType.MUSIC);
    }
}
