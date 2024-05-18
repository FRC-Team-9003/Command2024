package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
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
  private SparkAbsoluteEncoder elevEncoder;
  private SparkLimitSwitch elevFwd;
  private SparkLimitSwitch elevRev;

  // private final SparkPIDController elevController;

  private static final double ENCODER_OFFSET = 0.01;
  private static final double LIMIT_BOTTOM = 0.00;
  // private static final float LIMIT_TOP = ;

  private Double targetPostion = null;

  // Smart Motion Coefficients
  private double maxVel = 1500;
  private double maxAcc = 1000;
  private double minVel = 0;
  private double allowedErr = 0.002;

  private SysIdRoutine idRoutine;

  public Elevator() {
    elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    elevEncoder = elev.getAbsoluteEncoder(Type.kDutyCycle);
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
    targetPostion = null;
    elev.stopMotor();
  }

  public void setSpeed(double speed) {
    targetPostion = null;
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
