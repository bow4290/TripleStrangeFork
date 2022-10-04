// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.swerve.SwerveDrive;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.JoystickButtons;
import frc.robot.commands.autoSubsystems.DefaultDrive;

//import edu.wpi.first.cameraserver.CameraServer;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static SwerveDrive swerve;
  public static final SendableChooser<String> color = new SendableChooser<String>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer(SendableChooser<Command> choose) {
    swerve = new SwerveDrive();

    SmartDashboard.putData(swerve);
    SmartDashboard.putBoolean("Starts on right?", true);

    // Configure the button bindings
    configButtons();
    configDefaults();

    SmartDashboard.putBoolean("Blind me", true);

    //CameraServer.startAutomaticCapture();

    SmartDashboard.putString("DriverStation", DriverStation.getAlliance().toString());

    SmartDashboard.putString("Event", DriverStation.getEventName());
    SmartDashboard.putNumber("Match #", DriverStation.getMatchNumber());
    SmartDashboard.putString("Match #", DriverStation.getMatchType().toString());
    SmartDashboard.putBoolean("RejectOpps", true);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configButtons() {
  }

  public void configDefaults() {
    swerve.setDefaultCommand(new DefaultDrive(swerve, JoystickButtons.m_driverController, 1));
    swerve.resetEncoders();
  }

  public void setupTele() {
  }

}
