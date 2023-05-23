// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.lib.LightSubsystem;
import frc.robot.lib.Pattern;

public class TestSubsystem extends LightSubsystem {

    public TestSubsystem(final int id, final int length) {
        super(id, length);
    }

    public final Command solid = registerPattern(
            (data) -> new Color(data.timePercent(), 0, 0));

    public final Command gradientPosition = registerPattern(
            Pattern.gradientOverPosition(Color.kBlue, Color.kYellow)
                    .modify((data) -> {
                        data.setPosition(data.position() + (int) Math.floor(data.timePercent() * data.maxPosition()));
                    }));

    public final Command gradientTime = registerPattern(
            Pattern.gradientOverTime(Color.kBlue, Color.kYellow));

}
