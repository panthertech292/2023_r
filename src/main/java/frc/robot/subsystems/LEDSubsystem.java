// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LEDConstants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  private AddressableLED o_led;
  private AddressableLEDBuffer o_ledBuffer;
  private int v_rainbowFirstPixelHue;
  private int lowerBound = 0;
  private int upperBound = 10;
  private int hue;


  public LEDSubsystem() {
    o_led = new AddressableLED(LEDConstants.kLEDPort);
    o_ledBuffer = new AddressableLEDBuffer(150);
    o_led.setLength(o_ledBuffer.getLength());
    o_led.setData(o_ledBuffer);
    o_led.start();
  }
  public void setOff(){
    for (var i = 0; i < o_ledBuffer.getLength(); i++) {
      o_ledBuffer.setRGB(i, 0, 0, 0);
    }
    o_led.setData(o_ledBuffer);
  }

  public void setSolidColor(int r, int g, int b){
    for (var i = 0; i < o_ledBuffer.getLength(); i++) {
      o_ledBuffer.setRGB(i, r, g, b);
    }
    o_led.setData(o_ledBuffer);
  }
  public void setColorChase(int r, int g, int b, int pulseSpeed){

    for (var i = 0; i < o_ledBuffer.getLength(); i++) {
      //System.out.println("I: "+ i + " LOW: "+lowerBound + " HI: " +upperBound);
      if (DriverStation.getAlliance() == Alliance.Red){
        hue = 0;
      }else{
        hue = 120;
      }

      if ((i > lowerBound) && (i < upperBound)){
        hue = 147;
      }
      //System.out.println((i > lowerBound) && (i < upperBound));
      o_ledBuffer.setHSV(i, hue, 255,128);
    }
    
    //System.out.println("LOW: " + lowerBound + "UP: " + upperBound);
    
    if (upperBound + 1 > 150){
      lowerBound = 0;
      upperBound = 10;
    }else{
      upperBound = upperBound + 1;
      lowerBound = lowerBound + 1;
    }
    o_led.setData(o_ledBuffer);
  }


  public void rainbow(int pulseSpeed) {
    // For every pixel
    for (var i = 0; i < o_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (v_rainbowFirstPixelHue + (i * 180 / o_ledBuffer.getLength())) % 180;
      // Set the value
      o_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    v_rainbowFirstPixelHue += pulseSpeed;
    // Check bounds
    v_rainbowFirstPixelHue %= 180;
    //v_rainbowFirstPixelHue %= 255;

    o_led.setData(o_ledBuffer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
