package net.indrix.arara.model.email.exceptions;

public class SendEmailException extends Exception {

    public SendEmailException(){
        super();
    }
    
    public SendEmailException(String msg){
        super(msg);
    }
}
