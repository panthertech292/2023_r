// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LEDConstants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  private AddressableLED o_led;
  private AddressableLEDBuffer o_ledBuffer;
  private int v_rainbowFirstPixelHue;
  private int lowerBound = 0;
  private int upperBound = 10;
  private int lowerBound1 = 0+37;
  private int upperBound1 = 10+37;
  private int lowerBound2 = 0+75;
  private int upperBound2 = 10+75;
  private int lowerBound3 = 0+112;
  private int upperBound3 = 10+112;
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

  public void setColorChase(int mainHue ,int chaseHue, int pulseSpeed){
    for (var i = 0; i < o_ledBuffer.getLength(); i++) {
      hue = mainHue;

      if ((i > lowerBound) && (i < upperBound)){
        hue = chaseHue; //was  = 147 before
      }
      if ((i > lowerBound1) && (i < upperBound1)){
        hue = chaseHue; //was  = 147 before
      }
      if ((i > lowerBound2) && (i < upperBound2)){
        hue = chaseHue; //was  = 147 before
      }
      if ((i > lowerBound3) && (i < upperBound3)){
        hue = chaseHue; //was  = 147 before
      }
      //You can't change the S & V on this without causing issues it seems. so just only change the hue.
      //Not sure if it's a skill issue or a WPI issue.
      o_ledBuffer.setHSV(i, hue, 255,128); 
    }
    
    if (upperBound + 1 + pulseSpeed > 150){
      lowerBound = 0;
      upperBound = 10;
    }else{
      upperBound = upperBound + 1 + pulseSpeed;
      lowerBound = lowerBound + 1 + pulseSpeed;
    }

    if (upperBound1 + 1 + pulseSpeed > 150){
      lowerBound1 = 0;
      upperBound1 = 10;
    }else{
      upperBound1 = upperBound1 + 1 + pulseSpeed;
      lowerBound1 = lowerBound1 + 1 + pulseSpeed;
    }

    if (upperBound2 + 1 + pulseSpeed > 150){
      lowerBound2 = 0;
      upperBound2 = 10;
    }else{
      upperBound2 = upperBound2 + 1 + pulseSpeed;
      lowerBound2 = lowerBound2 + 1 + pulseSpeed;
    }
    if (upperBound3 + 1 + pulseSpeed > 150){
      lowerBound3 = 0;
      upperBound3 = 10;
    }else{
      upperBound3 = upperBound3 + 1 + pulseSpeed;
      lowerBound3 = lowerBound3 + 1 + pulseSpeed;
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
