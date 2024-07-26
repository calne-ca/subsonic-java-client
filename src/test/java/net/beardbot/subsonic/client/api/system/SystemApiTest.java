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
package net.beardbot.subsonic.client.api.system;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static net.beardbot.subsonic.client.api.TestUtil.subsonic;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class SystemApiTest {

    @Test
    void ping(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        subsonic(uri).system().ping();
    }

    @Test
    void getLicense(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var license = subsonic(uri).system().getLicense();
        System.out.println(license.getLicenseExpires());
        System.out.println(license.getTrialExpires());
        assertThat(license.getEmail()).isEqualTo("airsonic@github.com");
        assertThat(license.getLicenseExpires()).isNotNull();
        assertThat(license.getTrialExpires()).isNotNull();
    }
}
