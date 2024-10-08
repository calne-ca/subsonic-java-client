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
package net.beardbot.subsonic.client.api.annotation;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static net.beardbot.subsonic.client.api.TestUtil.subsonic;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class AnnotationsApiTest {

    @Test
    void scrobble(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).annotation().scrobble("1", System.currentTimeMillis());
    }

    @Test
    void star(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).annotation().star("1");
        subsonic(uri).annotation().starAlbumID3("1");
        subsonic(uri).annotation().starArtistID3("1");
    }

    @Test
    void unstar(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).annotation().unstar("1");
        subsonic(uri).annotation().unstarAlbumID3("1");
        subsonic(uri).annotation().unstarArtistID3("1");
    }

    @Test
    void setRating(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).annotation().setRating("1", 3);
    }
}
