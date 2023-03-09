// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

  PowerDistribution revPDH;
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

    revPDH = new PowerDistribution(1,ModuleType.kRev);

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
    FrontLeftMotorEncoder.setPositionConversionFactor(2.3);
    FrontRightMotorEncoder.setPositionConversionFactor(2.3);
    BackLeftMotorEncoder.setPositionConversionFactor(2.3);
    BackRightMotorEncoder.setPositionConversionFactor(2.3);
    zeroLeftMotorEncoder();
    zeroRightMotorEncoder();
  }

  private void InitDriveMotors(CANSparkMax motor, boolean inverted){
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(40);
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

  public void tankDrive(double leftspeed, double rightspeed){
    DifDrive.tankDrive(leftspeed, rightspeed, false);
  }
  public void arcadeDrive(double rotateSpeed, double forBackSpeed){
    DifDrive.arcadeDrive(forBackSpeed, rotateSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println("FL TEMP: " + FrontLeftMotor.getMotorTemperature());
    //System.out.println("FR TEMP: " + FrontRightMotor.getMotorTemperature());
    //System.out.println("BL VOLT: " + BackLeftMotor.getBusVoltage());
    //System.out.println("BR VOLT: " + BackRightMotor.getBusVoltage());
    SmartDashboard.putNumber("Voltage", revPDH.getVoltage());
    SmartDashboard.putNumber("Total Current", revPDH.getTotalCurrent());
    SmartDashboard.putNumber("Total Energy", revPDH.getTotalEnergy());
    //SmartDashboard.put("Faults", revPDH.getFaults());
  }
}
