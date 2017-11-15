package finished_eportfolio.straub.main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WitResponse {

    private String msgID;
    private String msg;
    private String rawJSON;
    private List<Entity> entities;

    public WitResponse(JSONObject jsonObject) {
       this.msgID = jsonObject.getString("msg_id");
       this.msg   = jsonObject.getString("_text");
       this.rawJSON = jsonObject.toString(4);
       this.entities = new ArrayList<>();

       JSONObject l_entities = jsonObject.getJSONObject("entities");

       for(Iterator<?> key=l_entities.keys(); key.hasNext();){
           String next = (String) key.next();
           JSONArray array = (JSONArray)l_entities.get(next);
           JSONObject obj  = array.getJSONObject(0);
           this.entities.add(new Entity(next, String.valueOf(obj.get("value")), obj.getDouble("confidence")));
       }

    }

    public String getMsgID() {
        return msgID;
    }

    public String getMsg() {
        return msg;
    }

    public String getRawJSON() {
        return rawJSON;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    class Entity{

        private String name;
        private String value;
        private double conf;

        public Entity(String name, String value, double conf) {
            this.name = name;
            this.value = value;
            this.conf = conf;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public double getConf() {
            return conf;
        }
    }

}
