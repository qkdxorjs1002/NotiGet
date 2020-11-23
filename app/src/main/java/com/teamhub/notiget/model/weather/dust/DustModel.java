package com.teamhub.notiget.model.weather.dust;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "air", strict = false)
public class DustModel {

    // TODO: 더나은 XML Parser 필요 (SimpleXmlConverter is deprecated / no JAXB supported)
    @Element(name = "item_all")
    public ItemModel item_all;

}
