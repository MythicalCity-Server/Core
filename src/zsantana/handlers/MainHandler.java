package zsantana.handlers;

import zsantana.command.Command;
import zsantana.configuration.Configuration;
import zsantana.configuration.ConfigurationHandler;
import zsantana.customitems.CustomItemsHandler;
import zsantana.economy.EconomyHandler;
import zsantana.entitydrops.EntityDropsHandler;

/**
 * Handles all the handlers for the different components of the plugin
 * 
 * @author Zackary Santana
 *
 */
public class MainHandler extends Handler {

    private EconomyHandler _economy;
    private CustomItemsHandler _customItems;
    private ConfigurationHandler _configuration;
    private EntityDropsHandler _entityDrops;

    /**
     * Inits the different handlers
     */
    @Override
    public void enable() {
        Configuration.setCore(_CORE);
        Command.setCore(_CORE);

        this._economy = new EconomyHandler();
        this._customItems = new CustomItemsHandler();
        this._configuration = new ConfigurationHandler();
        this._entityDrops = new EntityDropsHandler();
    }

    /**
     * Runs stop for the different handlers
     */
    @Override
    protected void disable() {
        this._economy.stop();
        this._customItems.stop();
        this._configuration.stop();
        this._entityDrops.stop();
    }

    public EconomyHandler getEconomyHandler() {
        return this._economy;
    }

    public CustomItemsHandler getCustomItemsHandler() {
        return this._customItems;
    }

    public ConfigurationHandler getConfigurationHandler() {
        return this._configuration;
    }

    public EntityDropsHandler getEntityDropsHandler() {
        return this._entityDrops;
    }
}
