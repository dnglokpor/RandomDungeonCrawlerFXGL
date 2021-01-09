package Customs.Exceptions;

import java.lang.Exception;

/** NullActionArgumentException:
 * this exception will be thrown if the value of a passed Action for setting any component of the
 * action set is null.
 */
public class NullActionArgumentException extends Exception{

    public NullActionArgumentException(String errMessage){
        super(errMessage);
    }
}
