package com.woderbar.domain.exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class EscapeUtils {

    /**
     * Escape String in order to avoid log injection vulnerability
     * It uses toString first to stringify
     *
     * @param s String to escape
     * @return escaped string
     */
    public static String escapeForLog(Object s) {
        return s == null ? null : s.toString().replaceAll("[\n\r]", "_");
    }

    /**
     * Escape List of String in order to avoid log injection vulnerability
     * It uses toString first to stringify
     *
     * @param s String list to escape
     * @return escaped string listr
     */
    public static List<String> escapeForLog(Collection<Object> s) {
        return s == null ? null : s.stream().map(EscapeUtils::escapeForLog).toList();
    }

    /**
     * Escape List of String in order to avoid log injection vulnerability
     * It uses toString first to stringify
     *
     * @param s String list to escape
     * @return escaped string listr
     */
    public static List<String> escapeForLog(Object[] s) {
        return s == null ? null : Arrays.stream(s).map(EscapeUtils::escapeForLog).toList();
    }

}
