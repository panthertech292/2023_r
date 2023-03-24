// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class DriveTeleop extends CommandBase {
  private final DriveSubsystem DriveSub;
  private final LEDSubsystem LEDSub;
  /** Creates a new DriveTeleop. */
  public DriveTeleop(DriveSubsystem s_DriveSubsystem, LEDSubsystem s_LEDSubsystem) {
    DriveSub = s_DriveSubsystem;
    LEDSub = s_LEDSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.setLimeLightDriverCam();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveSub.arcadeDrive(RobotContainer.getDriverLeftSpeedX()*0.80, (RobotContainer.getDriverRightSpeedY()));

    //LEDSub.rainbow(1 + (int) Math.round(Math.abs(RobotContainer.getDriverRightSpeedY()*10)));
    LEDSub.setColorChase(255, 0, 0, 0);
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
