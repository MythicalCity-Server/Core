package testing;

import godset.Godset;

public class Testing {

    @SuppressWarnings("unused")
    public Testing() {
        CustomItemTest test1 = new CustomItemTest();
        CustomArmorTest test2 = new CustomArmorTest();
        CustomRarityItemTest test3 = new CustomRarityItemTest();
        CustomRarityArmorTest test4 = new CustomRarityArmorTest();
        CustomEntityTest test5 = new CustomEntityTest();

        TestConfig config = new TestConfig();
        TestCommand command = new TestCommand();
        TestEntityDrop entityDrop = new TestEntityDrop();

        // for (Player player : Bukkit.getOnlinePlayers()) {
        // player.getInventory().addItem(test1.getItem());
        // player.getInventory().addItem(test2.getItem());
        // player.getInventory().addItem(test3.getItem());
        // player.getInventory().addItem(test4.getItem());
        // test5.spawnEntity(player.getLocation());
        // }

        new Godset();
    }
}
