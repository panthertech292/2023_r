// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveBalance extends CommandBase {
  private final DriveSubsystem DriveSub;
  private boolean onPlatform;
  /** Creates a new DriveBalance. */
  public DriveBalance(DriveSubsystem s_DriveSubsystem) {
    DriveSub = s_DriveSubsystem;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.zeroRightMotorEncoder();
    onPlatform = false;
  }

  //THIS KINDA WORKS BE NEEDS TO BE REMADE. ONLY USE THIS UNLESS WE REALLY HAVE TO

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(DriveSub.getPitch()) < 5){
      if (!onPlatform){
        DriveSub.tankDrive(.15, .15);
        DriveSub.zeroRightMotorEncoder();
      }
    }
    
    if (DriveSub.getPitch() < -5){
      onPlatform = true;
      System.out.println(DriveSub.getRightMotorEncoderPosition());
      if (DriveSub.getRightMotorEncoderPosition() > -35){
        DriveSub.tankDrive(0.15, 0.15);
      }else{
        DriveSub.tankDrive(0, 0);
      }
    }
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
