package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;

public class DiscordUtil {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public DiscordUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    public void sendMessageConsoleChannel(String message){

        try {
            TextChannel mainChannel = BeginnerManagement.discordSRV.getConsoleChannel();
            github.scarsz.discordsrv.util.DiscordUtil.sendMessage(mainChannel, message);
        }catch(NullPointerException e){

        }
    }


}
