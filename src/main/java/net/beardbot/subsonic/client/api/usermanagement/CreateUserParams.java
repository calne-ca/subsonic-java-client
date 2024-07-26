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
package net.beardbot.subsonic.client.api.usermanagement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.beardbot.subsonic.client.base.ApiParams;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserParams extends ApiParams {
    public static CreateUserParams create(){
        return new CreateUserParams();
    }

    public CreateUserParams ldapAuthenticated() {
        setParam("ldapAuthenticated", "true");
        return this;
    }

    public CreateUserParams roles(UserRole... userRoles) {
        for (UserRole userRole : userRoles) {
            setParam(userRole.getRoleName(), "true");
        }

        return this;
    }

    public CreateUserParams musicFolder(String... musicFolderIds) {
        for (String musicFolderId : musicFolderIds) {
            setParam("musicFolderId", musicFolderId);
        }

        return this;
    }
}
