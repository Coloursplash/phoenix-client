package phoenixclient;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {
    private boolean running = true;
    private long timeCreated = 0;

    public void start() {
        this.timeCreated = System.currentTimeMillis();

        DiscordEventHandlers discordHandler = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                System.out.println("Discord user '" + discordUser.username + "#" + discordUser.discriminator + "' found.");
                update("Booting up...", "");
            }
        }).build();

        DiscordRPC.discordInitialize("818499395614933032", discordHandler, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondLine);
        builder.setBigImage("black", "");
        builder.setDetails(firstLine);
        builder.setStartTimestamps(timeCreated);

        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
