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
package net.beardbot.subsonic.client.integration.subsonic;

import net.beardbot.subsonic.client.integration.base.SubsonicBaseContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class SubsonicContainer extends SubsonicBaseContainer {
    public SubsonicContainer() {
        super("danisla/subsonic");

        withExposedPorts(4040);
        withEnv("SUBSONIC_UID","1000");
        withEnv("SUBSONIC_GID","1000");
        withEnv("SUBSONIC_CONTEXT_PATH",contextPath());
        waitingFor(Wait.forLogMessage("Subsonic running on:.*", 1));
    }

    @Override
    protected String containerMediaPath() {
        return "/mnt/music";
    }

    @Override
    protected String contextPath() {
        return "/";
    }
}
