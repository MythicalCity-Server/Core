package testing;

import zsantana.command.Command;
import zsantana.command.CommandInformation;
import zsantana.command.data.CmdData;

public class TestCommand extends Command {

	public TestCommand() {
		super("test");
	}

	@CmdData(args = { "one" }, description = "", permission = "test.permission")
	public void test(CommandInformation info) {
		info.getSender().sendMessage("HEY");
	}

	@CmdData(args = { "one", "two" })
	public void test2(CommandInformation info) {
		info.getSender().sendMessage("HEY HEY");
		sendHelpPage();
	}

	@Override
	public void noArguments(CommandInformation info) {
		info.getSender().sendMessage("No argument command");
		sendHelpPage();
	}

	@Override
	public void defaultCommand(CommandInformation info) {
		info.getSender().sendMessage("No command were found");
		sendHelpPage();
	}
}
