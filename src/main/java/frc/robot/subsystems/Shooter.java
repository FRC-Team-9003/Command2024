package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private CANSparkMax shootBottom;
  private CANSparkMax shootTopLeft;
  private CANSparkMax shootTopRight;

  private DigitalInput noteSwitch;

  public Shooter() {
    shootBottom = new CANSparkMax(ShooterConstants.NeoBottom, MotorType.kBrushless);
    shootTopLeft = new CANSparkMax(ShooterConstants.NeoTopLeft, MotorType.kBrushless);
    shootTopRight = new CANSparkMax(ShooterConstants.NeoTopRight, MotorType.kBrushless);
    shootTopRight.setInverted(true);

    noteSwitch = new DigitalInput(3);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void setSpeedTop(double speed) {
    shootTopLeft.set(speed);
    shootTopRight.set(speed);
  }

  public void stopTop() {
    shootTopLeft.stopMotor();
    shootTopRight.stopMotor();
  }

  public void setSpeedBottom(double speed) {
    shootBottom.set(speed);
  }

  public void stopBottom() {
    shootBottom.stopMotor();
  }

  public boolean hasNote() {
    return noteSwitch.get();
  }
}
