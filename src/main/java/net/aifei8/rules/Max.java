package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class Max implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException{
        if(args.length < 1) return;

        if(Integer.parseInt(value) > Integer.parseInt(args[0].trim()))
            throw new FormatValidateException(String.format("parameter %s must less than %s", key, args[0]), 1);

    }
}