import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FindJsonData {

    public JSONArray getJsonArray(String value, JSONObject object)
    {
        if (object.get(value) instanceof JSONArray)
        {
            return (JSONArray) object.get(value);
        }
        else
        {
            System.out.println("Value : " + value + " does not exist or is not a JSONARRAY");
            return null;
        }
    }

    public String getJsonString(String value, JSONObject object)
    {
        if (object.get(value) instanceof String)
        {
            return (String) object.get(value);
        }
        else
        {
            System.out.println("Value : " + value + " does not exist or is not a String");
            return null;
        }
    }

    public Long getJsonLong(String value, JSONObject object)
    {
        if (object.get(value) instanceof Long)
        {
            return (Long) object.get(value);
        }
        else
        {
            System.out.println("Value : " + value + " does not exist or is not a Long");
            return null;
        }
    }

    public Integer getJsonInteger(String value, JSONObject object)
    {
        if (object.get(value) instanceof Integer)
        {
            return (Integer) object.get(value);
        }
        else
        {
            System.out.println("Value : " + value + " does not exist or is not a Integer");
            return null;
        }
    }
}
