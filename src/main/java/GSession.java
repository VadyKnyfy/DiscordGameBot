import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.PrivateChannel;
import reactor.core.publisher.Mono;

public class GSession {

    public GSession(Member p1m, Member p2m, Mono<MessageChannel> mainGroup) {
        this.p1m = p1m;
        this.p2m = p2m;
        this.mainGroup = mainGroup;
    }

    Member p1m;
    Member p2m;
    Mono<MessageChannel> mainGroup;
    Message mainMessage;
    GPlayer p1;
    GPlayer p2;
    int round =0;
    Boolean connectedBoth = false;
    Boolean joinBoth = false;
    Boolean readyBoth = false;
    Boolean p1c = false;
    Boolean p1j = false;
    Boolean p1r = false;
    Boolean p2c = false;
    Boolean p2j = false;
    Boolean p2r = false;
    GActivity a1;
    GActivity a2;
    public Member getP1m() {
        return p1m;
    }

    public Member getP2m() {
        return p2m;
    }

    public Mono<MessageChannel> getMainGroup() {
        return mainGroup;
    }

    public Message getMainMessage() {
        return mainMessage;
    }

    public void setMainMessage(Message mainMessage) {
        this.mainMessage = mainMessage;
    }

    public GPlayer getP1() {
        return p1;
    }

    public void setP1(GPlayer p1) {
        if(!joinBoth)
        this.p1 = p1;
    }

    public GPlayer getP2() {
        return p2;
    }

    public void setP2(GPlayer p2) {
        if(!joinBoth)
        this.p2 = p2;
    }

    public Boolean getConnectedBoth() {
        return connectedBoth;
    }

    public void setConnectedBoth(Boolean connectedBoth) {
        this.connectedBoth = connectedBoth;
    }

    public Boolean getJoinBoth() {
        return joinBoth;
    }

    public void setJoinBoth(Boolean joinBoth) {
        this.joinBoth = joinBoth;
    }

    public Boolean getReadyBoth() {
        return readyBoth;
    }

    public void setReadyBoth(Boolean readyBoth) {
        this.readyBoth = readyBoth;
    }

    public Boolean getP1c() {
        return p1c;
    }

    public void setP1c(Boolean p1c) {
        this.p1c = p1c;
    }

    public Boolean getP1j() {
        return p1j;
    }

    public void setP1j(Boolean p1j) {
        this.p1j = p1j;
    }

    public Boolean getP1r() {
        return p1r;
    }

    public void setP1r(Boolean p1r) {
        this.p1r = p1r;
    }

    public Boolean getP2c() {
        return p2c;
    }

    public void setP2c(Boolean p2c) {
        this.p2c = p2c;
    }

    public Boolean getP2j() {
        return p2j;
    }

    public void setP2j(Boolean p2j) {
        this.p2j = p2j;
    }

    public Boolean getP2r() {
        return p2r;
    }

    public void setP2r(Boolean p2r) {
        this.p2r = p2r;
    }

    public GActivity getA1() {
        return a1;
    }

    public void setA1(GActivity a1) {
        this.a1 = a1;
    }

    public GActivity getA2() {
        return a2;
    }

    public void setA2(GActivity a2) {
        this.a2 = a2;
    }
public int play(){
        new Gproccess(p1,a1,p2,a2);
        round++;
        p1r=false;
        p2r=false;
        if (p1.getCharacter().getHealthpoint()<=0 && p2.getCharacter().getHealthpoint()>0 )
            return  2;
        if (p2.getCharacter().getHealthpoint()<=0 && p1.getCharacter().getHealthpoint()>0 )
        return  1;
        if (p1.getCharacter().getHealthpoint()<=0 && p2.getCharacter().getHealthpoint()<=0 )
        return  3;
    return 0;
}

}
