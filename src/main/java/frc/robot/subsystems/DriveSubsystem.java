// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  //Motors
  private final CANSparkMax FrontLeftMotor;
  private final CANSparkMax FrontRightMotor;
  private final CANSparkMax BackLeftMotor;
  private final CANSparkMax BackRightMotor;

  //Motor Control
  private final MotorControllerGroup LeftSide;
  private final MotorControllerGroup RightSide;
  private final DifferentialDrive DifDrive;

  //IMU
  private WPI_Pigeon2 Pigeon2;

  //Encoders
  private RelativeEncoder FrontLeftMotorEncoder;
  private RelativeEncoder FrontRightMotorEncoder;
  private RelativeEncoder BackLeftMotorEncoder;
  private RelativeEncoder BackRightMotorEncoder;

  //Limelight
  NetworkTableEntry v_limeLightX;
  NetworkTableEntry v_limeLightY;
  NetworkTableEntry v_limeLightValidTarget;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    //Motors
    FrontLeftMotor = new CANSparkMax(DriveConstants.kFrontLeftMotor, MotorType.kBrushless);
    FrontRightMotor = new CANSparkMax(DriveConstants.kFrontRightMotor, MotorType.kBrushless);
    BackLeftMotor = new CANSparkMax(DriveConstants.kBackLeftMotor, MotorType.kBrushless);
    BackRightMotor = new CANSparkMax(DriveConstants.kBackRightMotor, MotorType.kBrushless);
    InitDriveMotors(FrontLeftMotor, true);
    InitDriveMotors(FrontRightMotor, false);
    InitDriveMotors(BackLeftMotor, true);
    InitDriveMotors(BackRightMotor, false);
    
    //Motor Control
    LeftSide = new MotorControllerGroup(FrontLeftMotor, BackLeftMotor);
    RightSide = new MotorControllerGroup(FrontRightMotor, BackRightMotor);
    DifDrive = new DifferentialDrive(LeftSide, RightSide);
    
    //IMU
    Pigeon2 = new WPI_Pigeon2(DriveConstants.kPigeon2);

    //Encoders
    FrontLeftMotorEncoder = FrontLeftMotor.getEncoder();
    FrontRightMotorEncoder = FrontRightMotor.getEncoder();
    BackLeftMotorEncoder = BackLeftMotor.getEncoder();
    BackRightMotorEncoder = BackRightMotor.getEncoder();
    FrontLeftMotorEncoder.setPositionConversionFactor(2.4); //2.3 //Maybe 1.6?
    FrontRightMotorEncoder.setPositionConversionFactor(2.4);
    BackLeftMotorEncoder.setPositionConversionFactor(2.4);
    BackRightMotorEncoder.setPositionConversionFactor(2.4);
    zeroLeftMotorEncoder();
    zeroRightMotorEncoder();

    //Limelight
    NetworkTable limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
    v_limeLightX = limeLightTable.getEntry("tx");
    v_limeLightY = limeLightTable.getEntry("ty");
    v_limeLightValidTarget = limeLightTable.getEntry("tv");
  }

  private void InitDriveMotors(CANSparkMax motor, boolean inverted){
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(60);
    motor.setInverted(inverted);
    motor.burnFlash();
  }
  public double getPitch(){
    return Pigeon2.getRoll();
  }
  public double getLeftMotorEncoderPosition(){
    return FrontLeftMotorEncoder.getPosition();
  }
  public double getRightMotorEncoderPosition(){
    return -(FrontRightMotorEncoder.getPosition());
  }
  public void zeroLeftMotorEncoder(){
    FrontLeftMotorEncoder.setPosition(0);
    BackLeftMotorEncoder.setPosition(0);
  }
  public void zeroRightMotorEncoder(){
    FrontRightMotorEncoder.setPosition(0);
    BackRightMotorEncoder.setPosition(0);
  }
  //Limelight
  public double getVisionAngle(){
    return v_limeLightX.getDouble(0.0);
  }
  public double getVisionYDistance(){
    return v_limeLightY.getDouble(0.0);
  }
  public boolean getVisionValidTarget(){
    return (v_limeLightValidTarget.getDouble(0.0) == 1);
  }
  public void setLimeLightDriverCam(){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }
  public void setLimeLightVisionCam(){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
  }

  public void tankDrive(double leftspeed, double rightspeed){
    DifDrive.tankDrive(leftspeed, rightspeed, false);
  }
  public void arcadeDrive(double rotateSpeed, double forBackSpeed){
    DifDrive.arcadeDrive(forBackSpeed, rotateSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
