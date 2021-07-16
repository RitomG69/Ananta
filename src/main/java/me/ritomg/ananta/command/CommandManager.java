package me.ritomg.ananta.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ritomg.ananta.util.AnantaMessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public static List<Command> commandList = new ArrayList<>();
    public static String prefix = ".";

    public static void init() {
    }

    public static void callCommand(String message) {
        message = message.substring(prefix.length());
        if(message.split(" ").length > 0) {
            boolean commandFound = false;
            String commandName = message.split(" ")[0];
            for(Command c : commandList) {
                if(c.getAlias().contains(commandName) || c.getName().equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    commandFound = true;
                    break;
                }
            }
            if(!commandFound) {
                AnantaMessageUtil.sendClientPrefixMessage(ChatFormatting.DARK_RED + "Command not found, use " + ChatFormatting.ITALIC + prefix + "help " + ChatFormatting.RESET + "" + ChatFormatting.DARK_RED + "for list of commands.");
            }
        }
    }

}
