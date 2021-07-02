package meechapooch.coords.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand {
    public boolean isSonsoleCompatible();
    public boolean run(CommandSender sender, String[] args);
}
