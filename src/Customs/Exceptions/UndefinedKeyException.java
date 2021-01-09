package Customs.Exceptions;

import java.lang.Exception;

/**
 * this Exception pallies the lack of information from a hashtable.get()
 * method call in the case of a WorldBasic.Units.Body objects. null is
 * considered to be the value that only empty slots should return so
 * this exception will be thrown everytime the requested key doesn't exist.
 */
public class UndefinedKeyException extends Exception{

    private static final long serialVersionUID = -5043699426753125595L;

    public UndefinedKeyException(String errMessage){
        super(errMessage);
    }
}

