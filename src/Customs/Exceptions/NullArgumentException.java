package Customs.Exceptions;

import  java.lang.Exception;

/** NonPositiveValueArgumentException:
 * this exception is to signal to the user of the package when a passed argument is null
 * when it should never be.
 */
public class NullArgumentException extends Exception{

    public NullArgumentException(String errMessage){
        super(errMessage);
    }
}
