import java.util.Arrays;

public class Gproccess {
    private void getDamage(GPlayer victim, GPlayer offender ){
        victim.getCharacter().setHealthpoint(victim.getCharacter().getHealthpoint()-offender.getCharacter().getBasedamage()-offender.getDamagemod());
        if(offender.getDamagemod()>0) offender.setDamagemod(0);
    }
    private static int  wholoser(GPlayer p1, GActivity a1, GPlayer p2, GActivity a2){
        if(p1.getStatus()=="Normal"&&p2.getStatus()=="Normal"){ //check players on stunned status
            if(a1.getId()!=a2.getId())
            {
                if(Arrays.stream(a1.getContr()).anyMatch(s -> s.equals(a2.getName()))) {
                    return 1; // if action player 1 defeat, all action have contr action in normal
                }
                if(Arrays.stream(a2.getContr()).anyMatch(s -> s.equals(a1.getName()))) {
                    return 2;// if action player 2 defeat, all action have contr action in normal
                }
            }
            else{
               return 3;}
        }
        else{
            if((p1.getStatus()!="Normal") && (p2.getStatus()!="Normal")){ //if all player be in stunned status skip round
                p1.setStatus("Normal");
                p2.setStatus("Normal");
                return 0;

            }
            else{
                if(p1.getStatus()!="Normal")
                    return 1;//if player 1 in stunned status, he automatically lose round
                if(p2.getStatus()!="Normal")
                    return 2;//if player 2 in stunned status, he automatically lose round
            }
        }
        return 0;
    }
public Gproccess(GPlayer p1, GActivity a1, GPlayer p2, GActivity a2){
    int who;
    who= wholoser(p1,a1,p2,a2);
    System.out.println(who);
    switch (who) {
        case 1: {
            switch (a2.getName()) {
                case "Attack":
                   getDamage(p1,p2);
                   p1.setStatus("Normal");
                    break;
                case "Catch":
                    if(p1.getStatus()=="Normal")
                        p1.setStatus("Stunned");
                    else {
                        p2.setStatus("Stunned");
                        p1.setStatus("Normal");
                    }
                    break;
                case "Taunt":{
                    p2.getTaunt().Active(p2,p1);
                    break;
                }
            }
        break;
        }
        case 2: {
            switch (a1.getName()) {
                case "Attack":
                    getDamage(p2,p1);
                    p2.setStatus("Normal");
                    break;
                case "Catch":
                    if(p2.getStatus()=="Normal")
                        p2.setStatus("Stunned");
                    else {
                        p1.setStatus("Stunned");
                        p2.setStatus("Normal"); }
                    break;
                case "Taunt":{
                    p1.getTaunt().Active(p1,p2);
                    break;
                }
        }
        break;
        }
        case 3:{
            switch (a1.getName()){
                case "Attack":
                    getDamage(p1,p2);
                    getDamage(p2,p1);
                    break;
                case "Taunt":{
                    p1.getTaunt().Active(p1,p2);
                    p2.getTaunt().Active(p2,p1);
                }
            }
            break;
        }
    }
    }
}

