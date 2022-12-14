import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.List;

public class App {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("MTA0NDE3MjIyMDU4NDQzNTczMg.G1O4QH.vwr16iNWcMeLailh4ydCEAismLiS4mP4h-sCrU");
        client.gateway().setEnabledIntents(IntentSet.all()).login().flatMap(gateway-> {
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
            Guild gui =   event.getGuild().block();
                List<Member> Members = gui.getMembers().collectList().block();
                for (Member m : Members
                     ) {
                    System.out.println(m.getDisplayName());
                }

                return Mono.empty();
            }).then();
            return handlePingCommand;
        }).block();
        }
    }
