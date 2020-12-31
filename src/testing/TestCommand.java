package testing;

import zsantana.command.Command;
import zsantana.command.CommandInformation;
import zsantana.command.data.CmdData;

public class TestCommand extends Command {

	public TestCommand() {
		super("test");
	}

	@CmdData(args = { "one" })
	public void test(CommandInformation info) {
		info.getSender().sendMessage("HEY");
	}

	@Override
	public void noArguments(CommandInformation info) {
		
	}

	@Override
	public void defaultCommand(CommandInformation info) {
		
	}
}
