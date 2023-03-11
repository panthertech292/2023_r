// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PickupSubsystem;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.ClawOpen;
import frc.robot.commands.DualArmControl;
import frc.robot.subsystems.ArmSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoBasic extends SequentialCommandGroup {
  /** Creates a new BasicAuto. */
  public AutoBasic(DriveSubsystem s_DriveSubsystem, ArmSubsystem s_ArmSubsystem, PickupSubsystem s_PickupSubsystem) {    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 11, 0).withTimeout(4),
      new ClawOpen(s_PickupSubsystem).withTimeout(0.5),
      new DriveManual(s_DriveSubsystem, -0.20, -0.20).withTimeout(5.5),
      new DualArmControl(s_ArmSubsystem, false, ArmConstants.kStowedSpot, 4, 0).withTimeout(2.5)

    );
  }
}