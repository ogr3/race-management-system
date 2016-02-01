package se.cag.labs.currentrace.services.sensors;

import se.cag.labs.currentrace.services.sensors.impl.*;

public enum RegisterSensorFactory {

  INSTANCE;

  public RegisterSensor createRegisterSensorObject(RegisterSensorType type) {
    switch (type) {
      case START:
        return new RegisterSensorStart();
      case SPLIT:
        return new RegisterSensorSplit();
      case FINISH:
        return new RegisterSensorFinish();
      default:
        throw new IllegalArgumentException("Type not supported");
    }
  }
}
