// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class VisionDistanceAlign extends CommandBase {
  private final DriveSubsystem DriveSub;
  private double v_error;
  private double v_minSpeed;
  private double v_p;
  private double v_distanceTarget;
  /** Creates a new VisionDistanceAlign. */
  public VisionDistanceAlign(DriveSubsystem s_DriveSubsystem, double minSpeed, double p, double distanceTarget) {
    DriveSub = s_DriveSubsystem;
    v_minSpeed = minSpeed;
    v_p = p;
    v_distanceTarget = distanceTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.tankDrive(0, 0);
    DriveSub.setLimeLightVisionCam();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Check to make sure we actually have a target before we start driving.
    if (DriveSub.getVisionValidTarget()){
      v_error = (v_distanceTarget - DriveSub.getVisionYDistance())*v_p;
    }else{
      v_error = 0;
    }

    if ((Math.abs(v_minSpeed) > Math.abs(v_error)) && (DriveSub.getVisionValidTarget())){
      if (v_error > 0){
        v_error = v_minSpeed;
      }
      else{
        v_error = -v_minSpeed;
      }
    }
    DriveSub.tankDrive(-v_error, -v_error);
    System.out.println("DRIVE SPD: " + -v_error +" ERR: " + (v_distanceTarget - DriveSub.getVisionYDistance()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
    //DriveSub.setLimeLightDriverCam();
    System.out.println("VISION DISTANCE ALIGN: FINISHED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((Math.abs(v_distanceTarget - DriveSub.getVisionYDistance()) < 1) && (DriveSub.getVisionValidTarget()));
  }
}
