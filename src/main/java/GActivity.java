import java.util.ArrayList;
import java.util.List;

public class GActivity {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getContr() {
        return contr;
    }

    private String[] contr;
    private void Initial(int id, String name, String[] contrs){
        this.id =id;
        this.name = name;
        contr=contrs;

    }
    private GActivity(){}
    public static ArrayList<String> getActivityList(){
        ArrayList<String> actilist = new ArrayList<>();
        int n=1;
        try {
            while (!getActivitybyId(n).equals(null)) {
                actilist.add(getActivitybyId(n));
                n++;
            }
        }catch (NullPointerException e){return actilist;}
        return actilist;
    }
    public  static String getActivitybyId(int id){
        String name= null;
        switch (id) {
            case 1:{
                name="Attack";
                break;}
            case 2:{
                name="Block";
                break;}
            case 3:{
                name="Dodge";
                break;}
            case 4:
            {
                name="Taunt";
                break;}
            case 5:
            {
                name="Catch";
                break;}
        }
        return name;
    }
   public GActivity(String name){
        switch (name) {
            case "Attack":{
                Initial(1,name,new String[]{"Block","Dodge"});
                break;}
            case "Block":{
                Initial(2,name,new String[]{"Catch","Taunt"});
                break;}
            case "Dodge":{
                Initial(3,name,new String[]{"Taunt"});
                break;}
            case "Taunt":
            {
                Initial(4,name,new String[]{"Catch","Attack"});
                break;}
            case "Catch":
            {
                Initial(5,name,new String[]{"Attack","Dodge"});
                break;}
        }
        }


}
