package fatalgamer.com.mradioto.entities;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by FATALGAMER on 11/29/2016.
 */

public class Country {

    public String name;
    public String id;
    public String flag;

    public Country(){

    }

    public Country(String name, String id,String flag) {
        this.name = name;
        this.id=id;
        this.flag=flag;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();


        result.put("name", name);
        result.put("flag",flag);


        return result;
    }
}
