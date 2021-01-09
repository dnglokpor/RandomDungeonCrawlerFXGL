package Customs.Exceptions;

import  java.lang.Exception;

/** NonPositiveValueArgumentException:
 * this exception is to signal to the user of the package when a passed argument is negative
 * when it was expected positive or zero. this pattern can be seen in classes that contains
 * attributes that should be raised and never diminished.
 */
public class NegativeValueArgumentException extends Exception{

    public NegativeValueArgumentException(String errMessage){
        super(errMessage);
    }
}
