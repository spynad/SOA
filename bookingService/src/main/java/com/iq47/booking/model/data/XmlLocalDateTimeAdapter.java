package com.iq47.booking.model.data;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDateTime;

public class XmlLocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>{

    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
