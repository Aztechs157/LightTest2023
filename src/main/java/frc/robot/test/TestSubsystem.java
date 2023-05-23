// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.lib.Flexbox;
import frc.robot.lib.LightSubsystem;
import frc.robot.lib.Pattern;

public class TestSubsystem extends LightSubsystem {

    public TestSubsystem(final int id, final int length) {
        super(id, length);
    }

    public final Command solid = registerPattern(Pattern.solid(Color.kHotPink));

    public final Command gradientPosition = registerPattern(
            new Flexbox()
                    .add(1, Pattern.gradientOverPosition(Color.kBlue, Color.kYellow))
                    .add(1, Pattern.gradientOverPosition(Color.kYellow, Color.kBlue))
                    .intoPositionPattern()
                    .shiftOverTime());

    public final Command gradientTime = registerPattern(
            new Flexbox()
                    .add(1, Pattern.gradientOverTime(Color.kBlue, Color.kYellow))
                    .add(1, Pattern.gradientOverTime(Color.kYellow, Color.kBlue))
                    .intoTimePattern());

}
