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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.subsonic.restapi.ResponseStatus;

import static net.beardbot.subsonic.client.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemServiceTest {

    @Mock
    private SystemClient systemClient;

    private SystemService systemService;

    @BeforeEach
    void setUp() {
        systemService = new SystemService(systemClient);
    }

    @Test
    void ping() {
        when(systemClient.ping()).thenReturn(pingResponse());

        systemService.ping();
    }

    @Test
    void ping_error() {
        var pingResponse = pingResponse();
        pingResponse.setError(subsonicError());
        pingResponse.setStatus(ResponseStatus.FAILED);

        when(systemClient.ping()).thenReturn(pingResponse);

        assertSubsonicError(systemService::ping, pingResponse.getError());
    }

    @Test
    void getLicense() {
        var licenseResponse = licenseResponse();
        when(systemClient.getLicense()).thenReturn(licenseResponse);

        var license = systemService.getLicense();
        assertThat(license).isEqualTo(licenseResponse.getLicense());
    }

    @Test
    void getLicense_error() {
        var licenseResponse = licenseResponse();
        licenseResponse.setError(subsonicError());
        licenseResponse.setStatus(ResponseStatus.FAILED);

        when(systemClient.getLicense()).thenReturn(licenseResponse);

        assertSubsonicError(systemService::getLicense, licenseResponse.getError());
    }
}