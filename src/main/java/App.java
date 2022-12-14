import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.gateway.intent.IntentSet;
import reactor.core.publisher.Mono;

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

                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Game code: " +
                            messages[2] + "\tRound: " + sessions.get(messages[2]).round +
                            "\nPlayer1\t\tPlayer2\n" +
                            sessions.get(messages[2]).p1m.getMention() + "\t\t" + sessions.get(messages[2]).p2m.getMention() +
                            "\n" + sessions.get(messages[2]).p1.getCharacter().getName() + "\t\t" + sessions.get(messages[2]).p2.getCharacter().getName() +
                            "\nHp:" + sessions.get(messages[2]).p1.getCharacter().getHealthpoint() +
                            "\t\tHp:" + sessions.get(messages[2]).p2.getCharacter().getHealthpoint() +
                            "\n" + ready1
                            + "\t\t" +
                            ready2);
                }).block();
            }
            if (autm.equalsIgnoreCase(sessions.get(scode).p2m.getMention())) {
                sessions.get(scode).a2 = new GActivity(activityname);
                sessions.get(scode).p2r = true;
                String ready1 = sessions.get(messages[2]).p1r ? "ready :green_circle:" : "not ready :red_circle:";
                String ready2 = sessions.get(messages[2]).p2r ? "ready :green_circle:" : "not ready :red_circle:";

                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Game code: " +
                            messages[2] + "\tRound: " + sessions.get(messages[2]).round +
                            "\nPlayer1\t\tPlayer2\n" +
                            sessions.get(messages[2]).p1m.getMention() + "\t\t" + sessions.get(messages[2]).p2m.getMention() +
                            "\n" + sessions.get(messages[2]).p1.getCharacter().getName() + "\t\t" + sessions.get(messages[2]).p2.getCharacter().getName() +
                            "\nHp:" + sessions.get(messages[2]).p1.getCharacter().getHealthpoint() +
                            "\t\tHp:" + sessions.get(messages[2]).p2.getCharacter().getHealthpoint() +
                            "\n" + ready1
                            + "\t\t" +
                            ready2);
                }).block();
            }
            if (sessions.get(scode).p2r && sessions.get(scode).p1r) {
                sessions.get(scode).play();
                String ready1 = sessions.get(messages[2]).p1r ? "ready :green_circle:" : "not ready :red_circle:";
                String ready2 = sessions.get(messages[2]).p2r ? "ready :green_circle:" : "not ready :red_circle:";

                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Game code: " +
                            messages[2] + "\tRound: " + sessions.get(messages[2]).round +
                            "\nPlayer1\t\tPlayer2\n" +
                            sessions.get(messages[2]).p1m.getMention() + "\t\t" + sessions.get(messages[2]).p2m.getMention() +
                            "\n" + sessions.get(messages[2]).p1.getCharacter().getName() + "\t\t" + sessions.get(messages[2]).p2.getCharacter().getName() +
                            "\nHp:" + sessions.get(messages[2]).p1.getCharacter().getHealthpoint() +
                            "\t\tHp:" + sessions.get(messages[2]).p2.getCharacter().getHealthpoint() +
                            "\n" + ready1
                            + "\t\t" +
                            ready2);
                }).block();

            }
        }
    }

    public static void joingame(MessageCreateEvent event, String[] messages) {
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
                String join1 = sessions.get(messages[2]).p1j ? "join :green_circle:" : "not join :red_circle:";
                String join2 = sessions.get(messages[2]).p2j ? "join :green_circle:" : "not join :red_circle:";
                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Both player is connected in game, code: " +
                            messages[2] + "\nPlayer1: " + sessions.get(messages[2]).p1m.getMention()
                            + "\t" + join1 +
                            "\nPlayer2: " + sessions.get(messages[2]).p2m.getMention()
                            + "\t" + join2);
                }).block();
            }
            if (autm.equalsIgnoreCase(sessions.get(messages[2]).p2m.getMention())) {
                System.out.println("0");
                sessions.get(messages[2]).p2j = true;
                sessions.get(messages[2]).p2 = new GPlayer(new GCharacter(characterS),
                        sessions.get(messages[2]).p2m.getId().asString());
                String join1 = sessions.get(messages[2]).p1j ? "join :green_circle:" : "not join :red_circle:";
                String join2 = sessions.get(messages[2]).p2j ? "join :green_circle:" : "not join :red_circle:";
                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Both player is connected in game, code: " +
                            messages[2] + "\nPlayer1: " + sessions.get(messages[2]).p1m.getMention()
                            + "\t" + join1 +
                            "\nPlayer2: " + sessions.get(messages[2]).p2m.getMention()
                            + "\t" + join2);
                }).block();

            }
            if ((sessions.get(messages[2]).p1j) && (sessions.get(messages[2]).p2j)) {
                sessions.get(messages[2]).joinBoth = true;
                sessions.get(messages[2]).mainMessage.edit(msg -> {
                    msg.setContent("Game code: " +
                            messages[2] + "\tRound: " + sessions.get(messages[2]).round +
                            "\nPlayer1\t\tPlayer2\n" +
                            sessions.get(messages[2]).p1m.getMention() + "\t\t" + sessions.get(messages[2]).p2m.getMention() +
                            "\n" + sessions.get(messages[2]).p1.getCharacter().getName() + "\t\t" + sessions.get(messages[2]).p2.getCharacter().getName() +
                            "\nHp:" + sessions.get(messages[2]).p1.getCharacter().getHealthpoint() +
                            "\t\tHp:" + sessions.get(messages[2]).p2.getCharacter().getHealthpoint() +
                            "\nnot ready :red_circle: \t\tnot ready :red_circle:");
                }).block();
            }
        }
    }

    public static void conngame(MessageCreateEvent event, String message) {
        String autm = event.getMessage().getAuthor().get().getMention();
        System.out.println(autm);
        System.out.println(sessions.get(message).p1m.getMention());
        if (!sessions.get(message).connectedBoth) {
            if (autm.equalsIgnoreCase(sessions.get(message).p1m.getMention())) {
                System.out.println("1");
                sessions.get(message).p1c = true;
            }
            if (autm.equalsIgnoreCase(sessions.get(message).p2m.getMention())) {
                sessions.get(message).p2c = true;
            }
            if ((sessions.get(message).p2c) && (sessions.get(message).p1c)) {
                sessions.get(message).connectedBoth = true;
                sessions.get(message).mainMessage = sessions.get(message).mainGroup.block().createMessage("Both player is connected in game, code: " +
                        message + "\nPlayer1: " + sessions.get(message).p1m.getMention() + "\t not join :red_circle:" +
                        "\nPlayer2: " + sessions.get(message).p2m.getMention() + "\t not join :red_circle:").block();
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
        channel.block().createMessage("Game create, for auth answer on message in you private message. Session code: " + scode).block();
        member1.getPrivateChannel().block().createMessage("Hi, u invited in game. For connected and auth, send: !connect " + scode).block();
        member2.getPrivateChannel().block().createMessage("Hi, u invited in game. For connected and auth, send: !connect " + scode).block();
    }

    public static void main(String[] args) {

        DiscordClient client = DiscordClient.create("MTA0NDE3MjIyMDU4NDQzNTczMg.G1O4QH.vwr16iNWcMeLailh4ydCEAismLiS4mP4h-sCrU");
        client.gateway().setEnabledIntents(IntentSet.all()).login().flatMap(gateway -> {
            Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                            Mono.fromRunnable(() -> {
                                final User self = event.getSelf();
                                System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
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
