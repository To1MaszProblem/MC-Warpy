package pl.to1maszproblem.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static String fixColor(String string) {
        Pattern pattern = Pattern.compile("&(#[a-fA-F0-9]{6})");
        for (Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)) {
            String color = string.substring(matcher.start() + 1, matcher.end());
            string = string.replace("&" + color, ChatColor.of(color) + "");
        }
        string = ChatColor.translateAlternateColorCodes('&', string);
        return ChatColor.translateAlternateColorCodes('&', string)
                .replace(">>", "»")
                .replace("<<", "«")
                .replace("->", "→")
                .replace("<-", "←")
                .replace("**", "•");
    }




    public static void sendMessage(String chatType, Player player, String message) {
        switch (chatType) {
            case "CHAT" -> player.sendMessage(fixColor(message));
            case "ACTION_BAR" ->
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(fixColor(message)));
            case "SUBTITLE" -> player.sendTitle("", fixColor(message), 10, 35, 10);
            case "TITLE" -> player.sendTitle(fixColor(message), "", 10, 35, 10);
        }
    }

    public static void sendLogger(String text, Plugin plugin) {
        Bukkit.getLogger().info(fixColor("&8[&e" + plugin.getName() + "&8] " + text));
    }

}