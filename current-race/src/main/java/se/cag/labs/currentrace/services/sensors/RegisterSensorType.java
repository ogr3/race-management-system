package se.cag.labs.currentrace.services.sensors;

import java.util.*;

public enum RegisterSensorType {
  START("START"),
  SPLIT("SPLIT"),
  FINISH("FINISH");

  private static final Map<String, RegisterSensorType> lookup = new HashMap<>();

  static {
    for (RegisterSensorType type : RegisterSensorType.values()) {
      lookup.put(type.getId(), type);
    }
  }

  private final String id;

  RegisterSensorType(String id) {
    this.id = id;
  }

  public static RegisterSensorType get(String id) {
    return lookup.get(id);
  }

  public String getId() {
    return id;
  }
}