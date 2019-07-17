package net.aifei8.rules;

import net.aifei8.FormatValidateException;
import net.aifei8.FormatValidateRuleInterface;

public class Phone implements FormatValidateRuleInterface {

    public void Validate(String key, String value, String args[]) throws FormatValidateException {
        Boolean ret = false;
        for(String param:args){
            if(param.trim().toLowerCase().equals("zh_cn")){  // 中国大陆手机号
                ret = this.chinesePhone(value);
                if(ret) break;
            }
            if(param.trim().toLowerCase().equals("zh_hk")){
                ret = this.HongKongPhone(value);
                if(ret) break;
            }

        }
        if(!ret) throw new FormatValidateException(String.format("parameter %s must be a phone number", key), 1);
    }

    public Boolean chinesePhone(String phone){
        return phone.matches("^1[3|4|5|7|8|9][0-9]{9}$");
    }

    public Boolean HongKongPhone(String phone){
        return phone.matches("^(5|6|8|9)\\\\d{7}$");
    }

}