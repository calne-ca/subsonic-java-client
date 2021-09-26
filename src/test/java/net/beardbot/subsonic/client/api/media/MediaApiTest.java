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

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import java.io.IOException;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class MediaApiTest {
    @Test
    void stream(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var inputStream = subsonic(uri).media().stream("6303");
        assertThat(toByteArray(inputStream)).startsWith(0x49, 0x44, 0x33);
    }

    @Test
    void stream_error(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        assertSubsonicError(()->subsonic(uri).media().stream("999999999999"), subsonicError());
    }

    @Test
    void streamUrl(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) throws IOException {
        var url = subsonic(uri).media().streamUrl("6303");
        assertThat(url.getQuery()).contains("id=6303");
        assertThat(toByteArray(url.openStream())).startsWith(0x49, 0x44, 0x33);
    }

    @Test
    void download(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var inputStream = subsonic(uri).media().download("6303");
        assertThat(toByteArray(inputStream)).startsWith(0x49, 0x44, 0x33);
    }

    @Test
    void download_error(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        assertSubsonicError(()->subsonic(uri).media().download("999999999999"), subsonicError());
    }

    @Test
    void getCoverArt(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var inputStream = subsonic(uri).media().getCoverArt("20958");
        assertThat(toByteArray(inputStream)).containsSequence(0x4A, 0x46, 0x49, 0x46);
    }

    @Test
    void getCoverArt_withParams(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var inputStream = subsonic(uri).media().getCoverArt("20959", CoverArtParams.create().size(500));
        assertThat(toByteArray(inputStream)).containsSequence(0x4A, 0x46, 0x49, 0x46);
    }

    @Test
    void getCoverArt_error(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        assertSubsonicError(()->subsonic(uri).media().getCoverArt("999999999999"), subsonicError());
    }

    @Test
    void getCoverArtUrl(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) throws IOException {
        var url = subsonic(uri).media().getCoverArtUrl("20958");
        assertThat(toByteArray(url.openStream())).containsSequence(0x4A, 0x46, 0x49, 0x46);
    }

    @Test
    void getCoverArtUrl_withParams(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) throws IOException {
        var url = subsonic(uri).media().getCoverArtUrl("20959", CoverArtParams.create().size(500));
        assertThat(url.getQuery()).contains("size=500");
        assertThat(toByteArray(url.openStream())).containsSequence(0x4A, 0x46, 0x49, 0x46);
    }

    @Test
    void getAvatar(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var inputStream = subsonic(uri).media().getAvatar();
        assertThat(toByteArray(inputStream)).containsSequence(0x4A, 0x46, 0x49, 0x46);
    }

    //TODO: FIND A SOLUTION FOR THIS
    @Tag("fails-on-github")
    @Test
    void getAvatar_error(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        assertSubsonicError(()->subsonic(uri).media().getAvatar("999999999999"), subsonicError());
    }
}
