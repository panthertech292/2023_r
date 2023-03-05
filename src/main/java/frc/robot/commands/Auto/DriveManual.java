// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveManual extends CommandBase {
  private final DriveSubsystem DriveSub;
  private double v_leftSpeed;
  private double v_rightSpeed;
  /** Creates a new DriveManual. */
  public DriveManual(DriveSubsystem s_DriveSubsystem, double leftspeed, double rightspeed) {
    DriveSub = s_DriveSubsystem;
    v_leftSpeed = leftspeed;
    v_rightSpeed = rightspeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveSub.tankDrive(v_leftSpeed, v_rightSpeed);
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}