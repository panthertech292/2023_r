// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final double kControllerDeadZone = 0.15;
    public static final double kControllerDeadZoneSmall = 0.05;
  }
  public static class DriveConstants {
    //CAN Devices
    public static int kFrontLeftMotor = 2;
    public static int kFrontRightMotor = 3;
    public static int kBackLeftMotor = 4;
    public static int kBackRightMotor = 5;
    public static int kPigeon2 = 6;
  }
  public static class ArmConstants {
    //CAN Devices
    public static int kUpArmMotor = 10;
    //DIO Inputs
    public static int kUpArmEncoderChannel = 0;
    //PCM Devices
    public static int kLowArmSolenoidRetractChannel = 9;
    public static int kLowArmSolenoidExtendChannel = 8;
    //Arm Positions
    public static double kPickupSpot = 0.456;
    public static double kScoreSpot = 0.278;
    public static double kStowedSpot = 0.62;
    public static double kFloorSpot = 0.48; 
  }
  public static class PickupConstants {
    //PCM Devices
    public static int kClawSolenoidRetractChannel = 11;
    public static int kClawSolenoidExtendChannel = 10;
  }
  public static class LEDConstants {
    //PWM Devices
    public static int kRightLEDPort = 9;
    public static int kLeftLEDPort = 8;
  }
}
