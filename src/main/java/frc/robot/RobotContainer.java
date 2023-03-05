// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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

  //Drive Commands
  private final Command z_DriveTeleop = new DriveTeleop(s_DriveSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    CameraServer.startAutomaticCapture().setFPS(20);
    configureBindings();
    s_DriveSubsystem.setDefaultCommand(z_DriveTeleop);
  }

  private void configureBindings() {
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

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
