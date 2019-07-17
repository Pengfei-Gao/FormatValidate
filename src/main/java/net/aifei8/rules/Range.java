package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class Range implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException{
        if(args.length < 2){
            return;
        }
        int v = Integer.parseInt(value);
        int min = Integer.parseInt(args[0].trim());
        int max = Integer.parseInt(args[1].trim());
        if(!(v >= min && v <= max)){
            throw new FormatValidateException(String.format("parameter %s must more than %s and less than %s",
                    key, args[0], args[1]), 1);
        }
    }
}