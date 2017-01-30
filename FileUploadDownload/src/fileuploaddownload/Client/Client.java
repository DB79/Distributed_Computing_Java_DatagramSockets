import java.io.*;

public class Client {
  static String username=""; 
  static String filename="";  
  static String path="";   
  static String action="";
  static ClientHelper helper;
  static InputStreamReader is = new InputStreamReader(System.in);
  static BufferedReader br = new BufferedReader(is);

  public static void main(String[] args) {
    try {

      boolean done = false;
      String message, echo, returnAns="";

      while (!done) {
        menu();

        switch(action){
          case "LOGIN":     returnAns = helper.login(username);
                            break;
          case "LOGOUT":    returnAns = helper.logout();
                            done = false;
                            break;
          case "UPLOAD":    returnAns = helper.upload(filename, path);
                            break;
          case "DOWNLOAD":  returnAns = helper.download(filename, path);
                            break;
          default:          returnAns = "INVALID REQUEST";
        }

        System.out.println(returnAns.trim());
      } 
    }   
    catch (Exception ex) {
      ex.printStackTrace( );
    }
  }

  public static void menu(){
    try{
      System.out.println("LOGIN OR LOGOUT OR UPLOAD OR DOWNLOAD");
      action = br.readLine();

      if(action.equals("LOGIN")|| action.equals("LOGOUT")){
        System.out.println("What is your username");
        username = br.readLine();
      }
      else{
        System.out.println("Filename");
        filename = br.readLine();
        System.out.println("Path");
        path = br.readLine();
      }
      helper = new ClientHelper();
    }
    catch(Exception e){
      System.out.println(e);
    }   
  }
}
