package com.project.port.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class TestUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @SneakyThrows
  protected String objectToJsonString(final Object object) {
    return OBJECT_MAPPER.writeValueAsString(object);
  }

  @SneakyThrows
  protected <T> T jsonStringToObject(final String json, final Class<T> clazz) {
    return OBJECT_MAPPER.readValue(json, clazz);
  }
}
