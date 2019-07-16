package net.aifei8.rules;

import net.aifei8.FormatValidateRuleInterface;
import net.aifei8.FormatValidateException;



public class Min implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException{
        if(args.length < 1){
            return;
        }
        if(Integer.parseInt(value) < Integer.parseInt(args[0])){
            throw new FormatValidateException(String.format("parameter %s must more than %s", key, args[0]), 1);
        }
    }
}