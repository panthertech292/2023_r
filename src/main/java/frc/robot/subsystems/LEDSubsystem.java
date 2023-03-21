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
  //Right Side LEDs
  private AddressableLED rightLed;
  private AddressableLEDBuffer rightLedBuffer;

  //Left Side LEDs
  private AddressableLED leftLed;
  private AddressableLEDBuffer leftLedBuffer;

  private int v_rainbowFirstPixelHue;

  public LEDSubsystem() {
    rightLed = new AddressableLED(LEDConstants.kRightLEDPort);
    rightLedBuffer = new AddressableLEDBuffer(150);
    rightLed.setLength(rightLedBuffer.getLength());
    rightLed.setData(rightLedBuffer);
    rightLed.start();

    leftLed = new AddressableLED(LEDConstants.kLeftLEDPort);
    leftLedBuffer = new AddressableLEDBuffer(150);
    leftLed.setLength(leftLedBuffer.getLength());
    leftLed.setData(leftLedBuffer);
    leftLed.start();
  }
  public void setOff(){
    for (var i = 0; i < rightLedBuffer.getLength(); i++) {
      rightLedBuffer.setRGB(i, 0, 0, 0);
      leftLedBuffer.setRGB(i, 0, 0, 0);
    }
    rightLed.setData(rightLedBuffer);
    leftLed.setData(leftLedBuffer);
  }

  public void setSolidColor(int r, int g, int b){
    for (var i = 0; i < rightLedBuffer.getLength(); i++) {
      rightLedBuffer.setRGB(i, r, g, b);
      leftLedBuffer.setRGB(i, r, g, b);
    }
    rightLed.setData(rightLedBuffer);
    leftLed.setData(leftLedBuffer);
  }

  /* 
  public void setColorChase(int r, int g, int b){
    for (var i = 0; i < rightLedBuffer.getLength(); i++) {
      rightLedBuffer.setRGB(i, r, g,b);
    }
    rightLed.setData(rightLedBuffer);
  }*/

  public void rainbow(int pulseSpeed) {
    // For every pixel
    for (var i = 0; i < rightLedBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (v_rainbowFirstPixelHue + (i * 180 / rightLedBuffer.getLength())) % 180;
      // Set the value
      rightLedBuffer.setHSV(i, hue, 255, 128);
      leftLedBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    v_rainbowFirstPixelHue += pulseSpeed;
    // Check bounds
    v_rainbowFirstPixelHue %= 180;
    //v_rainbowFirstPixelHue %= 255;

    rightLed.setData(rightLedBuffer);
    leftLed.setData(leftLedBuffer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
