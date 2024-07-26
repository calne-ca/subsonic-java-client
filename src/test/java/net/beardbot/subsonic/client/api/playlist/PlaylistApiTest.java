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
package net.beardbot.subsonic.client.api.playlist;

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
public class PlaylistApiTest {

    @Test
    void getPlaylists(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var result = subsonic(uri).playlists().getPlaylists();
        assertThat(result).hasSize(2);

        var firstPlaylist = result.get(0);
        assertThat(firstPlaylist.getId()).isEqualTo("32");
        assertThat(firstPlaylist.getName()).isEqualTo("Pop");
        assertThat(firstPlaylist.getComment()).isEqualTo("Auto-imported from Pop.m3u");
        assertThat(firstPlaylist.getOwner()).isEqualTo("admin");
        assertThat(firstPlaylist.isPublic()).isTrue();
        assertThat(firstPlaylist.getSongCount()).isEqualTo(202);
        assertThat(firstPlaylist.getDuration()).isEqualTo(46763);
        assertThat(firstPlaylist.getCreated()).isEqualTo("2019-03-02T18:52:03.662");
        assertThat(firstPlaylist.getChanged()).isEqualTo("2020-11-14T03:00:31.549");
        assertThat(firstPlaylist.getCoverArtId()).isEqualTo("pl-32");
    }

    @Test
    void getPlaylist(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var playlist = subsonic(uri).playlists().getPlaylist("32");

        assertThat(playlist.getId()).isEqualTo("32");
        assertThat(playlist.getName()).isEqualTo("Pop");
        assertThat(playlist.getComment()).isEqualTo("Auto-imported from Pop.m3u");
        assertThat(playlist.getOwner()).isEqualTo("admin");
        assertThat(playlist.isPublic()).isTrue();
        assertThat(playlist.getSongCount()).isEqualTo(202);
        assertThat(playlist.getDuration()).isEqualTo(46763);
        assertThat(playlist.getCreated()).isEqualTo("2019-03-02T18:52:03.662");
        assertThat(playlist.getChanged()).isEqualTo("2020-11-14T03:00:31.549");
        assertThat(playlist.getCoverArtId()).isEqualTo("pl-32");

        var songs = playlist.getEntries();
        assertThat(songs).hasSize(2);

        assertThat(songs.get(0).getId()).isEqualTo("20032");
        assertThat(songs.get(0).getParentId()).isEqualTo("20033");
        assertThat(songs.get(0).isDir()).isFalse();
        assertThat(songs.get(0).getTitle()).isEqualTo("Can't Help Falling in Love");
        assertThat(songs.get(0).getAlbum()).isEqualTo("Lilo and Stitch OST");
        assertThat(songs.get(0).getArtist()).isEqualTo("A*Teens");
        assertThat(songs.get(0).getTrack()).isEqualTo(9);
        assertThat(songs.get(0).getYear()).isEqualTo(2002);
        assertThat(songs.get(0).getGenre()).isEqualTo("Soundtrack");
        assertThat(songs.get(0).getCoverArtId()).isEqualTo("20033");
        assertThat(songs.get(0).getSize()).isEqualTo(5468839);
        assertThat(songs.get(0).getContentType()).isEqualTo("audio/mpeg");
        assertThat(songs.get(0).getSuffix()).isEqualTo("mp3");
        assertThat(songs.get(0).getDuration()).isEqualTo(186);
        assertThat(songs.get(0).getBitRate()).isEqualTo(224);
        assertThat(songs.get(0).getPath()).isEqualTo("A-Teens/Can't Help Falling in Love.mp3");
        assertThat(songs.get(0).isVideo()).isFalse();
        assertThat(songs.get(0).getPlayCount()).isEqualTo(26);
        assertThat(songs.get(0).getCreated()).isEqualTo("2020-01-06T22:33:30.000");
        assertThat(songs.get(0).getAlbumId()).isEqualTo("997");
        assertThat(songs.get(0).getArtistId()).isEqualTo("432");
        assertThat(songs.get(0).getType()).isEqualTo(MediaType.MUSIC);
    }

    @Test
    void createPlaylist(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).playlists().createPlaylist("testPlaylist","1","2","3");
    }

    @Test
    void updatePlaylist(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).playlists().updatePlaylist("1", UpdatePlaylistParams.create().addSong("11").addSong("12")
                .name("testPlaylist2").comment("testComment").removeSong(1).removeSong(2).makePrivate());
    }

    @Test
    void deletePlaylist(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).playlists().deletePlaylist("1");
    }
}
