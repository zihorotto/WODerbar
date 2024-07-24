package com.woderbar.core.util;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Objects;
import java.util.Properties;


public class YamlPropertiesLoader implements PropertiesPersister {

    @Override
    public void load(Properties props, InputStream is) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new InputStreamResource(is));
        Objects.requireNonNull(yaml.getObject()).forEach((key, value) ->
                props.setProperty(String.valueOf(key), StringUtils.hasText(String.valueOf(value)) ? String.valueOf(value) : ""));
    }

    @Override
    public void load(Properties props, Reader reader) throws IOException {
        // Uses Commons IO ReaderInputStream
        InputStream inputStream = ReaderInputStream.builder().setReader(reader).get();
        load(props, inputStream);
    }

    @Override
    public void store(Properties props, OutputStream os, String header) throws IOException {
        throw new UnsupportedEncodingException("Storing is not supported by YamlPropertiesLoader");
    }

    @Override
    public void store(Properties props, Writer writer, String header) throws IOException {
        throw new UnsupportedEncodingException("Storing is not supported by YamlPropertiesLoader");
    }

    @Override
    public void loadFromXml(Properties props, InputStream is) throws IOException {
        throw new UnsupportedEncodingException("Loading from XML is not supported by YamlPropertiesLoader");
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header) throws IOException {
        throw new UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader");
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header, String encoding) throws IOException {
        throw new UnsupportedEncodingException("Storing to XML is not supported by YamlPropertiesLoader");
    }
}
