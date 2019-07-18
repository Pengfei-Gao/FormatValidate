package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

import java.util.Arrays;

public class Email  implements FormatValidateRuleInterface {

    private String regx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private String regxFormat = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@%s$";

    public void Validate(String key, String value, String args[]) throws FormatValidateException {
        Boolean ret = false;
        do{
            if(args.length < 1){  // 未传入校验参数
                ret = value.trim().matches(regx);
                break;
            }
            for(String domain:args){
                ret = value.matches(String.format(this.regxFormat, domain));
                if(ret) break;
            }
            if(ret) break;

        }while (false);
        if(!ret) throw new FormatValidateException(String.format("parameter %s must be a valid email, like : xxx@%s", key, Arrays.asList(args)), 1);
    }


}