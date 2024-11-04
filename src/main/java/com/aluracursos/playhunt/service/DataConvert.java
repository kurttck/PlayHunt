package com.aluracursos.playhunt.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convert(String json, Class<T> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
