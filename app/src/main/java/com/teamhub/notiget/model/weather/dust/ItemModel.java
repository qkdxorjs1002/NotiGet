package com.teamhub.notiget.model.weather.dust;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "item_all", strict = false)
public class ItemModel {

    @Element(name = "stationId")
    public StationId stationId;

    @Element(name = "stationName")
    public StationName stationName;

    @Element(name = "localName")
    public LocalName localName;

    @Element(name = "dataTime")
    public DataTime dataTime;

    @Element(name = "pm10Value")
    public Pm10Value pm10Value;

    @Element(name = "pm25Value")
    public Pm25Value pm25Value;

    @Element(name = "pm10Grade")
    public Pm10Grade pm10Grade;

    @Element(name = "pm25Grade")
    public Pm25Grade pm25Grade;

    @Root(name = "stationId", strict = false)
    public static class StationId {
        @Text
        public String value;
    }

    @Root(name = "stationName", strict = false)
    public static class StationName {
        @Text
        public String value;
    }

    @Root(name = "localName", strict = false)
    public static class LocalName {
        @Text
        public String value;
    }

    @Root(name = "dataTime", strict = false)
    public static class DataTime {
        @Text
        public String value;
    }

    @Root(name = "pm10Value", strict = false)
    public static class Pm10Value {
        @Text
        public String value;
    }

    @Root(name = "pm25Value", strict = false)
    public static class Pm25Value {
        @Text
        public String value;
    }

    @Root(name = "pm10Grade", strict = false)
    public static class Pm10Grade {
        @Text
        public String value;
    }

    @Root(name = "pm25Grade", strict = false)
    public static class Pm25Grade {
        @Text
        public String value;
    }

}
