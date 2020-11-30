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
package net.beardbot.subsonic.client.base;

import java.util.*;

public abstract class ApiParams {
    private final Map<String,List<String>> paramMap = new HashMap<>();
    private final List<String> secretParamNames = new ArrayList<>();

    protected void setParam(String name, String value){
        paramMap.computeIfAbsent(name, n->new ArrayList<>()).add(value);
    }

    protected void setSecretParam(String name, String value){
        setParam(name,value);
        secretParamNames.add(name);
    }

    public Map<String,List<String>> getParamMap(){
        return new HashMap<>(paramMap);
    }

    public Map<String,List<String>> getParamMapForLogging(){
        var params = getParamMap();
        secretParamNames.forEach(s->params.put(s,Collections.singletonList("*****")));
        return params;
    }
}
