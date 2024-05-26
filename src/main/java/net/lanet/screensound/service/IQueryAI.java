package net.lanet.screensound.service;

import net.lanet.screensound.configs.ApplicationProperties;

public interface IQueryAI {

    String getInformation(String text,
                          ApplicationProperties applicationProperties);

}
