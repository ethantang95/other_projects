import java.io.*;

class Input{

  public static String inputString (){
    return inputString (-1, "");
  }
  
  public static String inputString (int length, String errorMsg){
    
    InputStreamReader keyInput = new InputStreamReader(System.in);
    BufferedReader userInput = new BufferedReader(keyInput);
    String input = "";
    
    if (errorMsg.equals("") && length > 0)
      errorMsg = ("This program is asking for an input no more than " + length + " characters, please try again.");
    
    try{
      input = userInput.readLine();
      if (length > 0 && input.length() > length){
        System.out.println (errorMsg);
        return inputString(length, errorMsg);
      }
    }
    catch (IOException error){
      System.out.println ("input error occured at Input.inputString, recursing to try again");
      return inputString();
    }
    return input;
  }
  
  public static boolean inputYesNo (){
    String[] options = {"yes", "no"};
    String toReturn = inputStringWithOptions(options, "Please input only 'yes' or 'no'");
    if (toReturn.equals("yes"))
      return true;
    else
      return false;
  }
  
  public static boolean inputYesNo (String errorMsg){
    String[] options = {"yes", "no"};
    String toReturn = inputStringWithOptions(options, errorMsg);
    if (toReturn.equals("yes"))
      return true;
    else
      return false;
  }
  
  public static String inputStringWithOptions (String[] options, String errorMsg){
    return inputStringWithOptions(-1, options, errorMsg);
  }
  
  public static String inputStringWithOptions (int length, String[] options, String errorMsg){
    String input = inputString(length, "An error has occured at inputString under the Input class");
    for (int i = 0; i < options.length; i++){
      if (input.equalsIgnoreCase(options[i]))
        return input;
    }
    System.out.println (errorMsg);
    return inputStringWithOptions(length, options, errorMsg);
  }
  
  public static int inputIntMaxLimit (int maxLimit, String errorMsg){
    if (errorMsg.equals(""))
      errorMsg = ("This program is asking for a number no greater than " + maxLimit + ", please try again.");
    return inputInt(-2147483648, maxLimit, errorMsg);
  }
  
  public static int inputIntMinLimit (int minLimit, String errorMsg){
    if (errorMsg.equals(""))
      errorMsg = ("This program is asking for a number no less than " + minLimit + ", please try again.");
    return inputInt(minLimit,2147483647, errorMsg);
  }
  
  public static int inputInt (){
    return inputInt(-2147483648, 2147483647, "inputInt parameter problem, please check input.java");
  }
  
  public static int inputInt (int minLimit, int maxLimit, String errorMsg){
    if (minLimit > maxLimit){
      System.out.println ("the minLimit parameter is higher than the maxLimit parameter, swapping the two parameters.");
      SimpleTools.sleep(5);
      int temp = minLimit;
      minLimit = maxLimit;
      maxLimit = temp;
    }
    
    if (errorMsg.equals(""))
      errorMsg = ("This program is asking for a number between " + minLimit + " to " + maxLimit + ", please try again.");
    
    int toReturn = 0;
    try{
      toReturn = Integer.parseInt(inputString());
      if (toReturn > maxLimit || toReturn < minLimit){
        System.out.println (errorMsg);
        toReturn = inputInt(minLimit, maxLimit, errorMsg);
      }
    }
    catch(Exception e){
      System.out.println ("The input was not a number or something went wrong at Input.inputInt. Please try again");
      toReturn = inputInt(minLimit, maxLimit, errorMsg);
    }
    return toReturn;
  }
  
    public static double inputDoubleMaxLimit (double maxLimit, String errorMsg){
      if (errorMsg.equals(""))
        errorMsg = ("This program is asking for a number no greater than " + maxLimit + ", please try again.");
    return inputDouble(-1.79769313486231570e+308d, maxLimit, errorMsg);
  }
  
  public static double inputDoubleMinLimit (double minLimit, String errorMsg){
    if (errorMsg.equals(""))
      errorMsg = ("This program is asking for a number no less than " + minLimit + ", please try again.");
    return inputDouble(minLimit, 1.79769313486231570e+308d, errorMsg);
  }
  
  public static double inputDouble(){
    return inputDouble(-1.79769313486231570e+308d,  1.79769313486231570e+308d, "inputDouble parameter problem, please check Input.java");
  }
  
  public static double inputDouble (double minLimit, double maxLimit, String errorMsg){
    
    if (minLimit > maxLimit){
      System.out.println ("the minLimit parameter is higher than the maxLimit parameter, swapping the two parameters.");
      SimpleTools.sleep(5);
      double temp = minLimit;
      minLimit = maxLimit;
      maxLimit = temp;
    }
    
    if (errorMsg.equals(""))
      errorMsg = ("This program is asking for a number between " + minLimit + " to " + maxLimit + ", please try again.");
    
    double toReturn = 0;
    String enteredFloat;
    try{
      //toReturn = Double.valueOf(inputString());
      enteredFloat = inputString();
      if (enteredFloat.charAt(0) == '-'){
        toReturn = Double.valueOf(enteredFloat.substring (1,enteredFloat.length()));
        toReturn = toReturn*-1;
      }
      else{
        toReturn = Double.valueOf(enteredFloat);
      }
        
      toReturn = Double.valueOf(enteredFloat);
      if (toReturn > maxLimit || toReturn < minLimit){
        System.out.println (errorMsg);
        toReturn = inputDouble(minLimit, maxLimit, errorMsg);
      }
    }
    catch(Exception e){
      System.out.println ("The input was not a float or something went wrong at Input.inputDouble. Please try again");
      toReturn = inputDouble(minLimit, maxLimit, errorMsg);
    }
    return toReturn;
  }
  
  public static char inputCharLetterOnly(){
    return inputChar(true, true, "You may only enter letters, please try again");
  }
  
  public static char inputCharCapitalLetterOnly(){
    return inputChar(true, false, "You may only enter upper case letters, please try again");
  }
  
  public static char inputCharLowerCaseLetterOnly(){
    return inputChar(false, true, "You may only enter lower case letters, please try again");
  }
  
  public static char inputChar(){
    return inputChar(false, false, "Something has went wrong at Input.inputChar, recursing to try again");
  }
  
  public static char inputChar (boolean upperCaseCheck, boolean lowerCaseCheck, String errorMsg){
    String userInput = inputString(1,"You have entered more than 1 characters, please only enter 1 character");
    char toReturn;
    toReturn = userInput.charAt(0);
    if (upperCaseCheck && lowerCaseCheck){
      if (!((toReturn >= 65 && toReturn <= 90)||(toReturn >=97 && toReturn <= 122))){
        System.out.println (errorMsg);
        return inputChar(upperCaseCheck, lowerCaseCheck, errorMsg);
      }
    }
    if (upperCaseCheck && !(lowerCaseCheck)){
      if (toReturn < 65 || toReturn > 90){
        System.out.println (errorMsg);
        return inputChar(upperCaseCheck, lowerCaseCheck, errorMsg);
      }
    }
    if (lowerCaseCheck && !(upperCaseCheck)){
      if (toReturn < 97 || toReturn > 122){
        System.out.println (errorMsg);
        return inputChar(upperCaseCheck, lowerCaseCheck, errorMsg);
      }
    }
    return toReturn;
  }
}