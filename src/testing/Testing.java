package testing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Testing {
	
	public Testing() {
		CustomItemTest test1 = new CustomItemTest();
		CustomArmorTest test2 = new CustomArmorTest();
		
		TestConfig config = new TestConfig();
		
		
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getInventory().addItem(test1.getItem());
			player.getInventory().addItem(test2.getItem());
		}
	}
}
