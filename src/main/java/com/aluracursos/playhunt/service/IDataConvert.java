package com.aluracursos.playhunt.service;

public interface IDataConvert {
    <T> T convert(String json, Class<T> clazz);
}
