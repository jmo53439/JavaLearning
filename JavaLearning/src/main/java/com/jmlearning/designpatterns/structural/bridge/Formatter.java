package com.jmlearning.designpatterns.structural.bridge;

import java.util.List;

public interface Formatter {

    String format(String header, List<Detail> details);
}
