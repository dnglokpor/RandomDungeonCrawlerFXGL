package Customs.Exceptions;

import java.lang.Exception;

/**
 * the game requires the use of many arrays that represent stats of a
 * a unit or an object. these arrays must have the same length as the
 * defines.Constants.STATS array. if the passed argument array is
 * shorter than the reference STATS array, a
 * ArrayIndexOutOfBoundsException will be thrown and the program will
 * crash. This custom exception rides on that and gives more detail
 * to the user on what happened.
 */
public class WrongSizeStatsArrayException extends Exception{

    // important
    private static final long serialVersionUID = 7216819163941954795L;

    // construct
    public WrongSizeStatsArrayException (String errorMessage, Throwable err){
        super(errorMessage);
    }
}

