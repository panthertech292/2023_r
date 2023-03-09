// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.commands.Auto.*;
import frc.robot.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  //Controllers
  private final static XboxController io_drivercontroller = new XboxController(OperatorConstants.kDriverControllerPort);
  private final static XboxController io_opercontroller = new XboxController(OperatorConstants.kOperatorControllerPort);

  //Subsystems
  private final DriveSubsystem s_DriveSubsystem = new DriveSubsystem();
  private final ArmSubsystem s_ArmSubsystem = new ArmSubsystem();
  private final PickupSubsystem s_PickupSubsystem = new PickupSubsystem();
  private final LEDSubsystem s_LEDSubsystem = new LEDSubsystem();

  //Drive Commands
  private final Command z_DriveTeleop = new DriveTeleop(s_DriveSubsystem, s_LEDSubsystem);

  //Auto Commands
  private final Command z_BasicAuto = new AutoBasic(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem);
  //private final Command z_DriveBalance = new DriveBalance(s_DriveSubsystem);
  //private final Command z_DriveUntilPitch = new DriveUntilPitch(s_DriveSubsystem, 0.15 , 5);
  private final Command z_AutoBalance = new AutoBalance(s_DriveSubsystem);
  
  //Arm Commands
  private final Command z_DualArmManual = new DualArmManual(s_ArmSubsystem);
  private final Command z_DualArmPickupSpot = new DualArmControl(s_ArmSubsystem, false,ArmConstants.kPickupSpot,8,0.0);
  private final Command z_DualArmScoreSpot = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 8, 0.0);
  private final Command z_DualArmStowedSpot = new DualArmControl(s_ArmSubsystem, false, ArmConstants.kStowedSpot, 6, 0.0);
  private final Command z_DualArmFloorSpot = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kFloorSpot, 6, 0.0);

  //Pickup Commands
  private final Command z_ClawOpen = new ClawOpen(s_PickupSubsystem);
  private final Command z_ClawClose = new ClawClose(s_PickupSubsystem);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    //CameraServer.startAutomaticCapture().setFPS(20);
    configureBindings();
    s_DriveSubsystem.setDefaultCommand(z_DriveTeleop);
    s_ArmSubsystem.setDefaultCommand(z_DualArmManual);
  }

  private void configureBindings() {
    final JoystickButton d_aButton = new JoystickButton(io_drivercontroller, Button.kA.value);
    d_aButton.whileTrue(z_DualArmPickupSpot);
    final JoystickButton d_bButton = new JoystickButton(io_drivercontroller, Button.kB.value);
    d_bButton.whileTrue(z_DualArmScoreSpot);
    final JoystickButton d_xButton = new JoystickButton(io_drivercontroller, Button.kX.value);
    d_xButton.whileTrue(z_DualArmStowedSpot);
    final JoystickButton d_yButton = new JoystickButton(io_drivercontroller, Button.kY.value);
    d_yButton.whileTrue(z_DualArmFloorSpot);
    final JoystickButton d_startButton = new JoystickButton(io_drivercontroller, Button.kStart.value);
    d_startButton.whileTrue(z_AutoBalance);
    //final JoystickButton d_backButton = new JoystickButton(io_drivercontroller, Button.kBack.value);
    final JoystickButton d_rightBumper = new JoystickButton(io_drivercontroller, Button.kRightBumper.value);
    d_rightBumper.onTrue(z_ClawClose);
    final JoystickButton d_leftBumper = new JoystickButton(io_drivercontroller, Button.kLeftBumper.value);
    d_leftBumper.onTrue(z_ClawOpen);

    //Operator Buttons
    final JoystickButton o_rightBumper = new JoystickButton(io_opercontroller, Button.kRightBumper.value);
    o_rightBumper.onTrue(z_ClawClose);
    final JoystickButton o_leftBumper = new JoystickButton(io_opercontroller, Button.kLeftBumper.value);
    o_leftBumper.onTrue(z_ClawOpen);
    final JoystickButton o_aButton = new JoystickButton(io_opercontroller, Button.kA.value);
    o_aButton.whileTrue(z_DualArmPickupSpot); 
    final JoystickButton o_bButton = new JoystickButton(io_opercontroller, Button.kB.value);
    o_bButton.whileTrue(z_DualArmScoreSpot);
    final JoystickButton o_xButton = new JoystickButton(io_opercontroller, Button.kX.value);
    o_xButton.whileTrue(z_DualArmStowedSpot);
    final JoystickButton o_yButton = new JoystickButton(io_opercontroller, Button.kY.value);
    o_yButton.whileTrue(z_DualArmFloorSpot);
  }
  public static double deadZoneCheck(double rawInput, double deadBand){
    if (Math.abs(rawInput) > deadBand){
      return rawInput;
    }
    else{
      return 0.00;
    }
  }
  public static double getDriverLeftSpeedY(){
    return deadZoneCheck(io_drivercontroller.getLeftY(), OperatorConstants.kControllerDeadZoneSmall);
  }
  public static double getDriverRightSpeedY(){
    return deadZoneCheck(io_drivercontroller.getRightY(), OperatorConstants.kControllerDeadZone);
  }
  public static double getDriverLeftSpeedX(){
    return deadZoneCheck(io_drivercontroller.getLeftX(), OperatorConstants.kControllerDeadZoneSmall);
  }
  public static double getDriverRightSpeedX(){
    return deadZoneCheck(io_drivercontroller.getRightX(), OperatorConstants.kControllerDeadZone);
  }
  public static double getOperRightSpeedY(){
    return deadZoneCheck(io_opercontroller.getRightY(), OperatorConstants.kControllerDeadZone);
  }
  public static double getOperLeftSpeedY(){
    return deadZoneCheck(io_opercontroller.getLeftY(), OperatorConstants.kControllerDeadZone);
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return z_BasicAuto;
  }
}
