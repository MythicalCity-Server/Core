package zsantana.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import zsantana.Core;
import zsantana.command.data.CmdData;
import zsantana.misc.Log;

public abstract class Command implements CommandExecutor {

	private static Core _CORE;

	public static void setCore(Core core) {
		_CORE = core;
	}

	private final Map<Function<CommandInformation, Boolean>, Consumer<CommandInformation>> _SUB_COMMANDS;
	private final List<Function<CommandSender, String>> _HELP_PAGE;
	
	private CommandSender _currentCommandSender;

	public Command(String cmdName) {
		this._SUB_COMMANDS = new HashMap<>();
		this._HELP_PAGE = new ArrayList<>();
		register();

		_CORE.getCommand(cmdName).setExecutor(this);
	}

	private final void register() {
		for (Method method : getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(CmdData.class) && method.getParameterCount() == 1
					&& method.getParameterTypes()[0].isAssignableFrom(CommandInformation.class)) {
				CmdData data = method.getAnnotation(CmdData.class);
				this._SUB_COMMANDS.put((commandInformation) -> {
					if (data.type().compatible(commandInformation.getSenderType())) {
						if (data.overLength()) {
							if (commandInformation.getArgs().length < data.args().length) {
								return false;
							}
						} else {
							if (commandInformation.getArgs().length != data.args().length) {
								return false;
							}
						}
						for (int i = 0; i < data.args().length; ++i) {
							if (!commandInformation.getArgs()[i].equalsIgnoreCase(data.args()[i])) {
								return false;
							}
						}
						return true;
					}
					return false;
				}, (commandInformation) -> {
					if (commandInformation.getSender().hasPermission(data.permission())) {
						try {
							method.invoke(this, commandInformation);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Log.m(commandInformation.getSender(), "&cYou don't have permission to do that!");
					}
				});
				this._HELP_PAGE.add((sender) -> {
					if (data.permission().equals("") || sender.hasPermission(data.permission())) {
						return "";
					} else {
						return "";
					}
				});
			}
		}
	}

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		this._currentCommandSender = sender;
		CommandInformation information = new CommandInformation(sender, args);
		if (args.length == 0) {
			noArguments(information);
			return true;
		}
		for (Function<CommandInformation, Boolean> key : this._SUB_COMMANDS.keySet()) {
			if (key.apply(information)) {
				this._SUB_COMMANDS.get(key).accept(information);
				return true;
			}
		}
		defaultCommand(information);
		return true;
	}
	
	public final void sendHelpPage() {
		this._currentCommandSender.sendMessage("Hey");
		// TODO FROM PULLING THE METHOD INFORMATION AND STORING IT IN A FIELD
	}

	public abstract void noArguments(CommandInformation info);

	public abstract void defaultCommand(CommandInformation info);
}
