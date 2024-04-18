package com.musicallyanna.mosfet;

import com.musicallyanna.mosfet.command.CommandManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {

    private final Dotenv config;
    private final ShardManager shardManager;

    public Bot() throws LoginException {
        config = Dotenv.configure().load();
        final String token = config.get("TOKEN");

        this.shardManager = DefaultShardManagerBuilder.createLight(token)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE) // disable parts of the cache
                .setBulkDeleteSplittingEnabled(false) // enable the bulk delete event in case of moderation
                .setStatus(OnlineStatus.ONLINE) // set online (green circle) status for the bot
                .setActivity(Activity.watching("ElectroBOOM")) // set status for the bot
                .build();

        this.shardManager.addEventListener(new CommandManager()); // register listeners
    }

    public Dotenv getConfig() {
        return this.config;
    }

    public ShardManager getShardManager() {
        return this.shardManager;
    }


    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
        } catch (LoginException le) {
            le.printStackTrace();
        }
    }
}
