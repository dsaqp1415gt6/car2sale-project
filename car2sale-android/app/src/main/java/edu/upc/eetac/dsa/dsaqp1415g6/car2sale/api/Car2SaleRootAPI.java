package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;

import java.util.HashMap;
import java.util.Map;

public class Car2SaleRootAPI {

    private Map<String, Link> links;

    public Car2SaleRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}
