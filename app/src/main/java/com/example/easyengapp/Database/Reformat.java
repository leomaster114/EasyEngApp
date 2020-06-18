package com.example.easyengapp.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Reformat {
    public ArrayList<String> Split(String value) {
        String[] at = value.split("(?=@)");
        ArrayList<String> al = new ArrayList<>();
        for (String tmpAt : at) {
            String[] hyphen = tmpAt.split("(?=-)");
            for (String tmpHyphen : hyphen) {
                String[] slash = tmpHyphen.split("(?==)");
                for (String tmpSlash : slash) {
                    String[] equals = tmpSlash.split("(?==)");
                    for (String tmpEqual : equals) {
                        String[] plus = tmpEqual.split("(?=\\+)");
                        for (String tmpPlus : plus) {
                            String[] asterisk = tmpPlus.split("(?=\\*)");
                            for (String tmpAsterisk : asterisk) {
                                int n;
                                int pos = 0;
                                String x = tmpAsterisk;
                                while ((n = x.indexOf('!')) != -1) {
                                    if (tmpAsterisk.length() > n + pos + 1) {
                                        if (Character.isLetter(tmpAsterisk.charAt(n + pos + 1)) ||
                                                tmpAsterisk.charAt(n + pos + 1) == '[') {
                                            tmpAsterisk = tmpAsterisk.substring(0, n + pos) + '^' + tmpAsterisk.substring(n + pos + 1);
                                        }
                                    }
                                    x = x.substring(n + 1);
                                    pos += n + 1;
                                }
                                String[] caret = tmpAsterisk.split("(?=\\^)");
                                Collections.addAll(al, caret);
                            }
                        }
                    }
                }
            }
        }
        return al;
    }

    // hiển thị chi tiết của từ trong từ điển
    public String ShowDetail(String key, ArrayList<String> content) {
        String returnString = "";
        for (String str : content) {
            if (str.length() > 0) {
                switch (str.charAt(0)) {
                    case '@'://linh vuc, chu de
                        if (!key.equals(str.substring(1).trim())) {
                            returnString += "<br/><br/><b>" + str.substring(1).toUpperCase() + "</b>";
                        }
                        break;
                    case '-'://nghia
                        returnString += "<br/>" + str;
                        break;
                    case '/'://phien am
                        break;
                    case '*'://tu loai
                        returnString += "<br/><b><font color='#b71c1c'>" + str.substring(2) + "</font></b>";
                        break;
                    case '^':
                        returnString += "<br/><font color='3f51b5'><b>" + str.substring(1) + "</b></font>";
                        break;
                    case '=':
                        if (Character.isLetter(str.charAt(1))) {
                            returnString += "<br/><font color='#607d8b'><i>" + str.substring(1) + "</i></font>";
                        } else {
                            returnString += str;
                        }
                        break;
                    case '+':
                        returnString += "<br/><font color='#607d8b'></i>->" + str.substring(1) + "</i></font>";
                        break;
                    default:
//                        returnString += string;
                        break;
                }
            }
        }
        return returnString;
    }

    public String getMean(ArrayList<String> content) {
        String mean = "";
        for (String str : content) {
            if (str.length() > 0) {
                switch (str.charAt(0)) {
                    case '-':
                        mean += str.substring(1);
                        break;
                }
            }
        }
        return mean;
    }

    public String getPhonetic(ArrayList<String> strings) {
        for (String string : strings) {
            if (string.length() > 0) {
                if (string.charAt(0) == '/') {
                    return string + "/";
                }
            }
        }
        return "";
    }
}
