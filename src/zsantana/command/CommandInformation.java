package zsantana.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import zsantana.command.data.SenderType;

public class CommandInformation {
	
	private final CommandSender _COMMAND_SENDER;
	private final String[] _ARGS;
	private final SenderType _SENDER_TYPE;
	
	public CommandInformation(CommandSender sender, String[] args) {
		this._COMMAND_SENDER = sender;
		this._ARGS = args;
		if (sender instanceof Player) {
			this._SENDER_TYPE = SenderType.PLAYER;
		} else if (sender instanceof ConsoleCommandSender) {
			this._SENDER_TYPE = SenderType.CONSOLE;
		} else {
			this._SENDER_TYPE = SenderType.ELSE;
		}
	}
	
	public CommandSender getSender() {
		return this._COMMAND_SENDER;
	}
	
	public String[] getArgs() {
		return this._ARGS;
	}
	
	public SenderType getSenderType() {
		return this._SENDER_TYPE;
	}
}
