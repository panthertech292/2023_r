// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
  //Low Arm
  private final DoubleSolenoid LowArmCylinder;

  //Up Arm Motor
  private final CANSparkMax UpArmMotor;

  //Up Arm Encoders
  private final DutyCycleEncoder UpArmEncoder;

  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    //Low Arm
    LowArmCylinder = new DoubleSolenoid(PneumaticsModuleType.REVPH, ArmConstants.kLowArmSolenoidExtendChannel, ArmConstants.kLowArmSolenoidRetractChannel);

    //Up Arm Motor
    UpArmMotor = new CANSparkMax(ArmConstants.kUpArmMotor, MotorType.kBrushless);
    UpArmMotor.restoreFactoryDefaults();
    UpArmMotor.setIdleMode(IdleMode.kBrake);
    UpArmMotor.setSmartCurrentLimit(60);
    UpArmMotor.setInverted(true);
    UpArmMotor.burnFlash();

    //Up Arm Encoder
    UpArmEncoder = new DutyCycleEncoder(ArmConstants.kUpArmEncoderChannel);
  }

  public void setLowArmCylinderExtended(){
    LowArmCylinder.set(Value.kForward);
  }
  public void setLowArmCylinderRetracted(){
    LowArmCylinder.set(Value.kReverse);
  }
  public void setLowArmCylinderOff(){
    LowArmCylinder.set(Value.kOff);
  }
  public void setUpArmMotorSpeed(double speed){
    UpArmMotor.set(speed);
  }
  public double getUpArmEncoder(){
    return UpArmEncoder.getAbsolutePosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
