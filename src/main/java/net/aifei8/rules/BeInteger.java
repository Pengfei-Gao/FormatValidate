package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class BeInteger implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException{
        Boolean ret = true;
        for(int i=value.length();--i>=0;){
            int chr=value.charAt(i);
            if(chr<48 || chr>57){
                ret = false;
                break;
            }
        }
        if(!ret)
            throw new FormatValidateException(String.format("parameter %s must be integer", key), 1);
    }

}
