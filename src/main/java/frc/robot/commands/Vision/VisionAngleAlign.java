// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class VisionAngleAlign extends CommandBase {
  private final DriveSubsystem DriveSub;
  private final LEDSubsystem LEDSub;
  private double v_error;
  private double v_minSpeed;
  private double v_p;
  //private double v_onTargetCount;
  /** Creates a new VisionAngleAlign. */
  public VisionAngleAlign(DriveSubsystem s_DriveSubsystem, LEDSubsystem s_LEDSubsystem, double minSpeed, double p) {
    DriveSub = s_DriveSubsystem;
    LEDSub = s_LEDSubsystem;
    v_minSpeed = minSpeed;
    v_p = p;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem, s_LEDSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.tankDrive(0, 0);
    DriveSub.setLimeLightVisionCam();
    LEDSub.setOff();
    //v_onTargetCount = 0;
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
    //if (Math.abs(DriveSub.getVisionAngle()) < 1){
    //  v_onTargetCount++;
    //}
    System.out.println("VISION ANGLE ALIGN: ERR: " + v_error + " ANGLE: " + DriveSub.getVisionAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
    System.out.println("VISION ANGLE ALIGN: FINISHED");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return ((Math.abs(DriveSub.getVisionAngle()) < 0.5) && (v_timer > 15));
    return ((Math.abs(DriveSub.getVisionAngle()) < 0.4) && (DriveSub.getVisionValidTarget())); //&& (v_onTargetCount > 2));
  }
}
