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
package net.beardbot.subsonic.client.integration.airsonic;

import net.beardbot.subsonic.client.integration.base.CommonIntegrationTestCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Tag("airsonic")
@Testcontainers
public class AirsonicIntegrationTest {
    @Container
    private final AirsonicContainer airsonicContainer = new AirsonicContainer();

    @BeforeEach
    void setUp() {
        assertThat(airsonicContainer.isRunning()).isTrue();
    }

    @Test
    public void ping() {
        CommonIntegrationTestCode.ping(airsonicContainer);
    }

    @Test
    public void getLicense() {
        CommonIntegrationTestCode.getLicense(airsonicContainer);
    }

    @Test
    public void browsing() {
        CommonIntegrationTestCode.browsing(airsonicContainer);
    }

    @Test
    public void userFlow() {
        CommonIntegrationTestCode.userFlow(airsonicContainer, false);
    }

    @Test
    public void search() {
        CommonIntegrationTestCode.search(airsonicContainer);
    }

    @Test
    public void lists() {
        CommonIntegrationTestCode.lists(airsonicContainer);
    }

    @Test
    public void playlist() {
        CommonIntegrationTestCode.playlist(airsonicContainer);
    }

    @Test
    public void songDownloadAndStream() {
        CommonIntegrationTestCode.songDownloadAndStream(airsonicContainer);
    }

    @Test
    public void imageDownload() {
        CommonIntegrationTestCode.imageDownload(airsonicContainer);
    }

    @Test
    public void annotation() {
        CommonIntegrationTestCode.annotation(airsonicContainer);
    }
}
