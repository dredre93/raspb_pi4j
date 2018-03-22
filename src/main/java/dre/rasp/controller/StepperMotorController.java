package dre.rasp.controller;

import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.lang.InterruptedException;

@RestController
public class StepperMotorController {

   private static GpioPinDigitalOutput pina;
   private static GpioPinDigitalOutput pinb;
   private static GpioPinDigitalOutput pinc;
   private static GpioPinDigitalOutput pind;

   private HashMap driveLogic=new HashMap();

    @RequestMapping("/stepping")
    public String greeting() {
        return "Hello World lets Step";
    }

    @RequestMapping("/stepping/clockwise")
    public void clockwise(){
	System.out.println("your motor is stepping clockwise!");

	initDriveLogic();

        if (pina == null && pinb == null && pinc == null &&pind == null) 
	{
            GpioController gpio = GpioFactory.getInstance();
            pina = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "PinA", PinState.LOW);
	    pinb = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "PinB", PinState.LOW);
            pinc = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "PinC", PinState.LOW);
            pind = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "PinD", PinState.LOW);

        }
	
        driveMotor();

    }
	
    private boolean initDriveLogic(){

  	driveLogic.put(0, "1001");
  	driveLogic.put(1, "0001");
  	driveLogic.put(2, "0011");
  	driveLogic.put(3, "0010");
  	driveLogic.put(4, "0110");
  	driveLogic.put(5, "0100");
  	driveLogic.put(6, "1100");
  	driveLogic.put(7, "1000");

	return true;
    }

    private void driveMotor()
    {
      while (true) 
      {
        for (int i = 1; i < driveLogic.size(); i++) 
        {
 	  String grayCode = (String) driveLogic.get(i);
          setPin(pina,grayCode.charAt(0));
    	  setPin(pinb,grayCode.charAt(1));
    	  setPin(pinc,grayCode.charAt(2));
    	  setPin(pind,grayCode.charAt(3));

	  try 
          {
    	    Thread.sleep(10);
	  } catch (InterruptedException iEx) 
	  {
	    System.out.println(iEx);
	  }
   	}
      }	
    }

    /**
    * 
    * Sets the passed pin Low or High depending on the passed value.
    * 
    * @param pin
    * @param value
    */
    private void setPin(GpioPinDigitalOutput pin,char value){
      if (value == '0') 
      {
        pin.low();
      } else {
        pin.high();
      }
    }
}
