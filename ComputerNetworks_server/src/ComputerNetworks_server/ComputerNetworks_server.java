/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComputerNetworks_server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Ibraheem
 */
public class ComputerNetworks_server implements ActionListener {
    
    int Highscore=0;
    String UserName = "";
 Database database = new Database();
 Timer timer = new Timer(1,this);
 String Message;
 String difficulty;
 String answer;
 String User;

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws Exception {
        ComputerNetworks_server server = new ComputerNetworks_server();
            server.run();  
        // TODO code application logic here
    }
      public void run() throws Exception {
    database.conn();
    ServerSocket SRVSocket = new ServerSocket(444);
    Socket sock = SRVSocket.accept();
    InputStreamReader IR = new InputStreamReader(sock.getInputStream());
    BufferedReader BR = new BufferedReader(IR);
    System.out.println("Server is running");
    //timer.start();

    Message = BR.readLine();
    close(Message);
    System.out.println(Message);
    PrintStream PS = new PrintStream(sock.getOutputStream());
    ResultSet rs;
    
    if(Message != null){
       PS.println("Message recieved");

    }
    
   
    if(Message != null && Message.equals("Username")){
      User = BR.readLine();
          close(User);
      UserName=User;
      rs=database.EQ("Select highscore from Player where Username like '"+User+"'");
      
      if(!rs.next()){
      database.EU(" Insert into Player(Username,Highscore) values ('"+User+"',0)");
      }
       
      }
     rs=database.EQ("Select * from Player where Username like '"+UserName+"'");
    if(rs.next()){
            PS.println(rs.getString("HighScore"));

    }
          System.out.println("Hello");
    Message=BR.readLine();
    close(Message);
    if(Message.equals("Easy")){
    rs=database.EQ("SELECT * FROM Questions where difficulty like 'Easy' " +
"ORDER BY RAND() " +
"LIMIT 5");
    }
    else if(Message.equals("Medium")){
    rs=database.EQ("SELECT * FROM Questions where difficulty like 'Medium' " +
"ORDER BY RAND() " +
"LIMIT 5");
    }
    else{
    rs=database.EQ("SELECT * FROM Questions where difficulty like 'Hard' " +
"ORDER BY RAND() " +
"LIMIT 5");
    }
    int counter =0;
     while(rs.next()){
    PS.println(rs.getString("Question"));
    PS.println(rs.getString("option1"));
    PS.println(rs.getString("option2"));
    PS.println(rs.getString("option3"));
    PS.println(rs.getString("answer"));
    PS.println(rs.getString("Difficulty"));
    counter++;
   difficulty = BR.readLine();
   close(difficulty);
   answer = BR.readLine();
       close(answer);
   System.out.println("The answer is "+answer);
   if(answer.equals("Correct")){
       if(difficulty.equals("Easy")){
   Highscore+=10;
       }
     else if(difficulty.equals("Medium")){
   Highscore+=25;
     }
     else{
        Highscore+=50;
     }
      
    }
    }
    int scoreFromDatabase=0;
    rs= database.EQ("select * from Player where Username like '"+UserName+"'");
    while(rs.next()){
    scoreFromDatabase = Integer.parseInt(rs.getString("HighScore"));
    }
    System.out.println("The score from db is "+scoreFromDatabase +" the highscore is "+Highscore);
    if(scoreFromDatabase<Highscore){
       database.EU("Update Player set Highscore = " +Highscore+" where UserName like '" + UserName +"'");
    }
    /*
    Message = BR.readLine();
    if(Message.equals("Restart")){
    try{
    run();
    }catch(Exception e){
    
    }
    }
            */
    }
      
      public void actionPerformed(ActionEvent ae) {
          
      if(ae.getSource() == timer && 
       (Message.equals("closing connection") || difficulty.equals("closing connection") || answer.equals("closing connection"))){
          
          System.out.println("client closed connection");
          try {
              this.finalize();
          } catch (Throwable ex) {
              Logger.getLogger(ComputerNetworks_server.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
          
      }
     
      
      public void close(String s) {
          
      if(!s.equals(null) && s.equals("closing connection")){
          
          System.out.println("client closed connection");
          try {
              this.finalize();
          } catch (Throwable ex) {
              Logger.getLogger(ComputerNetworks_server.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
          
      }
    
}
