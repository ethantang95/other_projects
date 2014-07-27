import java.io.*;

class SimpleTools{

  /**
   * 
   * 
   * 
   * 
   */
  
  public static String[] fileInput (String fileName){
    String[] toReturn;
    String line;
    int totalLine = 0;
    
    try{
      FileReader fileInput = new FileReader(fileName);
      BufferedReader fileReader = new BufferedReader (fileInput);
      
      while ((line = fileReader.readLine()) != null){
        totalLine++;
      }
      fileReader.close();
    }
    catch(IOException error){    
      System.out.println("File not found, remember to add the extension (eg .txt)");
      toReturn = null;
      return toReturn;
    }
    
    toReturn = new String[totalLine];
    
    try{
      FileReader fileInput = new FileReader(fileName);
      BufferedReader fileReader = new BufferedReader (fileInput);
      
      for (int i = 0; i < totalLine; i++){
        line = fileReader.readLine();
        toReturn[i] = line;
      }
      fileReader.close();
    }
    catch(IOException error){    
      System.out.println("File not found, remember to add the extension (eg .txt)");
      return toReturn;
    }
    
    return toReturn;
    
  }
  
  public static void fileOutput (String toWrite, String outputName){
    String[] write = {toWrite};
    fileOutput(write, outputName);
  }
  
  public static void fileOutput (String[] toWrite, String outputName){
  
    //check if an extension is available
    boolean extension = false;
    for (int i = 0; i < outputName.length(); i++){
      if (outputName.charAt(i) == '.'){
        extension = true;
        break;
      }
    }
    
    if (!(extension))
      outputName = outputName + ".txt";
      
     try{    
      FileWriter fileOutput = new FileWriter (outputName);
      PrintWriter fileWriter = new PrintWriter (fileOutput);
      
      for (int i = 0; i < toWrite.length; i++){
        fileWriter.println (toWrite[i]);
      }
      fileWriter.close();
     }
    catch(IOException error){
      System.out.println("error at fileOutput in SimpleTools");
      System.exit(1);
    }
  }
  
  public static String[] stringSplitter (String toSplit, char splitPoint){
    
    char[] split = {splitPoint};
    return stringSplitter (toSplit, split, true, -1);
    
  }
  
  public static String[] stringSplitter (String toSplit, char splitPoint, boolean splitBeforePoint){
    
    char[] split = {splitPoint};
    return stringSplitter (toSplit, split, splitBeforePoint, -1);
    
  }
  
  public static String[] stringSplitter (String toSplit, char[] splitPoints){
    
    return stringSplitter (toSplit, splitPoints, true, -1);
      
  }
    
  //splitting after the point will make it that the split will be in the previous line
  public static String[] stringSplitter (String toSplit, char[] splitPoints, boolean splitBeforePoint){
  
    return stringSplitter (toSplit, splitPoints, splitBeforePoint, -1);
    
  }
  
  
  public static String[] stringSplitter (String toSplit, char splitPoint, boolean splitBeforePoint, int maxTime){
    
    char[] split = {splitPoint};
    return stringSplitter (toSplit, split, splitBeforePoint, maxTime);
    
  }
  
  public static String[] stringSplitter (String toSplit, char[] splitPoints, boolean splitBeforePoint, int maxTime){
  
    //if the string is empty
    if (toSplit.length() == 0){
      String[] toReturn = new String[0];
      return toReturn;
    }
    
    int subStringPoint = 0, splitCount = 0, times = 0;
    String[] splits = new String[1000];    
    
    //add a split end to indentify the end of a string
    toSplit = toSplit + '`';
    
    for (int i = 0; i < toSplit.length(); i++){
      for (int j = 0; j < splitPoints.length; j++){
        if (toSplit.charAt(i) == splitPoints[j] || toSplit.charAt(i) == '`'){
          if (splitBeforePoint){
            splits[splitCount] = toSplit.substring(subStringPoint, i);
            subStringPoint = i;
          }
          else{
            splits[splitCount] = toSplit.substring(subStringPoint, i+1);
            subStringPoint = i+1;
          }
          splitCount++;
          times++;
          if (toSplit.charAt(i) == '`' || times >= maxTime)
            break;
        }
      }
    }
    
    //Trim the array
    int filled = 0;
    for (int i = 0; i < splits.length; i++){
      if (splits[i] != null)
        filled++;
      else
        break;
    }
    String[] splitsTrimmed = new String[filled];
    for (int i = 0; i < splitsTrimmed.length; i++){
      splitsTrimmed[i] = splits[i];
    }
    
    //cuts out the '`' at the last element of the array
    if (splitsTrimmed[splitsTrimmed.length-1].charAt(splitsTrimmed[splitsTrimmed.length-1].length()-1) == '`')
      splitsTrimmed[splitsTrimmed.length-1] = splitsTrimmed[splitsTrimmed.length-1].substring(0, splitsTrimmed[splitsTrimmed.length-1].length()-1);
    
    return splitsTrimmed;
  }
  
  public static String[] splitPointRemover(String[] toFix, char splitPoint){
    char[] split = {splitPoint};
    return splitPointRemover(toFix, split);
  }
  
  public static String[] splitPointRemover(String[] toFix, char[] splitPoints){
    
    for (int i = 0; i < toFix.length; i++){
      for (int j = 0; j < toFix[i].length(); j++){
        for (int k = 0; k < splitPoints.length; k++){
          if (toFix[i].charAt(j) == splitPoints[k]){
            if (j == 0){
              toFix[i] = toFix[i].substring(1,toFix[i].length());
              break;
            }
            else{
              toFix[i] = toFix[i].substring(0, toFix[i].length()-1);
              break;
            }
          }
        }
      }
    }
    
    return toFix;
    
  }
  
  public static void sleep (double timeSeconds){
    int time = (int)(timeSeconds*1000);
    try{
      Thread.sleep(time);  
    }
    catch (InterruptedException ie){
      System.out.println(ie.getMessage());
      System.out.println("Something went wrong at SimpleTools.sleep method");
    }
  }
  
  public static double[] sorter(double[] toSort){
    
    int slots = toSort.length;
    
    double[] newArray = new double[slots];
    
    //clear all the array values
    for (int i = 0; i < slots; i++){
      newArray[i] = 1.79769313486231570e+308d;      
    }
    
    //main sorting algor
    for (int i = 0; i < slots; i++){
      for (int j = 0; j < slots; j++){
        if (toSort[i] < newArray[j]){
          //if a possible space is found, move all the values after it over by 1 place
          for (int k = slots-1; k > j; k--){
            newArray[k] = newArray[k-1];
          }
          newArray[j] = toSort[i];
          break;
        }
      }
    }
    return newArray;    
  }
  
  public static int[] sorter(int[] toSort){
    
    int slots = toSort.length;
    
    int[] newArray = new int[slots];
    
    //clear all the array values
    for (int i = 0; i < slots; i++){
      newArray[i] = 2147483647;      
    }
    
    //main sorting algor
    for (int i = 0; i < slots; i++){
      for (int j = 0; j < slots; j++){
        if (toSort[i] < newArray[j]){
          //if a possible space is found, move all the values after it over by 1 place
          for (int k = slots-1; k > j; k--){
            newArray[k] = newArray[k-1];
          }
          newArray[j] = toSort[i];
          break;
        }
      }
    }
    return newArray;    
  }
  
  public static String[] stringArrayTrim(String[] toTrim){
        
    int count = 0;
    while(toTrim[count] != null){
      count++;
    }
    String[] toReturn = new String[count];
    for (int i = 0; i < count; i++){
      toReturn[i] = toTrim[i];
    }
    return toReturn;
  }
}