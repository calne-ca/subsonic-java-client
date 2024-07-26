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
package net.beardbot.subsonic.client.api.libraryscan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static net.beardbot.subsonic.client.api.TestUtil.scanStatusResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryScanServiceTest {
    @Mock
    private LibraryScanClient libraryScanClient;

    private LibraryScanService libraryScanService;

    @BeforeEach
    void setUp() {
        libraryScanService = new LibraryScanService(libraryScanClient);
    }

    @Test
    void getScanStatus() {
        var scanStatusResponse = scanStatusResponse();

        when(libraryScanClient.getScanStatus()).thenReturn(scanStatusResponse);

        var scanStatus = libraryScanService.getScanStatus();
        assertThat(scanStatus).isEqualTo(scanStatusResponse.getScanStatus());
    }

    @Test
    void startScan() {
        var scanStatusResponse = scanStatusResponse();

        when(libraryScanClient.startScan()).thenReturn(scanStatusResponse);

        var scanStatus = libraryScanService.startScan();
        assertThat(scanStatus).isEqualTo(scanStatusResponse.getScanStatus());
    }
}