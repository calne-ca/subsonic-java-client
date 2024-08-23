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
package net.beardbot.subsonic.client.api.lists;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.subsonic.restapi.MediaType;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import java.time.LocalDateTime;

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

    @Test
    void getStarred(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var starred = subsonic(uri).lists().getStarred();

        var starredArtists = starred.getArtists();
        assertThat(starredArtists).hasSize(2);
        var starredArtist1 = starredArtists.get(0);
        assertThat(starredArtist1.getId()).isEqualTo("10001");
        assertThat(starredArtist1.getName()).isEqualTo("Artist 1");
        assertThat(starredArtist1.getStarred()).isEqualTo(LocalDateTime.parse("2012-04-05T18:40:08"));

        var starredAlbums = starred.getAlbums();
        assertThat(starredAlbums).hasSize(2);
        var starredAlbum1 = starredAlbums.get(0);
        assertThat(starredAlbum1.getId()).isEqualTo("11001");
        assertThat(starredAlbum1.getParentId()).isEqualTo("13001");
        assertThat(starredAlbum1.getAlbum()).isEqualTo("Album 1");
        assertThat(starredAlbum1.getTitle()).isEqualTo("Album 1 Title");
        assertThat(starredAlbum1.getArtist()).isEqualTo("Album 1 Artist");
        assertThat(starredAlbum1.isDir()).isTrue();
        assertThat(starredAlbum1.getCoverArtId()).isEqualTo("15001");
        assertThat(starredAlbum1.getCreated().toString()).isEqualTo("2011-02-26T10:45:30");
        assertThat(starredAlbum1.getStarred().toString()).isEqualTo("2024-07-18T22:20:25.220976486");

        var starredSongs = starred.getSongs();
        assertThat(starredSongs).hasSize(2);
        var starredSong1 = starredSongs.get(0);
        assertThat(starredSong1.getId()).isEqualTo("12001");
        assertThat(starredSong1.getParentId()).isEqualTo("14001");
        assertThat(starredSong1.getAlbum()).isEqualTo("Song 1 Album");
        assertThat(starredSong1.getTitle()).isEqualTo("Song 1");
        assertThat(starredSong1.getArtist()).isEqualTo("Song 1 Artist");
        assertThat(starredSong1.getAlbumId()).isEqualTo("20001");
        assertThat(starredSong1.getArtistId()).isEqualTo("21001");
        assertThat(starredSong1.isDir()).isFalse();
        assertThat(starredSong1.isVideo()).isFalse();
        assertThat(starredSong1.getCoverArtId()).isEqualTo("16001");
        assertThat(starredSong1.getDuration()).isEqualTo(302);
        assertThat(starredSong1.getBitRate()).isEqualTo(320);
        assertThat(starredSong1.getSize()).isEqualTo(12087601);
        assertThat(starredSong1.getTrack()).isEqualTo(2);
        assertThat(starredSong1.getYear()).isEqualTo(2010);
        assertThat(starredSong1.getSuffix()).isEqualTo("mp3");
        assertThat(starredSong1.getContentType()).isEqualTo("audio/mpeg");
        assertThat(starredSong1.getPath()).isEqualTo("test/song1.mp3");
        assertThat(starredSong1.getType()).isEqualTo(MediaType.MUSIC);
        assertThat(starredSong1.getGenre()).isEqualTo("Song 1 Genre");
        assertThat(starredSong1.getCreated().toString()).isEqualTo("2010-09-27T20:52:23");
        assertThat(starredSong1.getStarred().toString()).isEqualTo("2024-07-18T22:20:25.220976486");
    }

    @Test
    void getStarred2(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var starred = subsonic(uri).lists().getStarred2();

        var starredArtists = starred.getArtists();
        assertThat(starredArtists).hasSize(2);
        var starredArtist1 = starredArtists.get(0);
        assertThat(starredArtist1.getId()).isEqualTo("10001");
        assertThat(starredArtist1.getName()).isEqualTo("Artist 1");
        assertThat(starredArtist1.getAlbumCount()).isEqualTo(1);
        assertThat(starredArtist1.getCoverArtId()).isEqualTo("cover-1");
        assertThat(starredArtist1.getStarred()).isEqualTo(LocalDateTime.parse("2012-04-05T18:40:08"));

        var starredAlbums = starred.getAlbums();
        assertThat(starredAlbums).hasSize(2);
        var starredAlbum1 = starredAlbums.get(0);
        assertThat(starredAlbum1.getId()).isEqualTo("11001");
        assertThat(starredAlbum1.getName()).isEqualTo("Album 1");
        assertThat(starredAlbum1.getArtistId()).isEqualTo("13001");
        assertThat(starredAlbum1.getArtist()).isEqualTo("Album 1 Artist");
        assertThat(starredAlbum1.getCoverArtId()).isEqualTo("cover-1");
        assertThat(starredAlbum1.getSongCount()).isEqualTo(12);
        assertThat(starredAlbum1.getDuration()).isEqualTo(2459);
        assertThat(starredAlbum1.getCreated().toString()).isEqualTo("2024-07-18T22:20:25.220976486");
        assertThat(starredAlbum1.getStarred().toString()).isEqualTo("2012-04-05T18:40:02");

        var starredSongs = starred.getSongs();
        assertThat(starredSongs).hasSize(2);
        var starredSong1 = starredSongs.get(0);
        assertThat(starredSong1.getId()).isEqualTo("12001");
        assertThat(starredSong1.getParentId()).isEqualTo("14001");
        assertThat(starredSong1.getAlbum()).isEqualTo("Song 1 Album");
        assertThat(starredSong1.getTitle()).isEqualTo("Song 1");
        assertThat(starredSong1.getArtist()).isEqualTo("Song 1 Artist");
        assertThat(starredSong1.getAlbumId()).isEqualTo("20001");
        assertThat(starredSong1.getArtistId()).isEqualTo("21001");
        assertThat(starredSong1.isDir()).isFalse();
        assertThat(starredSong1.isVideo()).isFalse();
        assertThat(starredSong1.getCoverArtId()).isEqualTo("16001");
        assertThat(starredSong1.getDuration()).isEqualTo(302);
        assertThat(starredSong1.getBitRate()).isEqualTo(320);
        assertThat(starredSong1.getSize()).isEqualTo(12087601);
        assertThat(starredSong1.getTrack()).isEqualTo(2);
        assertThat(starredSong1.getYear()).isEqualTo(2010);
        assertThat(starredSong1.getSuffix()).isEqualTo("mp3");
        assertThat(starredSong1.getContentType()).isEqualTo("audio/mpeg");
        assertThat(starredSong1.getPath()).isEqualTo("test/song1.mp3");
        assertThat(starredSong1.getType()).isEqualTo(MediaType.MUSIC);
        assertThat(starredSong1.getGenre()).isEqualTo("Song 1 Genre");
        assertThat(starredSong1.getCreated().toString()).isEqualTo("2010-09-27T20:52:23");
        assertThat(starredSong1.getStarred().toString()).isEqualTo("2024-07-18T22:20:25.220976486");
    }

    @Test
    void getAlbumList(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var albumList = subsonic(uri).lists().getAlbumList();

        var albums = albumList.getAlbums();
        assertThat(albums).hasSize(2);

        assertThat(albums.get(0).getId()).isEqualTo("id1");
        assertThat(albums.get(0).getAlbum()).isEqualTo("al1-name");
        assertThat(albums.get(0).getAlbumId()).isEqualTo("al1-id");
        assertThat(albums.get(0).getArtist()).isEqualTo("ar1-name");
        assertThat(albums.get(0).getArtistId()).isEqualTo("ar1-id");
        assertThat(albums.get(0).getType()).isEqualTo(MediaType.MUSIC);
        assertThat(albums.get(0).getContentType()).isEqualTo("ct1");
        assertThat(albums.get(0).getSuffix()).isEqualTo("suf1");
        assertThat(albums.get(0).getUserRating()).isEqualTo(3);
        assertThat(albums.get(0).getGenre()).isEqualTo("gen1");
        assertThat(albums.get(0).getYear()).isEqualTo(1234);
        assertThat(albums.get(0).getPath()).isEqualTo("path1");
        assertThat(albums.get(0).getSize()).isEqualTo(1234);
        assertThat(albums.get(0).getTrack()).isEqualTo(1234);
        assertThat(albums.get(0).getBitRate()).isEqualTo(1234);
        assertThat(albums.get(0).getDuration()).isEqualTo(1234);
        assertThat(albums.get(0).getCreated()).isEqualTo("2018-07-17T17:48:07.000");
        assertThat(albums.get(0).getCoverArtId()).isEqualTo("cov1");
        assertThat(albums.get(0).getTitle()).isEqualTo("title1");
        assertThat(albums.get(0).getParentId()).isEqualTo("parent1");
        assertThat(albums.get(0).getStarred()).isEqualTo("2018-07-17T17:48:07.000");
        assertThat(albums.get(0).getPlayCount()).isEqualTo(1234);
        assertThat(albums.get(0).getTranscodedSuffix()).isEqualTo("tcs");
        assertThat(albums.get(0).getTranscodedContentType()).isEqualTo("tcct");
        assertThat(albums.get(0).getDiscNumber()).isEqualTo(1234);
        assertThat(albums.get(0).getAverageRating()).isEqualTo(3.5);
        assertThat(albums.get(0).getBookmarkPosition()).isEqualTo(1234);
        assertThat(albums.get(0).getOriginalHeight()).isEqualTo(1234);
        assertThat(albums.get(0).getOriginalWidth()).isEqualTo(1234);
        assertThat(albums.get(0).isDir()).isTrue();
        assertThat(albums.get(0).isVideo()).isFalse();
    }

    @Test
    void getAlbumList2(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var albumList = subsonic(uri).lists().getAlbumList2();

        var albums = albumList.getAlbums();
        assertThat(albums).hasSize(2);

        assertThat(albums.get(0).getId()).isEqualTo("id1");
        assertThat(albums.get(0).getName()).isEqualTo("al1-name");
        assertThat(albums.get(0).getSongCount()).isEqualTo(1234);
        assertThat(albums.get(0).getArtist()).isEqualTo("ar1-name");
        assertThat(albums.get(0).getArtistId()).isEqualTo("ar1-id");
        assertThat(albums.get(0).getGenre()).isEqualTo("gen1");
        assertThat(albums.get(0).getYear()).isEqualTo(1234);
        assertThat(albums.get(0).getDuration()).isEqualTo(1234);
        assertThat(albums.get(0).getCreated()).isEqualTo("2018-07-17T17:48:07.000");
        assertThat(albums.get(0).getCoverArtId()).isEqualTo("cov1");
        assertThat(albums.get(0).getStarred()).isEqualTo("2018-07-17T17:48:07.000");
        assertThat(albums.get(0).getPlayCount()).isEqualTo(1234);
    }
}
