package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> onCompList = new ArrayList<>();

        CheckUtil checkUtil = new CheckUtil();

        if (args.length == 1) {

            if (sender.hasPermission("beginnerManagement.info") || sender.hasPermission("beginnerManagement.anotherInfo")) {
                onCompList.add("info");
            }
            if (sender.hasPermission("beginnerManagement.whitelist")) {
                onCompList.add("whitelist");
            }
            if (sender.hasPermission("beginnerManagement.reload")) {
                onCompList.add("reload");
            }
            onCompList.add("help");
        }

        if ((command.getName().equalsIgnoreCase("beginnermanagement") || command.getName().equalsIgnoreCase("bm")) && onCompList.size() > 0) {
            return onCompList;
        }
        return null;
    }
}
