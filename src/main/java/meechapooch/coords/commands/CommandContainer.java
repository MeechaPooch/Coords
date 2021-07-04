package meechapooch.coords.commands;

import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandContainer implements SubCommand {

    public HashMap<String, SubCommand> subCommands = new HashMap<>();

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return subCommands.keySet().stream().collect(Collectors.toList());
        else if(subCommands.containsKey(args[0].toLowerCase())) return subCommands.get(args[0].toLowerCase()).autoComplete(sender,profile,Arrays.copyOfRange(args,1,args.length));
        else return null;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 0) return getNoCommandsMessage();
        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if(subCommand == null) return args[0] + " is not a valid subcommand";
        return subCommand.run(sender, profile, Arrays.copyOfRange(args,1,args.length));
    }

    public abstract String getNoCommandsMessage();

    @Override
    public String getUsage() {
        StringBuilder usages = new StringBuilder();
        subCommands.forEach((name,command)->usages.append("\n" + name + " " + command.getUsage()));
        return usages.toString();
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        subCommands.forEach((name,command)->examples.addAll(command.getExamples()));
        return examples;
    }
}
