// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Vision.VisionAngleAlign;
import frc.robot.commands.Vision.VisionScore;
import frc.robot.commands.Vision.VisionScoreCube;
import frc.robot.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private final Command z_HoldPosition = new DriveToPosition(s_DriveSubsystem, 0.10, 0, 0.03);

  //Auto Commands
  private final Command z_BasicAuto = new AutoBasic(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem);
  private final Command z_AutoBalance = new AutoBalance(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem);
  private final Command z_AutoTraverseBalance = new AutoTraverseBalance(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem);
  private final Command z_AutoTwoScore = new AutoTwoScore(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem, s_LEDSubsystem);
  private final Command z_AutoBasicTurn = new AutoBasicTurn(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem);

  //Arm Commands
  private final Command z_DualArmManual = new DualArmManual(s_ArmSubsystem);
  //THE O versions should be the same as driver version. I don't like doing this. It's bad, but I don't see an alternative.
  private final Command z_DualArmPickupSpot = new DualArmControl(s_ArmSubsystem, false,ArmConstants.kPickupSpot,8,0.0);
  private final Command z_DualArmPickupSpotO = new DualArmControl(s_ArmSubsystem, false,ArmConstants.kPickupSpot,8,0.0);
  private final Command z_DualArmScoreSpot = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 8, 0.0);
  private final Command z_DualArmScoreSpotO = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 8, 0.0);
  private final Command z_DualArmFloorSpot = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kFloorSpot, 6, 0.0);
  private final Command z_DualArmFloorSpotO = new DualArmControl(s_ArmSubsystem, true, ArmConstants.kFloorSpot, 6, 0.0);
  private final Command z_DualArmStowedSpot = new DualArmControl(s_ArmSubsystem, false, ArmConstants.kStowedSpot, 4, 0.0);
  private final Command z_ArmKick = new DualArmControl(s_ArmSubsystem, true, .4, 8 ,0.0);

  //Vision Commands
  private final Command z_VisionAngleAlign = new VisionAngleAlign(s_DriveSubsystem, s_LEDSubsystem, 0.10, .010);
  //private final Command z_VisionDistanceAlign = new VisionDistanceAlign(s_DriveSubsystem, 0.10, .010, 0);
  private final Command z_VisionScore = new VisionScore(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem, s_LEDSubsystem);
  private final Command z_VisionScoreCube = new VisionScoreCube(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem, s_LEDSubsystem);

  //Gyro
  //private final Command z_GyroTurn = new GyroTurn(s_DriveSubsystem, 180, 0.005, 0.08);

  SendableChooser<Command> o_AutoChooser = new SendableChooser<>();

  //Pickup Commands
  private final Command z_ClawOpen = new ClawOpen(s_PickupSubsystem);
  private final Command z_ClawClose = new ClawClose(s_PickupSubsystem);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    CameraServer.startAutomaticCapture();
    configureBindings();
    s_DriveSubsystem.setDefaultCommand(z_DriveTeleop);
    s_ArmSubsystem.setDefaultCommand(z_DualArmManual);

    //Auto Command Selector
    o_AutoChooser.setDefaultOption("Basic Auto", z_BasicAuto);
    o_AutoChooser.addOption("Basic Auto with Turn", z_AutoBasicTurn);
    o_AutoChooser.addOption("Auto Balance", z_AutoBalance);
    o_AutoChooser.addOption("Auto Balance Traverse", z_AutoTraverseBalance);
    o_AutoChooser.addOption("Auto Two Score", z_AutoTwoScore);
    o_AutoChooser.addOption("Auto Dead", new DriveManual(s_DriveSubsystem, 0, 0));
    SmartDashboard.putData(o_AutoChooser);
  }

  private void configureBindings() {
    //Bumpers
    final JoystickButton d_rightBumper = new JoystickButton(io_drivercontroller, Button.kRightBumper.value);
    d_rightBumper.onTrue(z_ClawClose);
    final JoystickButton d_leftBumper = new JoystickButton(io_drivercontroller, Button.kLeftBumper.value);
    d_leftBumper.onTrue(z_ClawOpen);
    //Letters
    final JoystickButton d_aButton = new JoystickButton(io_drivercontroller, Button.kA.value);
    d_aButton.whileTrue(z_DualArmPickupSpot.repeatedly());
    final JoystickButton d_bButton = new JoystickButton(io_drivercontroller, Button.kB.value);
    d_bButton.whileTrue(z_DualArmScoreSpot.repeatedly());
    final JoystickButton d_xButton = new JoystickButton(io_drivercontroller, Button.kX.value);
    d_xButton.onTrue(z_DualArmStowedSpot);
    final JoystickButton d_yButton = new JoystickButton(io_drivercontroller, Button.kY.value);
    d_yButton.whileTrue(z_DualArmFloorSpot.repeatedly());
    d_yButton.onFalse(z_ArmKick);
    //Others
    final JoystickButton d_startButton = new JoystickButton(io_drivercontroller, Button.kStart.value);
    d_startButton.whileTrue(z_HoldPosition);
    final JoystickButton d_backButton = new JoystickButton(io_drivercontroller, Button.kBack.value);
    d_backButton.whileTrue(z_VisionScore);
    final JoystickButton d_leftStick = new JoystickButton(io_drivercontroller, Button.kLeftStick.value);
    d_leftStick.whileTrue(z_VisionScoreCube);
    //final JoystickButton d_rightStick = new JoystickButton(io_drivercontroller, Button.kRightStick.value);
    //d_rightStick.whileTrue(z_GyroTurn);
    

    //Operator Buttons
    //Bumpers
    final JoystickButton o_rightBumper = new JoystickButton(io_opercontroller, Button.kRightBumper.value);
    o_rightBumper.onTrue(z_ClawClose);
    final JoystickButton o_leftBumper = new JoystickButton(io_opercontroller, Button.kLeftBumper.value);
    o_leftBumper.onTrue(z_ClawOpen);
    //Letters
    final JoystickButton o_aButton = new JoystickButton(io_opercontroller, Button.kA.value);
    o_aButton.whileTrue(z_DualArmPickupSpotO.repeatedly()); 
    final JoystickButton o_bButton = new JoystickButton(io_opercontroller, Button.kB.value);
    o_bButton.whileTrue(z_DualArmScoreSpotO.repeatedly());
    final JoystickButton o_xButton = new JoystickButton(io_opercontroller, Button.kX.value);
    o_xButton.onTrue(z_DualArmStowedSpot);
    final JoystickButton o_yButton = new JoystickButton(io_opercontroller, Button.kY.value);
    o_yButton.whileTrue(z_DualArmFloorSpotO.repeatedly());
    o_yButton.onFalse(z_ArmKick);
    //Small buttons
    final JoystickButton o_startButton = new JoystickButton(io_opercontroller, Button.kStart.value);
    o_startButton.whileTrue(z_VisionAngleAlign);
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
    if (o_AutoChooser != null){
      return o_AutoChooser.getSelected();
    }
    else{
      return z_BasicAuto;
    }
  }
  
  public void setDisabledLED() {
    if (DriverStation.getAlliance() == Alliance.Red){
      s_LEDSubsystem.setSolidColor(128, 0, 0);
    }else{
      s_LEDSubsystem.setSolidColor(0, 0, 128);
    }
  }
  
}
