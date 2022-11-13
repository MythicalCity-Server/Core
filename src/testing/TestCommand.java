package testing;

import zsantana.command.Command;
import zsantana.command.CommandInformation;
import zsantana.command.data.CmdData;

public class TestCommand extends Command {

    public TestCommand() {
        super("Test");
    }

    @CmdData(args = { "one" }, description = "This is the only command", permission = "test.permission")
    public void test(CommandInformation info) {
        info.getSender().sendMessage("HEY");
    }

    @CmdData(args = { "one", "two" })
    public void test2(CommandInformation info) {
        info.getSender().sendMessage("HEY HEY");
    }

    @CmdData(args = { "five" }, overLength = true)
    public void test3(CommandInformation info) {
        info.getSender().sendMessage("HEY HEY");
        for (String arg : info.getArgs()) {
            info.getSender().sendMessage(arg);
        }
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
