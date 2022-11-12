package godset;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Godset {

    // @SuppressWarnings("unused")
    public Godset() {
        Helmet helmet = new Helmet();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().addItem(helmet.getItem());
        }
    }
}
