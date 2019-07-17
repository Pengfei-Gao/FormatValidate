package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class In implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException {
        if(args.length < 1){
            return;
        }

        Boolean in = false;
        for (String param:args) {
            if(param.trim() == value){
                in = true;
                break;
            }
        }
        if(!in) throw new FormatValidateException(String.format("parameter %s must in %s", key, args.toString()), 1);
    }
}