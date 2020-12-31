package zsantana.command.data;

import org.bukkit.command.CommandSender;

public class CommandMetaData {

	private String[] _args;
	private String _desc, _permission;
	private SenderType _type;

	private int _length;
	private boolean _overLength;
	
	public CommandMetaData(CmdData data) {
		this._args = data.args();
		this._desc = data.description();
		this._permission = data.permission();
		this._length = data.args().length;
		this._overLength = data.overLength();
		this._type = data.type();
	}

	/**
	 * @return The args required to be done in the command
	 */
	public String[] getArgs() {
		return this._args;
	}

	/**
	 * @return The desc of the command
	 */
	public String getDesc() {
		return this._desc;
	}

	/**
	 * @return The permission of the command
	 */
	public String getPermission() {
		return this._permission;
	}

	/**
	 * @return The length of the args required
	 */
	public int getLength() {
		return this._length;
	}

	/**
	 * @return If the command can use more args then required
	 */
	public boolean getOverLength() {
		return this._overLength;
	}

	/**
	 * @return Whom is the command for
	 */
	public SenderType getSenderType() {
		return this._type;
	}

	public boolean hasPermission(CommandSender sender) {
		if (!this._permission.equals("")) {
			return sender.hasPermission(this._permission);
		} else {
			return true;
		}
	}
}
