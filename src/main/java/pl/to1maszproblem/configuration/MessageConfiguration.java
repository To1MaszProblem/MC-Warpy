package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

@Getter
public class MessageConfiguration extends OkaeriConfig {
    private String teleportCanceled = "&cTeleportacja została anulowana!";
    private String teleportCanceledChatType = "CHAT";
    private String timeToTeleport = "&fZostaniesz przeteleportowany za &a[time] &fsekund!";
    private String timeChatType = "CHAT";
    private String teleported = "&aZostałeś przeteleportowany do warpu [warp]!";
    private String teleportedChatType = "SUBTITLE";
    private String holdItemToCreate = "&cMusisz trzymać item w ręce aby stworzyć warp!";
    private String warpAlreadyExist = "&cWarp o takiej nazwie już istnieje!";
    private String createdWarp = "&aPomyslnie stworzono warp o nazwie [warp-name]!";
    private String warpDoesntExist = "&cWarp o takiej nazwie nie istnieje!";
    private String deletedWarp = "&aPomyslnie usunięto warp o nazwie [warp-name]!";

}
