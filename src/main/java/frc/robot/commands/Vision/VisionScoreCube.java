// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.ClawOpen;
import frc.robot.commands.DualArmControl;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.PickupSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class VisionScoreCube extends SequentialCommandGroup {
  /** Creates a new VisionScoreCube. */
  public VisionScoreCube(DriveSubsystem s_DriveSubsystem, ArmSubsystem s_ArmSubsystem, PickupSubsystem s_PickupSubsystem, LEDSubsystem s_LEDSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      //wip
      new VisionSetPipeline(s_DriveSubsystem, 2),
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 8, 0),
      new VisionAngleAlign(s_DriveSubsystem, s_LEDSubsystem, 0.10, 0.030),
      new VisionDistanceAlign(s_DriveSubsystem, 0.15, 0.03, -8),

      //We are now at the pole
      new VisionSetPipeline(s_DriveSubsystem, 2),
      new VisionAngleAlign(s_DriveSubsystem, s_LEDSubsystem, 0.09, 0.009),
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 8, 0),
      new ClawOpen(s_PickupSubsystem)
    );
  }
}
