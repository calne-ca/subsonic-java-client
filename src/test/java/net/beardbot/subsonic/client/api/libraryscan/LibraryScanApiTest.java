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
package net.beardbot.subsonic.client.api.libraryscan;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.subsonic.restapi.Error;
import org.subsonic.restapi.ErrorCode;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static net.beardbot.subsonic.client.api.TestUtil.assertSubsonicError;
import static net.beardbot.subsonic.client.api.TestUtil.subsonic;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        WiremockResolver.class,
        WiremockUriResolver.class
})
public class LibraryScanApiTest {

    @Test
    void getScanStatus(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var scanStatus = subsonic(uri).libraryScan().getScanStatus();
        assertThat(scanStatus.getCount()).isEqualTo(8732);
        assertThat(scanStatus.isScanning()).isTrue();
    }

    @Test
    void startScan(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var subsonic = subsonic(uri);
        subsonic.getPreferences().setUsername("testAdmin");

        var scanStatus = subsonic.libraryScan().startScan();
        assertThat(scanStatus.getCount()).isEqualTo(9562);
        assertThat(scanStatus.isScanning()).isTrue();
    }

    @Test
    void startScan_error(@WiremockResolver.Wiremock WireMockServer server, @WiremockUri String uri) {
        var error = new Error();
        error.setCode(ErrorCode.USER_NOT_AUTHORIZED);
        error.setMessage("User not authorized.");

        assertSubsonicError(()->subsonic(uri).libraryScan().startScan(), error);
    }
}
