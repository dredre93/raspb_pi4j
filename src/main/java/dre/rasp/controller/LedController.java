package dre.rasp.controller;

import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedController {

   private static GpioPinDigitalOutput pin;

    @RequestMapping("/")
    public String greeting() {
        return "Hello World";
    }

    @RequestMapping("/light")
    public String light(){

        if (pin == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLed", PinState.LOW);

        }

        pin.toggle();

        return "ok";
    }
}
