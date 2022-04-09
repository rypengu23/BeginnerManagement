package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.ConsoleMessage;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;

public class DiscordUtil {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    private static boolean connectFirstTime = true;

    public DiscordUtil() {
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    public void sendMessage(String message) {

        try {
            TextChannel textChannel = github.scarsz.discordsrv.util.DiscordUtil.getTextChannelById(mainConfig.getNotifyChannelId());
            github.scarsz.discordsrv.util.DiscordUtil.sendMessage(textChannel, message);
        } catch (NoClassDefFoundError e) {
            Bukkit.getLogger().warning("[BeginnerManagement] " + ConsoleMessage.DiscordUtil_FailureSendMessage);
        }
    }

    public void connectDiscord() {

        try {
            Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.DiscordUtil_ConnectDiscord);

            TextChannel textChannel = github.scarsz.discordsrv.util.DiscordUtil.getTextChannelById(mainConfig.getNotifyChannelId());
            Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.DiscordUtil_SuccessConnect);
        } catch (NoClassDefFoundError e) {
            Bukkit.getLogger().warning("[BeginnerManagement] " + ConsoleMessage.DiscordUtil_FailureConnect);
        }

    }
}
