package net.beardbot.subsonic.client.api.lists;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlbumListType {
    RANDOM("random"),
    NEWEST("newest"),
    HIGHEST("highest"),
    FREQUENT("frequent"),
    RECENT("recent"),
    ALPHABETICAL_BY_NAME("alphabeticalByName"),
    ALPHABETICAL_BY_ARTIST("alphabeticalByArtist"),
    STARRED("starred"),
    BY_YEAR("byYear"),
    BY_GENRE("byGenre");
    private final String value;

}
