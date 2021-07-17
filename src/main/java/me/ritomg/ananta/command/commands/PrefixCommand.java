package me.ritomg.ananta.command.commands;

import me.ritomg.ananta.command.Command;
import me.ritomg.ananta.command.CommandManager;
import me.ritomg.ananta.util.AnantaMessageUtil;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("Prefix", "Sets the prefix", new String[]{"setPrefix"}, "prefix 'preifx'");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 1) {
            CommandManager.prefix = args[0];
            AnantaMessageUtil.sendClientPrefixMessage("Set preifx to" + CommandManager.prefix);
        }
    }
}