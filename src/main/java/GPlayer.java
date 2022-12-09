public class GPlayer extends GameMethods {

    private  String status = "Normal";
    private GCharacter GCharacter;
    private String Userid;

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    private GTaunt GTaunt;
    private int damagemod= 0;
    public int getDamagemod() {
        return damagemod;
    }

    public void setDamagemod(int damagemod) {
        this.damagemod = damagemod;
    }


    public GTaunt getTaunt() {
        return GTaunt;
    }

    public void setTaunt(GTaunt GTaunt) {
        this.GTaunt = GTaunt;
    }

    public GCharacter getCharacter() {
        return GCharacter;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   public GPlayer(GCharacter GCharacter, String userid){
        this.GCharacter = GCharacter;
   }
}
