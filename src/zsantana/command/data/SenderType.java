package zsantana.command.data;

public enum SenderType {

	PLAYER, CONSOLE, ELSE, ALL;

	public boolean compatible(SenderType type) {
		return this.equals(type) || this.equals(ALL) || type.equals(ALL);
	}
}
