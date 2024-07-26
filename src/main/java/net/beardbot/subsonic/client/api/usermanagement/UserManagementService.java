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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.beardbot.subsonic.client.Subsonic;
import net.beardbot.subsonic.client.SubsonicPreferences;
import net.beardbot.subsonic.client.base.SubsonicClient;
import org.subsonic.restapi.User;

import java.util.List;

import static net.beardbot.subsonic.client.utils.SubsonicResponseErrorHandler.handleError;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserManagementService {
    private final UserManagementClient userManagementClient;
    private final Subsonic subsonic;

    public UserManagementService(Subsonic subsonic) {
        this.userManagementClient = SubsonicClient.create(subsonic,UserManagementClient.class);
        this.subsonic = subsonic;
    }

    public User getUser(String userName){
        log.debug("Fetching user info for user '{}'", userName);

        var response = userManagementClient.getUser(userName);
        handleError(response);

        return response.getUser();
    }

    public List<User> getAllUsers(){
        log.debug("Fetching user info for all users");

        var response = userManagementClient.getUsers();
        handleError(response);

        return response.getUsers().getUsers();
    }

    public void createUser(String userName, String password, String email){
        createUser(userName,password,email,CreateUserParams.create());
    }

    public void createUser(String userName, String password, String email, CreateUserParams createUserParams){
        log.debug("Create user with name '{}', email '{}' and params '{}'", userName, email, createUserParams.getParamMapForLogging());

        var response = userManagementClient.createUser(userName, password, email, createUserParams.getParamMap());
        handleError(response);
    }

    public void updateUser(String userName,  UpdateUserParams updateUserParams){
        log.debug("Update user with name '{}' and params '{}'", userName, updateUserParams.getParamMapForLogging());

        var response = userManagementClient.updateUser(userName, updateUserParams.getParamMap());
        handleError(response);
    }

    public void deleteUser(String userName){
        log.debug("Delete user with name '{}'", userName);

        var response = userManagementClient.deleteUser(userName);
        handleError(response);
    }

    public void changePassword(String userName, String password){
        log.debug("Changing password for user with name '{}'", userName);

        var response = userManagementClient.changePassword(userName, password);
        handleError(response);

        var preferences = subsonic.getPreferences();
        if (preferences.getUsername().equals(userName)){
            preferences.setPassword(password);
        }
    }
}
