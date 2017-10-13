/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pushbutton;

import java.io.IOException;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;

/**
 *
 * @author yjkim
 */
public class PushButton {
    private static final String LED_PIN = "GPIO18";
    private static final String LED_BTN_PIN = "GPIO23";
    private static final String EXIT_BTN_PIN = "GPIO24";
    
    private GPIOPin ledPin = null;
    private GPIOPin ledBtnPin = null;
    private GPIOPin exitBtnPin = null;

    public PushButton() throws IOException {
        ledPin = DeviceManager.open(LED_PIN, GPIOPin.class);
        ledBtnPin = DeviceManager.open(LED_BTN_PIN, GPIOPin.class);
        exitBtnPin = DeviceManager.open(EXIT_BTN_PIN, GPIOPin.class);
        System.out.println("Devices were successfully opened in constructor...");
    }
    
    public void run() throws IOException {
        boolean exit = false;
        boolean ledState = false;
        
        try {
            while (!exit) {
                ledState = ledBtnPin.getValue();
                ledPin.setValue(ledState);
                System.out.println("LED: " + (ledState ? "ON" : "OFF"));
                
                exit = exitBtnPin.getValue();
                
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e) {}
        
        System.out.println("Exit...");
        close();
    }
    
    public void close() throws IOException {
        ledPin.close();
        ledBtnPin.close();
        exitBtnPin.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PushButton pushButton = new PushButton();
            pushButton.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
