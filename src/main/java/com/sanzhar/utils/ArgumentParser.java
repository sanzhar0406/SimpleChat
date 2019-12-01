/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.utils;

import com.sanzhar.utils.Constants;

/**
 *
 * @author Sanzhar
 * class for parsing command line arguments
 *
 */
public class ArgumentParser {


    /**
     * isValid method, checks for validity of command line arguments
     *
     * @param args arguments array
     * @return a <code> boolean </code>
     * @throws IllegalArgumentException in case of invalid input
     */
    public static boolean isValid(String[] args) {
        if (isEmpty(args) || firstArgumentUnsupported(args)) {
            throw new IllegalArgumentException(Constants.invalidArgument);
        }
        return true;
    }

    /**
     * isEmpty method, checks length of the array
     *
     * @param args arguments array
     * @return a <code> boolean </code>
     */
    private static boolean isEmpty(String[] args) {
        return args.length == 0;
    }

    /**
     * firstArgumentUnsupported method, checks if first argument is either SINGLE or SEPARATE
     *
     * @param args arguments array
     * @return a <code> boolean </code>
     */
    private static boolean firstArgumentUnsupported(String[] args) {
        String firstArgument = args[0];
        if (!firstArgument.equals(GameType.SINGLE.name()) || !firstArgument.equals(GameType.SEPARATE.name())) {
            return false;
        }
        return true;
    }

    /**
     * getType method, parses game type
     *
     * @param args arguments array
     * @return a <code> GameType </code>
     */
    public static GameType getType(String[] args) {
        String firstArgument = args[0];
        if (firstArgument.equals(GameType.SINGLE.name())) {
            return GameType.SINGLE;
        } else {
            return GameType.SEPARATE;

        }
    }
}
