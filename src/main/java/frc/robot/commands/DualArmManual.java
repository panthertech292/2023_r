// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;

public class DualArmManual extends CommandBase {
  private final ArmSubsystem ArmSub;
  /** Creates a new DualArmManual. */
  public DualArmManual(ArmSubsystem s_ArmSubsystem) {
    ArmSub = s_ArmSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("ARM POSITION: " + ArmSub.getUpArmEncoder()+ " LOW: " + ArmSub.getArmCylinder());
    //Extend or Retract the low arm based on joystick position
    if (RobotContainer.getOperLeftSpeedY() > .15){
      ArmSub.setLowArmCylinderExtended();
    }
    if (RobotContainer.getOperLeftSpeedY() < -0.15){
      ArmSub.setLowArmCylinderRetracted();
    } //.36
    //Arm check
    if (ArmSub.getUpArmEncoder() < 0.36 && (ArmSub.getArmCylinder() == Value.kReverse)){
      //The arm is up to high
      ArmSub.setUpArmMotorSpeed(0.10);
    }else{
      ArmSub.setUpArmMotorSpeed(RobotContainer.getOperRightSpeedY());
    }
    
  } 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ArmSub.setUpArmMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
