package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private CANSparkMax elev;
  private SparkLimitSwitch elevFwd;
  private SparkLimitSwitch elevRev;

  private SysIdRoutine idRoutine;

  public Elevator() {
    elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    elevFwd = elev.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    elevRev = elev.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);

    elev.setIdleMode(IdleMode.kBrake);
    elev.burnFlash();

    // final Measure<Velocity<Voltage>> rate = Units.Volts.per(Units.Seconds).of(0.5);
    // final Measure<Voltage> step = Units.Volts.of(4.0);

    // SysId Routine
    idRoutine =
        new SysIdRoutine(
            new SysIdRoutine.Config(),
            new SysIdRoutine.Mechanism(
                (Measure<Voltage> volts) -> elev.setVoltage(volts.in(Units.Volts)), null, this));
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stop() {
    elev.stopMotor();
  }

  public void setSpeed(double speed) {
    elev.set(speed);
  }

  public boolean getElevFwd() {
    return elevFwd.isPressed();
  }

  public boolean getElevRev() {
    return elevRev.isPressed();
  }

  public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
    return idRoutine.quasistatic(direction);
  }

  public Command sysIdDynamic(SysIdRoutine.Direction direction) {
    return idRoutine.dynamic(direction);
  }
}
