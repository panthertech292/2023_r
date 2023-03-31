// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GyroTurn extends CommandBase {
  private final DriveSubsystem DriveSub;
  private double v_error;
  private double v_p;
  private double v_minSpeed;
  private double v_headingTarget;
  /** Creates a new GyroTurn. */
  public GyroTurn(DriveSubsystem s_DriveSubsystem, double heading, double p, double minSpeed) {
    DriveSub = s_DriveSubsystem;
    v_headingTarget = heading;
    v_p = p;
    v_minSpeed = minSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.resetPigeon();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    v_error = ((v_headingTarget - DriveSub.getHeading())*v_p);
    System.out.println("Error: " + v_error + " HEADING: " + DriveSub.getHeading());

    if (Math.abs(v_error) < Math.abs(v_minSpeed)){
      if ((v_headingTarget - DriveSub.getHeading()) > 0){
        v_error = v_minSpeed;
      }else{
        v_error = -v_minSpeed;
      }
    }

    DriveSub.tankDrive(-v_error, v_error);

    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return false;
    return (Math.abs(v_headingTarget - DriveSub.getHeading()) < 0.1);
  }
}
