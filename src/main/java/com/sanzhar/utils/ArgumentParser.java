/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.utils;

/**
 *
 * @author alyce
 */
public class ArgumentParser {

    private static final String invalidArgument = "InvalidArgument! Please provide one argument, either SINGLE, or SEPARATE";

    public static boolean isValid(String[] args) {
        if (isEmpty(args) || firstArgumentUnsupported(args)) {
            throw new IllegalArgumentException(invalidArgument);
        }
        return true;
    }

    private static boolean isEmpty(String[] args) {
        return args.length == 0;
    }

    private static boolean firstArgumentUnsupported(String[] args) {
        String firstArgument = args[0];
        if (!firstArgument.equals(GameType.SINGLE.name()) || !firstArgument.equals(GameType.SEPARATE.name())) {
            return false;
        }
        return true;
    }

    public static GameType getType(String[] args) {
        String firstArgument = args[0];
        if (firstArgument.equals(GameType.SINGLE.name())) {
            return GameType.SINGLE;
        } else {
            return GameType.SEPARATE;

        }
    }
}
