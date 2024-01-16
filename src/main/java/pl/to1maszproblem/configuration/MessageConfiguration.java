package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import pl.to1maszproblem.notice.Notice;
import pl.to1maszproblem.notice.NoticeType;

@Getter
public class MessageConfiguration extends OkaeriConfig {
    private Notice teleportCanceled = new Notice(NoticeType.MESSAGE,"&cTeleportacja została anulowana!");
    private Notice timeToTeleport = new Notice(NoticeType.MESSAGE,"&fZostaniesz przeteleportowany za &a[time] &fsekund!");
    private Notice teleported = new Notice(NoticeType.TITLE ,"&aZostałeś przeteleportowany do warpu [warp]!");
    private String holdItemToCreate = "&cMusisz trzymać item w ręce aby stworzyć warp!";
    private String warpAlreadyExist = "&cWarp o takiej nazwie już istnieje!";
    private String createdWarp = "&aPomyslnie stworzono warp o nazwie [warp-name]!";
    private String warpDoesntExist = "&cWarp o takiej nazwie nie istnieje!";
    private String deletedWarp = "&aPomyslnie usunięto warp o nazwie [warp-name]!";

}
