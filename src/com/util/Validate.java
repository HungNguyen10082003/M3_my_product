package util;

public class Validate {
    public static boolean checkName(String regex){
        String nameRegex = "^[A-Z][a-z]+(\\s\\w+)*$";
        return regex.matches(nameRegex);
    }
    public static boolean checkDes(String regex){
        String nameRegex = "^\\w+(\\s\\w+)*$";
        return regex.matches(nameRegex);
    }
}