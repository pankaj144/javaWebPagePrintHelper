/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprotocolapp;

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import org.json.JSONObject;



/**
 *
 * @author hp
 */
public class JavaProtocolApp {

    /**
     * @param args the command line arguments
     */
    private static final String GET_URL = "http://stag-api.futtkr.com//api/user/app_version";
    private static final String POST_URL = "http://stag-api.futtkr.com/api/shop/getDataToPrint";
    private static final String POST_PARAMS = "1";  
    
    public static void main(String[] args) {
        try{
             
            if(args.length > 0){
               String argument = args[0]; 
               String replaceProtoclTag = argument.replaceAll("myappprotocol:", "");
              
               System.out.print("This is my Protocol java Application");
               sendPOST(replaceProtoclTag);
            }else{
            sendPOST("GZB_CR_PMCR_13497");
        }
          
        }catch(IOException exp){
            exp.printStackTrace();
        }
          //printData("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPp");
        
/*            PrintService mPrinter = null;
        Boolean bFoundPrinter = false;

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        // TODO code application logic here
        StringBuilder sb=new StringBuilder();  
        
        for(int i = 0;i < args.length;i++){
            sb.append(args[i]);//now original string is changed  
        }
       
         for (PrintService printService : printServices) {
                String sPrinterName = printService.getName();
                if (sPrinterName.equals("POS-58")) {
                    mPrinter = printService;
                    bFoundPrinter = true;
                }
         }
        if(bFoundPrinter){
            System.out.println("Printer POS-58 found");
        }else{
            System.out.println("Printer POS-58 Not found");
        }
        
         try {
        // Open the image file
            String testData = sb.toString();
            String modify_space = testData.replaceAll("%20", " ");
            String modify_newLine = modify_space.replaceAll("0X1", "\n");
            InputStream is = new ByteArrayInputStream(modify_newLine.getBytes());
            DocFlavor flavor =  DocFlavor.INPUT_STREAM.AUTOSENSE   ;

            // Find the default service
//            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = mPrinter;
            System.out.println(service);

            // Create the print job
            DocPrintJob job = service.createPrintJob();
            Doc doc= new SimpleDoc(is, flavor, null);

            // Monitor print job events; for the implementation of PrintJobWatcher,
            PrintJobWatcher pjDone = new PrintJobWatcher(job);
            // Print it
            job.print(doc, null);

            // Wait for the print job to be done
            pjDone.waitForDone();

            // It is now safe to close the input stream
            is.close(); 
         } catch (PrintException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */           
            
        //StringBuilder sb=new StringBuilder();  
        
//        for(int i = 0;i < args.length;i++){
//            sb.append(args[i]);//now original string is changed  
//        }

        
       /* JFrame f=new JFrame("Protocol Content");  
        JButton b=new JButton(sb.toString());  
        b.setBounds(50,50,200,300);  
        f.add(b);  
        f.setSize(400,400);  
        f.setLayout(null);  
        f.setVisible(true);
        System.out.print("This is my Protocol java Application");
        */
        
        
       
           

    }
    
    static class PrintJobWatcher {
        // true iff it is safe to close the print job's input stream
        boolean done = false;

        PrintJobWatcher(DocPrintJob job) {
            // Add a listener to the print job
            job.addPrintJobListener(new PrintJobAdapter() {
                public void printJobCanceled(PrintJobEvent pje) {
                    allDone();
                }
                public void printJobCompleted(PrintJobEvent pje) {
                    allDone();
                }
                public void printJobFailed(PrintJobEvent pje) {
                    allDone();
                }
                public void printJobNoMoreEvents(PrintJobEvent pje) {
                    allDone();
                }
                void allDone() {
                    synchronized (PrintJobWatcher.this) {
                        done = true;
                        PrintJobWatcher.this.notify();
                         System.out.print(" d!");
                         System.exit(0);
                    }
                }
            });
        }
        public synchronized void waitForDone() {
            try {
                while (!done) {
                    wait();
                }
                
            } catch (InterruptedException e) {
                 System.out.print("Error::" +e.toString());
            }
        }
    }
    
    private static void sendGET() throws IOException {
		URL obj;
                obj = new URL(GET_URL);
		HttpURLConnection con;
                 con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

	}
    
    
    private static void sendPOST(String orderId) throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "");
//                
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
//		os.write(POST_PARAMS.getBytes(StandardCharsets.UTF_8));
                os.write(getPostData(orderId));
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
                        
                        String toPrint = cleanData(response.toString());
                        System.out.println("CLEAN DATA="+toPrint);
                        //using json.org library
//                        try{
//                            toPrint = extractData(response.toString());
//                        showData(toPrint);
//                        }catch(Exception exp){
//                            showData("Error on Erxtraction");
//                        }
                        printData(toPrint);
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
	} 
    
    private static class Info{
        String shopId;
        public void setShopId(String shopId){
            this.shopId = shopId;
        }
    }
    
     static byte[]  getPostData(String myOrderId)  throws UnsupportedEncodingException{
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("orderId", myOrderId);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        return postDataBytes;
    }
     
     
    public static  void printData(String dataToPrint){
        PrintService mPrinter = null;
        Boolean bFoundPrinter = false;

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        // TODO code application logic here
        StringBuilder sb=new StringBuilder();  
        
//        for(int i = 0;i < args.length;i++){
//            sb.append(args[i]);//now original string is changed  
//        }
       
         for (PrintService printService : printServices) {
                String sPrinterName = printService.getName();
                if (sPrinterName.equals("POS-58")) {
                    mPrinter = printService;
                    bFoundPrinter = true;
                }
                
                System.out.println("Printer Name::" +" : " + sPrinterName);
                AttributeSet att = printService.getAttributes();
                for (Attribute a : att.toArray()) {
                    String attributeName;
                    String attributeValue;attributeName = a.getName();
                    attributeValue = att.get(a.getClass()).toString();
                    System.out.println(attributeName +" : " + attributeValue);
                }
         }
          PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
          //System.out.println("Default Printer::" +" : " + service1.getName());
        if(bFoundPrinter){
            System.out.println("Printer POS-58 found");
        }else{
            System.out.println("Printer POS-58 Not found");
        }
        
         try {
        // Open the image file
            String testData = dataToPrint.toString();
            String modify_space = testData.replaceAll("%20", " ");
            String modify_newLine = modify_space.replaceAll("0X1", "\n");
            InputStream is = new ByteArrayInputStream(modify_newLine.getBytes());
            DocFlavor flavor =  DocFlavor.INPUT_STREAM.AUTOSENSE   ;

            // Find the default service
//            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = mPrinter;
            System.out.println(service);

            // Create the print job
            DocPrintJob job = service.createPrintJob();
            Doc doc= new SimpleDoc(is, flavor, null);

            // Monitor print job events; for the implementation of PrintJobWatcher,
            PrintJobWatcher pjDone = new PrintJobWatcher(job);
            // Print it
            job.print(doc, null);

            // Wait for the print job to be done
            pjDone.waitForDone();

            // It is now safe to close the input stream
            is.close(); 
         } catch (PrintException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //convert response in json object
    private static String extractData(String data){
       try{
       JSONObject receviedObject = new JSONObject(data);
       String toPrintData = receviedObject.getString("data");
       return toPrintData.toString();
       }catch(Exception exp){
           return "Error";
       }
       
        
    }
    
    public static  void showData(String data){
         JFrame f=new JFrame("Protocol Content");  
        JButton b=new JButton(data);  
        b.setBounds(0,0,500,500);  
        f.add(b);  
        f.setSize(600,600);  
        f.setLayout(null);  
        f.setVisible(true);
    }
    
    public static String cleanData(String rawData){
        String modified = rawData.replace("{\"data\":\"", "");
        String finalString = modified.replace("\"}", "");
        return finalString;
    }
    
}
