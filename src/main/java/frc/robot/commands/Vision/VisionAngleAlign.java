// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class VisionAngleAlign extends CommandBase {
  private final DriveSubsystem DriveSub;
  private double v_error;
  private double v_minSpeed;
  private double v_p;
  private int v_timer;
  /** Creates a new VisionAngleAlign. */
  public VisionAngleAlign(DriveSubsystem s_DriveSubsystem, double minSpeed, double p) {
    DriveSub = s_DriveSubsystem;
    v_minSpeed = minSpeed;
    v_p = p;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.tankDrive(0, 0);
    DriveSub.setLimeLightVisionCam();
    v_timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    v_error = (DriveSub.getVisionAngle()*v_p);
    //Has the robot drive at a min speed if the min speed is greater than the error and there is a valid target
    if ((Math.abs(v_minSpeed) > Math.abs(v_error)) && DriveSub.getVisionValidTarget()){
      if (v_error > 0){
        v_error = v_minSpeed;
      }
      else{
        v_error = -v_minSpeed;
      }
    }
    DriveSub.tankDrive(-v_error, v_error);
    v_timer++;
    System.out.println("TIMER: " + v_timer + " error: " + v_error);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
    DriveSub.setLimeLightDriverCam();
    System.out.println("VISION ALIGN: FINISHED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((Math.abs(DriveSub.getVisionAngle()) < 0.5) && (v_timer > 15));
  }
}
