package testing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Testing {
	
	@SuppressWarnings("unused")
	public Testing() {
		CustomArmorTest test2 = new CustomArmorTest();
		CustomRarityArmorTest test4 = new CustomRarityArmorTest();
		
		TestConfig config = new TestConfig();
		TestCommand command = new TestCommand();
		TestEntityDrop entityDrop = new TestEntityDrop();
		
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getInventory().addItem(test2.getItem());
			player.getInventory().addItem(test4.getItem());
		}
	}
}
