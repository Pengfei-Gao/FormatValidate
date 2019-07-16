package net.aifei8;

public class FormatValidateException extends Exception {

    private String message;

    private int code;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public FormatValidateException(String message, int Code){
        super();
        this.message = message;
        this.code = Code;
    }


}
