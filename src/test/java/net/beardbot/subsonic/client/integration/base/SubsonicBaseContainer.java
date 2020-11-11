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
package net.beardbot.subsonic.client.integration.base;

import lombok.NonNull;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

public abstract class SubsonicBaseContainer extends GenericContainer<SubsonicBaseContainer> {
    public SubsonicBaseContainer(@NonNull String dockerImageName) {
        super(dockerImageName);
    }

    protected abstract String containerMediaPath();
    protected abstract String contextPath();

    public String baseUrl(){
        var adaptedContextPath = contextPath().startsWith("/") ? contextPath() : "/" + contextPath();
        return String.format("http://%s:%d%s",getContainerIpAddress(),getMappedPort(4040),adaptedContextPath);
    }

    public void addMusicFolder(String name){
        var adaptedName = name;

        if (name.startsWith("/")){
            adaptedName = name.replaceFirst("/","");
        }

        copyFileToContainer(MountableFile.forClasspathResource("media/" + adaptedName), containerMediaPath() + "/" + adaptedName);
    }
}
