import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.gateway.intent.IntentSet;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;


public class App {
    static int type = -1;
    static HashMap<String, GSession> sessions = new HashMap<>();

    public static void playgame(MessageCreateEvent event, String[] messages) {
        String scode = messages[2];
        if (sessions.get(scode).joinBoth) {
            String activityname = GActivity.getActivitybyId(Integer.parseInt(messages[1]));
            String autm = event.getMessage().getAuthor().get().getMention();
            if (autm.equalsIgnoreCase(sessions.get(scode).p1m.getMention())) {
                sessions.get(scode).a1 = new GActivity(activityname);
                sessions.get(scode).p1r = true;
                String ready1 = sessions.get(messages[2]).p1r ? "ready :green_circle:" : "not ready :red_circle:";
                String ready2 = sessions.get(messages[2]).p2r ? "ready :green_circle:" : "not ready :red_circle:";
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Game round:"+sessions.get(messages[2]).round);
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        sessions.get(messages[2]).p1.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p1.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p1.getStatus() + "\n"+
                                "Ready: " + ready1,true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        sessions.get(messages[2]).p2.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p2.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p2.getStatus() + "\n"+
                                "Ready: " + ready2,true);
                builder.addField("Last round","Waiting first round",false);
                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
            }
            if (autm.equalsIgnoreCase(sessions.get(scode).p2m.getMention())) {
                sessions.get(scode).a2 = new GActivity(activityname);
                sessions.get(scode).p2r = true;
                String ready1 = sessions.get(messages[2]).p1r ? "ready :green_circle:" : "not ready :red_circle:";
                String ready2 = sessions.get(messages[2]).p2r ? "ready :green_circle:" : "not ready :red_circle:";

                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Game round:"+sessions.get(messages[2]).round);
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        sessions.get(messages[2]).p1.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p1.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p1.getStatus() + "\n"+
                                "Ready: " + ready1,true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        sessions.get(messages[2]).p2.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p2.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p2.getStatus() + "\n"+
                                "Ready: " + ready2,true);
                builder.addField("Last round","Waiting first round",false);
                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
            }
            if (sessions.get(scode).p2r && sessions.get(scode).p1r) {
                sessions.get(scode).play();
                String ready1 = sessions.get(messages[2]).p1r ? "ready :green_circle:" : "not ready :red_circle:";
                String ready2 = sessions.get(messages[2]).p2r ? "ready :green_circle:" : "not ready :red_circle:";
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Game round:"+sessions.get(messages[2]).round);
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        sessions.get(messages[2]).p1.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p1.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p1.getStatus() + "\n"+
                                "Ready: " + ready1,true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        sessions.get(messages[2]).p2.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p2.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p2.getStatus() + "\n"+
                                "Ready: " + ready2,true);
                builder.addField("Last round","Waiting first round",false);
                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();

            }
        }
    }

    public static void joingame(MessageCreateEvent event, String[] messages) {
        String actname="";
        String actcode="";
        int i=1;
        for (String act: GActivity.getActivityList()
        ) {
            actname+= act + "\n";
            actcode+= i++ +"\n";
        }
        if ((sessions.get(messages[2]).connectedBoth) && (!sessions.get(messages[2]).joinBoth)) {
            String autm = event.getMessage().getAuthor().get().getMention();
            int character = Integer.valueOf(messages[1]);
            String characterS = "Knight";
            if (character == 1) {
                characterS = "Knight";
            }

            if (autm.equalsIgnoreCase(sessions.get(messages[2]).p1m.getMention())) {
                System.out.println("1");
                sessions.get(messages[2]).p1j = true;
                sessions.get(messages[2]).p1 = new GPlayer(new GCharacter(characterS),
                        sessions.get(messages[2]).p1m.getId().asString());
                String join1 = sessions.get(messages[2]).p1j ? ":green_circle:" : ":red_circle:";
                String join2 = sessions.get(messages[2]).p2j ? ":green_circle:" : ":red_circle:";
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Joining");
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        "Joined:"+join1,true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        "Joined:"+join2,true);
                builder.footer("code:"+messages[2],null);
                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
            }
            if (autm.equalsIgnoreCase(sessions.get(messages[2]).p2m.getMention())) {
                System.out.println("0");
                sessions.get(messages[2]).p2j = true;
                sessions.get(messages[2]).p2 = new GPlayer(new GCharacter(characterS),
                        sessions.get(messages[2]).p2m.getId().asString());
                String join1 = sessions.get(messages[2]).p1j ? ":green_circle:" : ":red_circle:";
                String join2 = sessions.get(messages[2]).p2j ? ":green_circle:" : ":red_circle:";
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Joining");
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        "Joined:"+join1,true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        "Joined:"+join2,true);
                builder.footer("code:"+messages[2],null);

                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
            }
            if ((sessions.get(messages[2]).p1j) && (sessions.get(messages[2]).p2j)) {
                sessions.get(messages[2]).joinBoth = true;
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Game round:"+sessions.get(messages[2]).round);
                builder.addField(sessions.get(messages[2]).p1m.getDisplayName(),
                        sessions.get(messages[2]).p1.getCharacter().getName()+"\n"+
                        "Hp: " + sessions.get(messages[2]).p1.getCharacter().getHealthpoint()+"\n"+
                        "Status: " + sessions.get(messages[2]).p1.getStatus() + "\n"+
                        "Ready: :red_circle:",true);
                builder.addField(sessions.get(messages[2]).p2m.getDisplayName(),
                        sessions.get(messages[2]).p2.getCharacter().getName()+"\n"+
                                "Hp: " + sessions.get(messages[2]).p2.getCharacter().getHealthpoint()+"\n"+
                                "Status: " + sessions.get(messages[2]).p2.getStatus() + "\n"+
                                "Ready: :red_circle:",true);
                builder.addField("Last round","Waiting first round",false);
                sessions.get(messages[2]).mainMessage.edit(MessageEditSpec.builder().addEmbed(builder.build()).build()).block();
                sessions.get(messages[2]).p1m.getPrivateChannel().block().createMessage("Ok, game is ready, u have to choose actions, when your opponent" +
                        " choose game you have to see result your and opponent actions. Good luck!");
                sessions.get(messages[2]).p2m.getPrivateChannel().block().createMessage("Ok, game is ready, u have to choose actions, when your opponent" +
                        " choose game you have to see result your and opponent actions. Good luck!");

                builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Playing");
                builder.description("Now you must choose activity \n " +
                        "For choose use command \n " +
                        "!play <Activity code> "+ messages[2]+
                        "\n Activity code you have to find below.");
                builder.addField("Activity",
                        actname,true);
                builder.addField("Code",
                        actcode,true);
                builder.footer("code:"+messages[2],null);
                sessions.get(messages[2]).p1m.getPrivateChannel().block().createMessage(builder.build()).block();
                sessions.get(messages[2]).p2m.getPrivateChannel().block().createMessage(builder.build()).block();
            }
        }
    }

    public static void conngame(MessageCreateEvent event, String message) {
        String autm = event.getMessage().getAuthor().get().getMention();
        System.out.println(autm);
        System.out.println(sessions.get(message).p1m.getMention());
        if (!sessions.get(message).connectedBoth) {
            if (autm.equalsIgnoreCase(sessions.get(message).p1m.getMention())) {
                sessions.get(message).p1c = true;
            }
            if (autm.equalsIgnoreCase(sessions.get(message).p2m.getMention())) {
                sessions.get(message).p2c = true;
            }
            if ((sessions.get(message).p2c) && (sessions.get(message).p1c)) {
                sessions.get(message).connectedBoth = true;
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Joining");
                builder.addField(sessions.get(message).p1m.getDisplayName(),
                        "Joined::red_circle:",true);
                builder.addField(sessions.get(message).p2m.getDisplayName(),
                        "Joined::red_circle:",true);
                builder.footer("code:"+message,null);
                sessions.get(message).mainMessage = sessions.get(message).mainGroup.block().createMessage(builder.build()).block();
                builder = EmbedCreateSpec.builder();
                builder.author(event.getClient().getSelf().block().getUsername(),null,null);
                builder.title("Joining");
                builder.description("Now you have to choose your character for a game. \n " +
                        "For choose use command \n " +
                        "!join <Character code> "+ message+
                        "\n Character code you have to find below.");
                builder.addField("Character",
                        "Knight",true);
                builder.addField("Code",
                        "1",true);
                builder.footer("code:"+message,null);
                sessions.get(message).p1m.getPrivateChannel().block().createMessage(builder.build()).block();
                sessions.get(message).p2m.getPrivateChannel().block().createMessage(builder.build()).block();
            }
        }
    }

    public static void authgame(MessageCreateEvent event, String message) {
        Guild f = event.getGuild().block();
        Member member1 = null;
        Member member2 = null;
        Mono<MessageChannel> channel = event.getMessage().getChannel();
        String autm = event.getMessage().getAuthor().get().getMention();
        for (Member member : f.getMembers().collectList().block()) {
            if (autm.equalsIgnoreCase(member.getMention())) member1 = member;
            if (message.equalsIgnoreCase(member.getMention())) member2 = member;
        }
        GSession gs = new GSession(member1, member2, channel);
        String scode = String.valueOf((Math.random() * 132141) / 100 * 20);
        sessions.put(scode, gs);
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.author(event.getClient().getSelf().block().getUsername(),null,null);
        builder.title("Connecting");
        builder.description("Session is created, into your private messages was sending instruction for connect");
        builder.footer("code:"+scode,null);
        channel.block().createMessage(builder.build()).block();
        member1.getPrivateChannel().block().createMessage("Hi, u invited in game. For connected and auth, send: !connect " + scode).block();
        member2.getPrivateChannel().block().createMessage("Hi, u invited in game. For connected and auth, send: !connect " + scode).block();
    }

    public static void main(String[] args) {

        DiscordClient client = DiscordClient.create("MTA0NDE3MjIyMDU4NDQzNTczMg.G1O4QH.vwr16iNWcMeLailh4ydCEAismLiS4mP4h-sCrU");
        client.gateway().setEnabledIntents(IntentSet.all()).login().flatMap(gateway -> {
            Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                            Mono.fromRunnable(() -> {
                                final User self = event.getSelf();
                                System.out.printf("(test)Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                            }))
                    .then();

            // MessageCreateEvent example
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();
                event.getMessage().getRestChannel().getData().flatMap(mr -> {
                    type = mr.type();
                    return Mono.empty();
                }).block();
                String[] messages = message.getContent().split(" ");
                System.out.println(type);
                if (type == 0) {
                    if (messages[0].equalsIgnoreCase("!create")) {
                        authgame(event, messages[1]);
                    }

                    return Mono.empty();
                } else if (type == 1) {
                    String messagecomm = messages[0];
                    switch (messagecomm) {
                        case "!connect": {
                            conngame(event, messages[1]);
                            break;
                        }
                        case "!join": {
                            joingame(event, messages);
                            break;
                        }
                        case "!play": {
                            playgame(event, messages);
                            break;
                        }


                    }


                }


                return Mono.empty();
            }).then();

            return printOnLogin.and(handlePingCommand);
        }).block();
    }
}
