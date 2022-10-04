package frc.robot.commands.autoSubsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.SwerveDrive;

public class DefaultDrive extends CommandBase {
  private final SwerveDrive m_drive;
  private final Joystick m_joystick;
  private double m_xSpeed, m_ySpeed, m_rot;
  private final boolean m_fieldRelative;
  private double heading;
  private PIDController pid;

  /**
   * Creates a new DefaultDrive.
   * 
   * @param subsystem  The drive subsystem this command will run on
   * @param driver     The joystick to be used for calculations in speed and
   *                   rotation
   * @param multiplier The mode of the robot -- multiplied with x, y, and rot
   *                   speed
   */
  public DefaultDrive(SwerveDrive subsystem, Joystick driver, double multiplier) {
    addRequirements(subsystem);
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = subsystem;
    pid = new PIDController(0.05, 0, 0.01);
    m_joystick = driver;
    m_xSpeed = 0;
    m_ySpeed = 0;
    m_rot = 0;
    m_fieldRelative = false;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    heading = m_drive.getAngle().getDegrees();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Timer timer = new Timer();
    //timer.start();

    if (m_drive.getGyroReset()) {
      heading = m_drive.getAngle().getDegrees();
      m_drive.setGyroReset(false);
    }


    //System.err.println("getGyroReset took: " + timer.get() + "s");
    //timer.reset();
    //System.err.println(m_joystick.getRawAxis(1));

    m_xSpeed = 0;
    m_ySpeed = 0;
    m_rot = 0;

    // y should be 0 when robot is facing ^ (and intake is facing driver station)
    // x should be negative when intake facing driver station %
    if (Math.abs(m_joystick.getRawAxis(1)) > 0.15) {
      m_ySpeed = -m_joystick.getRawAxis(1) * Constants.SwerveConstants.kMaxSpeedMetersPerSecond;
    }
    if (Math.abs(m_joystick.getRawAxis(0)) > 0.15) {
      m_xSpeed = m_joystick.getRawAxis(0) * Constants.SwerveConstants.kMaxSpeedMetersPerSecond;
    }
    if (Math.abs(m_joystick.getRawAxis(4)) > 0.2) {
      m_rot = -m_joystick.getRawAxis(4) * 2 * Math.PI;
    }

    double curHead = m_drive.getAngle().getDegrees();
    //System.err.println("angle: " + curHead);

    //System.err.println("input math took: " + timer.get() + "s");
    //timer.reset();
    if (m_rot == 0) {
      m_drive.drive(m_xSpeed, m_ySpeed, pid.calculate(curHead, heading), m_fieldRelative);

    } else {
      m_drive.drive(m_xSpeed, m_ySpeed, m_rot, m_fieldRelative);
      heading = m_drive.getAngle().getDegrees();
    }

    //System.err.println("driving took: " + timer.get() + "s");
    //timer.reset();

    // x for zero heading
    if (m_joystick.getRawButtonPressed(3)) {
      m_drive.zeroHeading();
    }
    //System.err.println("zeroHeading took: " + timer.get() + "s");
    //timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
