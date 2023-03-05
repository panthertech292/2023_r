// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PickupConstants;

public class PickupSubsystem extends SubsystemBase {
  private final DoubleSolenoid ClawCylinder;
  /** Creates a new PickupSubsystem. */
  public PickupSubsystem() {
    ClawCylinder = new DoubleSolenoid(PneumaticsModuleType.REVPH, PickupConstants.kClawSolenoidExtendChannel, PickupConstants.kClawSolenoidRetractChannel);
  }
  public void setClawCylinderExtended(){
    ClawCylinder.set(Value.kForward);
  }
  public void setClawCylinderRetracted(){
    ClawCylinder.set(Value.kReverse);
  }
  public void setClawCylinderOff(){
    ClawCylinder.set(Value.kOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
