// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveUntilZeroPitch extends CommandBase {
  private final DriveSubsystem DriveSub;
  private double v_speed;
  /** Creates a new DriveUntilPitch. */
  public DriveUntilZeroPitch(DriveSubsystem s_DriveSubsystem, double speed) {
    DriveSub = s_DriveSubsystem;
    v_speed = -speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.zeroRightMotorEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveSub.tankDrive(v_speed, v_speed);
    //System.out.println("TRYING TO PITCH DRIVE");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(DriveSub.getPitch()) < 0.1);
  }
}
