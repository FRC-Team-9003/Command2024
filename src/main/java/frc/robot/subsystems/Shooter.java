package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private CANSparkMax shootA;
  private CANSparkMax shootB;
  private DigitalInput noteswitch;


  public Shooter() {
    shootA = new CANSparkMax(ShooterConstants.NeoBottom, MotorType.kBrushless);
    shootB = new CANSparkMax(ShooterConstants.NeoTop, MotorType.kBrushless);
    noteswitch = new DigitalInput(0);
  }
  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stopShooter() {
    shootA.stopMotor();
    shootB.stopMotor();
  }

  public void setSpeedShootA(double speed) {
    shootA.set(speed);
  }

  public void setSpeedShootB(double speed) {
    shootB.set(speed);
  }
}
