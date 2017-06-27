/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComputerNetworks_client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Ibraheem
 */
public class ComputerNetworks_client implements ActionListener {

    /**
     * @param args the command line arguments
     */
    Socket sock; 
    PrintStream PS;
    InputStreamReader IR;
    BufferedReader BR;
    Login loginPage;
    Timer timer = new Timer(30,this);
    int loggedIn = 0;
    JFrame myFrame=new JFrame();
    HomeScreen home;
    SetDifficulty setDiff;
    PlayForm myPlayForm;
    boolean playFormSetup = false;
    int count=1;
    boolean homeScreenFlag=false;
    int score=0;
    String answer="";
    int loc=0;//location of answer
    boolean connectionEstablished = false;
    
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        ComputerNetworks_client Client = new ComputerNetworks_client();
        Client.run();
    }
    
     public void run() {
        loginPage = new Login();
        myFrame = new JFrame();
        myFrame.setSize(800, 600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.addWindowListener(exitListener);
        myFrame.getContentPane().add(loginPage);
        myFrame.setVisible(true);
        home = new HomeScreen("0");

     
   
     timer.start();
     setDiff = new SetDifficulty();
     }
    
     
     public void login(String Username) throws Exception{
     PS.println("Username");
     System.out.println("The user is "+Username);
     String s = BR.readLine();
     if(s.equals("Message recieved")){
     PS.println(Username);
     }
     loginPage.setVisible(false);
     home.setPlayer(Username);
     int scoreFromServer = Integer.parseInt(BR.readLine());
     home.setHighscore(scoreFromServer);
     myFrame.getContentPane().add(home);
     }
     /*
     public void sendMessage(String message){
  
     }
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        //Connect to server
        if(ae.getSource() == timer && loginPage.getConn() == 1){
        
         loginPage.setConn(2);
         connectionEstablished = true;
   try {  sock = new Socket("localHost",444);
     PS = new PrintStream(sock.getOutputStream());   
   // PS.println("Hello to server from client");
    
     IR = new InputStreamReader(sock.getInputStream());
     BR = new BufferedReader(IR);
   }catch(Exception e){
       JOptionPane.showMessageDialog(home, "Please run server first then run client");
      myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));

   }
        }
        
        //Login Page
        if(ae.getSource()==timer &&  loginPage.getSuccessfulLogin() == 1 && loggedIn == 0){
        try {
            loggedIn=1;
            login(loginPage.getUser());
        }catch(Exception e){}
        
        }
        
        //HomeScreen
        if(ae.getSource()==timer &&  home.flag==1){
              home.setVisible(false);
              myFrame.getContentPane().add(setDiff );
        }
       
        if(ae.getSource()==timer &&  setDiff.flag!=0 && homeScreenFlag==false ){
              setDiff.setVisible(false);
              switch (setDiff.flag){
                  case 1:   myFrame.getContentPane().add(myPlayForm = new PlayForm("Easy"));
                          PS.println("Easy");

                  break;
                  case 2: myFrame.getContentPane().add(myPlayForm = new PlayForm("Intermediate"));
                          PS.println("Medium");

                      break;
                  case 3: myFrame.getContentPane().add(myPlayForm = new PlayForm("hard"));
                          PS.println("Hard");

                      break;
              }
                            setDiff.flag=5;
                            homeScreenFlag = true;
                            

        }
       
        
        //PlayForm
        if(ae.getSource()==timer &&  setDiff.flag==5 && count <=5){
        String difficulty ="";
        //Didn't answer yet
        if(playFormSetup == false){
            try{
                
        myPlayForm.setQuestion(BR.readLine());
        /*
        myPlayForm.setOption1(BR.readLine());
        myPlayForm.setOption2(BR.readLine());
        myPlayForm.setOption3(BR.readLine());
        myPlayForm.setOption4(BR.readLine());
                        */
               String option1= BR.readLine();
               String option2= BR.readLine();
               String option3=BR.readLine();
               answer=BR.readLine();
               
                loc = randomAnswerPlacement(answer, myPlayForm); //location of answer
                int j=0;//used to keep track of how many entries were made
                for(int i=0;i<4;i++){
                if(i!=loc-1 && j==0){
                switch(i){
           case 0: myPlayForm.setOption1(option1);
           j++;
            break;
            case 1: myPlayForm.setOption2(option1);
            j++;
            break;
            case 2: myPlayForm.setOption3(option1);
           j++;
            break;
            case 3: myPlayForm.setOption4(option1);
            j++;
            break;
                }
                }
                
                else if(i!=loc-1 && j==1){
                switch(i){
        case 0: myPlayForm.setOption1(option2);
           j++;
            break;
            case 1: myPlayForm.setOption2(option2);
            j++;
            break;
            case 2: myPlayForm.setOption3(option2);
           j++;
            break;
            case 3: myPlayForm.setOption4(option2);
            j++;
            break;
                }
                
                }
                
                else if(i!=loc-1 && j==2){
                switch(i){
           case 0: myPlayForm.setOption1(option3);
           j++;
            break;
            case 1: myPlayForm.setOption2(option3);
            j++;
            break;
            case 2: myPlayForm.setOption3(option3);
           j++;
            break;
            case 3: myPlayForm.setOption4(option3);
            j++;
            break;
                }
                }
                }//End of for loop
        difficulty=BR.readLine();
        playFormSetup=true;
        }catch(Exception e){
        e.printStackTrace();
        }
        }
        
        //Answered
        if( myPlayForm.answered == true){
        myPlayForm.answered=false;
        playFormSetup=false;
        if(myPlayForm.flag==loc){
            PS.println(difficulty);
            PS.println("Correct");
            if(difficulty.equals("Easy"))
   score+=10;
     else if(difficulty.equals("Medium"))
   score+=25;
else
        score+=50;
            
            count++;
            myPlayForm.setScore(score);
        }
        else{
            count++;
   PS.println(difficulty);
   PS.println("False");
        }
        }
        }
        if(ae.getSource()==timer &&  setDiff.flag==5 && count >=5){
            System.out.println("I'm trying to EXIT");
       //If all 5 questions have been answered, exit
             JOptionPane.showMessageDialog(myPlayForm, "Thank you for playing: Your score is " +score);
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));

        }
        
                //To change body of generated methods, choose Tools | Templates.
    }
     
    //Random Answer placement for PlayForm, returns int with location of answer placement
    private int randomAnswerPlacement(String answer, PlayForm myForm){
        int location=0;
    Random random = new Random();
    switch(random.nextInt(4)){
           case 0: myPlayForm.setOption1(answer);
           location=1;
            break;
            case 1: myPlayForm.setOption2(answer);
            location=2;
            break;
            case 2: myPlayForm.setOption3(answer);
            location=3;
            break;
            case 3: myPlayForm.setOption4(answer);
            location=4;
            break;
    }
    
    return location;
    }
    
     WindowListener exitListener = new WindowAdapter() {
         
             @Override
     public void windowClosing(WindowEvent e){
     
         if(connectionEstablished == true){
             
         System.out.println("closing");
         PS.println("closing connection");
             try {
                 sock.close();
             } catch (IOException ex) {
                 Logger.getLogger(ComputerNetworks_client.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         
     
     }
     };
}
