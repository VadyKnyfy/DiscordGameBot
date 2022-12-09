public class GTaunt extends GameMethods {

    private String name;
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public GTaunt(String name){
       switch (name){
           case "DD":
               this.name = name;
               id = 1;
               break;
           case "MS":
               this.name = name;
               id = 2;
               break;
           case "Heal":
               this.name = name;
               id = 3;
               break;
       }
    }
    public void Active(GPlayer user, GPlayer uses){
        switch (name){
            case "DD":
                user.setDamagemod(user.getCharacter().getBasedamage());
                if(uses.getStatus()=="Stunned") //disable perm-stun
                    uses.setStatus("Normal");
                break;
            case "MS":
                if(uses.getStatus()=="Normal")
                    uses.setStatus("Stunned");
                else {
                    user.setStatus("Stunned");
                    uses.setStatus("Normal");
                }
                break;
            case "Heal":
                user.getCharacter().setHealthpoint(GameMethods.round(user.getCharacter().getHealthpoint()+(Math.random()*(user.getCharacter().getBasehealthpoint()/100*30)),1));
                if(user.getCharacter().getHealthpoint()>user.getCharacter().getBasehealthpoint()){
                    user.getCharacter().setHealthpoint(user.getCharacter().getBasehealthpoint());
                }
                if(uses.getStatus()=="Stunned")
                    uses.setStatus("Normal");
                break;
        }
    }

}
