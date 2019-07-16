package net.aifei8;


import com.sun.deploy.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class FormatValidate {

    private Map<String, Class<?>> rules = new HashMap<String, Class<?>>();


    public FormatValidate addRule(String ruleName, Class<?> rule){
        this.rules.put(ruleName, rule);
        return this;
    }

    public FormatValidate(){
        this.addRule("int", net.aifei8.rules.BeInteger.class);
        this.addRule("range", net.aifei8.rules.Range.class);
        this.addRule("max", net.aifei8.rules.Max.class);
        this.addRule("min", net.aifei8.rules.Min.class);
    }


    public void validate(Map<String, String> input, Map<String, String> rules) throws Exception {

        String key = null, value = null, ruleName = null;
        String[] ruleArr = null, ruleDesc = null ,ruleArgs = null;
        FormatValidateRuleInterface checker = null;
        Class obj = null;

        for(Map.Entry<String, String> rule : rules.entrySet()){

            if(!input.containsKey(rule.getKey())){
                continue;
            }

            //规则间使用 `,` 分隔
            ruleArr = StringUtils.splitString(rule.getValue(), ",");

            for(String tmp : ruleArr){

                //解析规则 示例： range|1:2, 规则名和参数之间用 `|` 分隔
                ruleDesc = StringUtils.splitString(tmp, "|");
                if(ruleDesc.length < 1){ // 规则描述为空
                    continue;
                }

                ruleName = ruleDesc[0];
                ruleArgs = StringUtils.splitString(ruleDesc[ruleDesc.length - 1], ":");

                //反射，获取校验组件实例
                obj = Class.forName(this.rules.get(ruleName).getCanonicalName());
                checker = (FormatValidateRuleInterface)obj.newInstance();

                //开始校验
                checker.Validate(rule.getKey(), input.get(rule.getKey()), ruleArgs);
            }

        }



    }

    public static void main(String[] args) {

        FormatValidate checker = new FormatValidate();

        Map<String, String> input = new HashMap<String, String>(){
            {
                put("a", "1");
                put("c", "1");
            }
        };

        Map<String, String> rules = new HashMap<String, String>(){
            {
                put("a", "int,range|1:4");
                put("c", "int,min|0,max|2");
            }
        };

        try{
            checker.validate(input, rules);
        }catch (Exception e){
            System.out.println(">>>>>>>>>>>" + e.getMessage());
        }

    }
}