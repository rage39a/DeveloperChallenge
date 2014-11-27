/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package developerchallenge;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.*;

/**
 *
 * @author Rage
 */
public class DeveloperChallenge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      try{
            
        FileInputStream fstream = new FileInputStream("sample.log");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        String strLine;
        Date date2 = new Date();
        int[] arrcount = new int[6];
        int[] arrresponse = new int[6];
        String[] arrmethod = new String[6];
        ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList();
        ArrayList<ArrayList<String>>  twoDArrayListdyno = new ArrayList();
        
        
        for(int i = 0;i < 6;i++){
           arrmethod[i] = "GET";
           twoDArrayList.add(new ArrayList());
           twoDArrayListdyno.add(new ArrayList());
        }
        
        arrmethod[4] = "POST";
               
        String[] arrURL = new String[6];
        arrURL[0] = "\"GET /api/users/{user_id}/count_pending_messages\"";
        arrURL[1] = "\"GET /api/users/{user_id}/get_messages\"";
        arrURL[2] = "\"GET /api/users/{user_id}/get_friends_progress\"";
        arrURL[3] = "\"GET /api/users/{user_id}/get_friends_score\"";
        arrURL[4] = "\"POST /api/users/{user_id}\"";
        arrURL[5] = "\"GET /api/users/{user_id}\"";
        
        String[] arrURLcheck = new String[6];
        arrURLcheck[0] = "count_pending_messages";
        arrURLcheck[1] = "get_messages";
        arrURLcheck[2] = "get_friends_progress";
        arrURLcheck[3] = "get_friends_score";
        arrURLcheck[4] = "/api/users";
        arrURLcheck[5] = "/api/users";
        
        System.out.println("Start : - " + date2.toString());
        
        while ((strLine = br.readLine()) != null) {
         //System.out.println(strLine); 
           
         int methodno = strLine.indexOf("method=");
         String methodstring = strLine.substring(methodno+7);
         //System.out.println(methodstring);  
         int methodend = methodstring.indexOf(" ");
         String method = methodstring.substring(0, methodend);
         //System.out.println(method);
         
         int pathno = strLine.indexOf("path=");
         String pathstring = strLine.substring(pathno+5);
         //System.out.println(pathstring);  
         int urlend = pathstring.indexOf(" ");
         String url = pathstring.substring(0, urlend);
         //System.out.println(url);
         int endfileno = url.lastIndexOf("/");
         String endfile = url.substring(endfileno + 1);
         //System.out.println(endfile);  
         
         int connectno = strLine.indexOf("connect=");
         String connectstring = strLine.substring(connectno+8);
         //System.out.println(connectstring);  
         int connectend = connectstring.indexOf("ms");
         String connect = connectstring.substring(0, connectend);
         //System.out.println(connect);

         int serviceno = strLine.indexOf("service=");
         String servicestring = strLine.substring(serviceno+8);
         //System.out.println(connectstring);  
         int serviceend = servicestring.indexOf("ms");
         String service = servicestring.substring(0, serviceend);
         //System.out.println(service);
         int response = Integer.parseInt(service) + Integer.parseInt(connect);
         
         int dynono = strLine.indexOf("dyno=");
         String dynostring = strLine.substring(dynono+5);
         //System.out.println(connectstring);  
         int dynoend = dynostring.indexOf(" ");
         String dyno = dynostring.substring(0, dynoend);
         //System.out.println(dyno);
         
         // System.out.println(method + " - " + count + " - " + endfile);
         
         for(int g = 0;g < 6; g++){
         
            if (method.equalsIgnoreCase(arrmethod[g]) && endfile.equalsIgnoreCase(arrURLcheck[g])
                 || method.equalsIgnoreCase(arrmethod[g]) && url.substring(0, endfileno).equalsIgnoreCase(arrURLcheck[g])){
                arrcount[g]++;  
                arrresponse[g] += response;
                twoDArrayList.get(g).add(response);
                twoDArrayListdyno.get(g).add(dyno);
            }
         }            
       }
       
       for(int i = 0;i < 6; i++){
           
        System.out.println("URL " + arrURL[i] + " was called " + arrcount[i] + " times,");
        if (arrcount[i] != 0){
            System.out.print("the response time averages are mean: " + arrresponse[i] / arrcount[i]);
            Collections.sort(twoDArrayList.get(i));
            int middle = twoDArrayList.get(i).size()/2;
            if (twoDArrayList.get(i).size()%2 == 1) {
                System.out.print(", median: " + twoDArrayList.get(i).get(middle));
            } 
            else {
                double num1 = twoDArrayList.get(i).get(middle);
                double num2 = twoDArrayList.get(i).get(middle-1);
                System.out.print(", median: " +  ((num2 + num1) / 2.0));
            }
        
            int maxvalue = 0, maxcount = 0;

            for (int f = 0; f < twoDArrayList.get(i).size(); f++) {
                int count = 0;
                for (int j = 0; j < twoDArrayList.get(i).size(); j++) {
                    if (twoDArrayList.get(i).get(j).compareTo(twoDArrayList.get(i).get(f)) == 0){ 
                        count++;
                    }
                    if (count > maxcount) {
                        maxcount = count;
                        maxvalue = twoDArrayList.get(i).get(f);
                    }
                }
            }
       
            System.out.println(", mode: " + maxvalue);
    
            String value = "";
            maxcount = 0;

            for (int f = 0; f < twoDArrayListdyno.get(i).size(); f++) {
                int count = 0;
                for (int j = 0; j < twoDArrayListdyno.get(i).size(); j++) {
                    if (twoDArrayListdyno.get(i).get(j).equalsIgnoreCase(twoDArrayListdyno.get(i).get(f))){ 
                        count++;
                    }
                    if (count > maxcount) {
                        maxcount = count;
                        value = twoDArrayListdyno.get(i).get(f);
                    }
                }
            }
       
            System.out.println("and the most common dyno is " + value);
            
        }
       }
      }
        
      catch(Exception e){
            e.printStackTrace();
      }
    }   
}
