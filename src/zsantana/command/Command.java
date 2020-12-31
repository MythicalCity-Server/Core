package zsantana.command;

import java.lang.reflect.Method;
import java.util.HashMap;
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

	private Map<Function<CommandInformation, Boolean>, Consumer<CommandInformation>> _SUB_COMMANDS;

	public Command(String cmdName) {
		this._SUB_COMMANDS = new HashMap<>();
		register();

		_CORE.getCommand(cmdName).setExecutor(this);
	}

	private void register() {
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
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
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
	
	public void sendHelpPage(CommandSender sender) {
		// TODO FROM PULLING THE METHOD INFORMATION AND STORING IT IN A FIELD
	}

	public abstract void noArguments(CommandInformation info);

	public abstract void defaultCommand(CommandInformation info);
}
