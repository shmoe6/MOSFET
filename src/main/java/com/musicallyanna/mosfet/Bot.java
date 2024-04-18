package com.musicallyanna.mosfet;

import com.musicallyanna.mosfet.calendar.CalendarHandler;
import com.musicallyanna.mosfet.command.CommandManager;
import com.musicallyanna.mosfet.command.commands.scheduling.ModalHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

/**
 * Creates the Bot and contains the entry point to the program. Config is initialized here.
 *
 * @author Anna Bontempo
 */
public class Bot {

    /* Instance Variables. */

    /**
     * Configuration for the bot.
     */
    private final Dotenv config;

    /**
     * The {@code net.dv8tion.jda.api.sharding.ShardManager} used to handle the bot.
     */
    private final ShardManager shardManager;

    public static final CalendarHandler calendarHandler = new CalendarHandler();

    /**
     * No argument constructor. Performs the initial setup for the bot by disabling cache, setting status/activity,
     * and building it.
     * @throws LoginException thrown when the bot cannot log in to Discord
     */
    public Bot() throws LoginException {
        config = Dotenv.configure().load(); // load config
        final String token = config.get("TOKEN"); // get the bot token to pass into {@code this.shardManager}

        this.shardManager = DefaultShardManagerBuilder.createLight(token) // create a "light" instance of JDA (look on docs)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE) // disable not needed parts of the cache
                .setBulkDeleteSplittingEnabled(false) // enable the bulk delete event in case of moderation
                .setStatus(OnlineStatus.ONLINE) // set online (green circle) status for the bot
                .setActivity(Activity.watching("ElectroBOOM")) // set activity status
                .build(); // build the bot

        // register listeners
        this.shardManager.addEventListener(new CommandManager());
        this.shardManager.addEventListener(new ModalHandler());
    }

    /**
     * Returns an instance of the bot config.
     * @return the {@code io.github.cdimascio.dotenv.Dotenv} that is the current config for the bot.
     */
    public Dotenv getConfig() {
        return this.config;
    }

    /**
     * Returns the current shard manager and JDA for the bot.
     * @return the {@code net.dv8tion.jda.api.sharding.ShardManager} that is the current shard manager for the bot.
     */
    public ShardManager getShardManager() {
        return this.shardManager;
    }

    public CalendarHandler getCalendarHandler() {
        return this.calendarHandler;
    }

    public static void main(String[] args) {
        try {
            // create bot
            Bot bot = new Bot();
        } catch (LoginException le) {
            // handle exception with Discord login
            le.printStackTrace();
        }
    }
}
