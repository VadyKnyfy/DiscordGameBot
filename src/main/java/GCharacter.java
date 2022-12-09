public class GCharacter extends  GameMethods{
    private double healthpoint;
    private double basehealthpoint;
    private String name;
    private int basedamage;
    private int id;
    public void setHealthpoint(double healthpoint) {
        this.healthpoint = healthpoint;
    }
    public double getHealthpoint() {
        healthpoint = GameMethods.round(healthpoint,2);
        return healthpoint;
    }
    public double getBasehealthpoint() {
        return basehealthpoint;
    }
    public String getName() {
        return name;
    }

    public int getBasedamage() {
        return basedamage;
    }

    public int getId() {
        return id;
    }

    private void Initial(int id, String name, double basehpoint, int basedamage){
        this.id = id;
        this.name = name;
        this.basehealthpoint = basehpoint;
        this.healthpoint = this.basehealthpoint;
        this.basedamage = basedamage;
    }
    private GCharacter(){}

    public GCharacter(String name){
            switch (name){
                case "Knight":{
                        Initial(1,name,100,10);
                        break;
                }
            }
        }

}
