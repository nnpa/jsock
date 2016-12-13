/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JRequestValidator {
    /**
     * json object from socket message
     */
    JSONObject json;
    /**
     * validator rules
     */
    String[][] rules;
    
    //List<String> errors =  new ArrayList<>();
    
   JSONObject   object = new JSONObject();
   JSONArray  errors   = new JSONArray();
   

    /**
     * 
     * @param json
     * @param rules
     */
    public JRequestValidator(JSONObject json, String[][] rules) {
        this.json   = json;
        this.rules  = rules;
    }
    
    public JSONObject check(){
        
        int length = rules.length - 1;
        
        for(int i = 0;i <= length;i++){

            if(rules[i][0].equals("require")){
                checkRequire(rules[i]);
            }
        }
        
        if(errors.isEmpty())
            return null;
        
        object.put("errors", errors);
        
        return object;
    }
    
    private void checkRequire(String[] row){
        String[] parts = row[1].split(",");
        int length     = parts.length - 1;
        
        for(int i = 0;i<=length;i++){
            if(json.get(parts[i]) == null){
                errors.add("Field " + parts[i] + " don't exists in socket data");
            }
        }
    }
}
