// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToPosition extends CommandBase {
  private final DriveSubsystem DriveSub;
  //private double v_speed;
  private double v_distanceInch;
  private double v_error;
  private double v_p;
  private double v_minSpeed;
  /** Creates a new DriveToPosition. */
  public DriveToPosition(DriveSubsystem s_DriveSubsystem, double minSpeed, double distanceInch, double p) {
    DriveSub = s_DriveSubsystem;
    //v_speed = -speed;
    v_distanceInch = distanceInch;
    v_minSpeed = minSpeed;
    v_p = p;
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
    /* 
    System.out.println("TRYING TO POSITION");
    if (DriveSub.getRightMotorEncoderPosition() < v_distanceInch){
      DriveSub.tankDrive(v_speed, v_speed);
    }else if(DriveSub.getRightMotorEncoderPosition() > v_distanceInch+1){
      DriveSub.tankDrive(-v_speed, -v_speed);
    }
    else{
      DriveSub.tankDrive(0, 0);
    } */
    v_error = -((v_distanceInch - DriveSub.getRightMotorEncoderPosition())*v_p);
    
    if (v_error > 0 && v_error < Math.abs(v_minSpeed)){
      v_error = Math.abs(v_minSpeed);
    }
    if (v_error < 0 && v_error > -Math.abs(v_minSpeed)){
      v_error = -Math.abs(v_minSpeed);
    }
    if ((Math.abs(v_distanceInch - DriveSub.getRightMotorEncoderPosition())) < 1){
      v_error = 0;
    }
    
    System.out.println("INCHES: " + DriveSub.getRightMotorEncoderPosition() + " ERR0R: " + v_error + "MINSPEED: " + v_minSpeed);
    //System.out.println(v_error);
    DriveSub.tankDrive(v_error, v_error);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
