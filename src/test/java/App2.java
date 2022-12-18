import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.gateway.intent.IntentSet;
import reactor.core.publisher.Mono;

public class App2 {
   static Message msge =null;
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("MTA0NDE3MjIyMDU4NDQzNTczMg.G1O4QH.vwr16iNWcMeLailh4ydCEAismLiS4mP4h-sCrU");
        client.gateway().setEnabledIntents(IntentSet.all()).login().flatMap(gateway -> {
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                if(event.getMessage().getContent().equalsIgnoreCase("!c")){
                    EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                    builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                    builder.title("Test");
                    builder.description("Primary message with out edit ");
                    builder.footer("footer",null);
                  msge= event.getMessage().getChannel().block().createMessage(builder.build()).block();
                }
                if(event.getMessage().getContent().equalsIgnoreCase("!ce")){
                    EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                    builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                    builder.title("Test");
                    builder.description("Message was edit");
                    builder.footer("edit",null);
                    msge.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
                }
                return Mono.empty();
            }).then();
            return handlePingCommand;
        }).block();
    }
}
