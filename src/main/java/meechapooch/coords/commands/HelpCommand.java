package meechapooch.coords.commands;

import org.bukkit.command.CommandSender;

public class HelpCommand implements SubCommand{
    @Override
    public boolean isSonsoleCompatible() {
        return true;
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        sender.sendMessage("      ========== COORDS COMMANDS ==========\n" +
                "<CORE COMMANDS>\n" +
                "\n" +
                "/coords guide: guide player to saved coordinate or\n" +
                "inputted xyz location\n" +
                "USAGE: /coords guide <saved name, list path, or x y z coordinate>\n" +
                "EXAMPLES:\n" +
                "/coords guide home\n" +
                "/coords guide 8453 57 -4324\n" +
                "/coords guide 8453 -4324\n" +
                "/coords guide bases/TurtleMasterPDX\n" +
                "\n" +
                "/coords add: add coordinate to personal list\n" +
                "USAGE\n" +
                "\n" +
                "/coords remove: remove coordinate from personal list\n" +
                "\n" +
                "/coords send\n" +
                "\n" +
                "<LIST COMMANDS>\n" +
                "\n");
        return true;
    }
}
