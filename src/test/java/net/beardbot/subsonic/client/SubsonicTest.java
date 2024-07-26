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
package net.beardbot.subsonic.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import net.beardbot.subsonic.client.base.SubsonicIncompatibilityException;
import net.beardbot.subsonic.client.base.Version;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;

import java.util.Collections;
import java.util.Map;

import static net.beardbot.subsonic.client.api.TestUtil.subsonic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
class SubsonicTest {
    @Test
    void testConnection_error30_negotiatesApiVersion(@WiremockResolver.Wiremock WireMockServer server, @WiremockUriResolver.WiremockUri String uri) {
        var subsonic = subsonic(uri);
        subsonic.setApiVersion(Version.of("1.16.0"));   // Magic number to match error stub

        assertThat(subsonic.getApiVersion().getVersionString()).isEqualTo("1.16.0");
        assertThat(subsonic.testConnection()).isTrue();
        assertThat(subsonic.getApiVersion().getVersionString()).isEqualTo("1.15.0");
    }

    @Test
    void testConnection_error30_serverVersionTooLow_throwsIncompatibilityException(@WiremockResolver.Wiremock WireMockServer server, @WiremockUriResolver.WiremockUri String uri) {
        var subsonic = subsonic(uri);
        subsonic.setApiVersion(Version.of("1.17.0"));   // Magic number to match error stub

        assertThatCode(subsonic::testConnection).isExactlyInstanceOf(SubsonicIncompatibilityException.class);
    }

    @Test
    void testConnection_error20_throwsIncompatibilityException(@WiremockResolver.Wiremock WireMockServer server, @WiremockUriResolver.WiremockUri String uri) {
        var subsonic = subsonic(uri);
        subsonic.setApiVersion(Version.of("0.1.0"));   // Magic number to match error stub

        assertThatCode(subsonic::testConnection).isExactlyInstanceOf(SubsonicIncompatibilityException.class);
    }

    @Test
    void testConnection_otherSubsonicError_fails(@WiremockResolver.Wiremock WireMockServer server, @WiremockUriResolver.WiremockUri String uri) {
        var subsonic = subsonic(uri);
        subsonic.getPreferences().setUsername("invalidUserName");
        var version = subsonic.getApiVersion();

        assertThat(subsonic.getApiVersion()).isEqualTo(version);
        assertThat(subsonic.testConnection()).isFalse();
        assertThat(subsonic.getApiVersion()).isEqualTo(version);
    }

    @Test
    void testConnection_otherNonSubsonicError_fails(@WiremockResolver.Wiremock WireMockServer server, @WiremockUriResolver.WiremockUri String uri) {
        var subsonic = subsonic("invalidUrl");
        var version = subsonic.getApiVersion();

        assertThat(subsonic.getApiVersion()).isEqualTo(version);
        assertThat(subsonic.testConnection()).isFalse();
        assertThat(subsonic.getApiVersion()).isEqualTo(version);
    }

    @Test
    void createUrl() {
        var subsonic = subsonic("http://testhost:123/prefix");
        var preferences = subsonic.getPreferences();

        var params = Map.of("testParam1",Collections.singletonList("testValue1"),"testParam2",Collections.singletonList("testValue2"));
        var url = subsonic.createUrl("test", params);

        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getHost()).isEqualTo("testhost");
        assertThat(url.getPort()).isEqualTo(123);
        assertThat(url.getPath()).isEqualTo("/prefix/rest/test");
        assertThat(url).hasParameter("u",preferences.getUsername());
        assertThat(url).hasParameter("s",preferences.getAuthentication().getSalt());
        assertThat(url).hasParameter("t",preferences.getAuthentication().getToken());
        assertThat(url).hasParameter("v",subsonic.getApiVersion().getVersionString());
        assertThat(url).hasParameter("c",preferences.getClientName());
        assertThat(url).hasParameter("f","xml");
        assertThat(url).hasParameter("testParam1","testValue1");
        assertThat(url).hasParameter("testParam2","testValue2");
    }

    @Test
    void createUrl_trailingSlash() {
        var subsonic = subsonic("http://testhost:123/prefix/");
        var preferences = subsonic.getPreferences();

        var url = subsonic.createUrl("test", Collections.emptyMap());

        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getHost()).isEqualTo("testhost");
        assertThat(url.getPort()).isEqualTo(123);
        assertThat(url.getPath()).isEqualTo("/prefix/rest/test");
    }
}