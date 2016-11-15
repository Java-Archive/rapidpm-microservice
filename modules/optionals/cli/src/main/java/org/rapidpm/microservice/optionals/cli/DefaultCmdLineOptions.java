/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.optionals.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.MainUndertow;

import java.util.ArrayList;
import java.util.List;

public class DefaultCmdLineOptions implements CmdLineStartupAction {

    public static final String CMD_REST_PORT = "restPort";
    public static final String CMD_REST_HOST = "restHost";
    public static final String CMD_SERVLET_PORT = "servletPort";
    public static final String CMD_SERVLET_HOST = "servletHost";


    @Override
    public List<Option> getOptions() {
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(null, CMD_REST_PORT, true, "Port for REST"));
        options.add(new Option(null, CMD_REST_HOST, true, "Host IP for REST"));
        options.add(new Option(null, CMD_SERVLET_PORT, true, "Port for optionslets"));
        options.add(new Option(null, CMD_SERVLET_HOST, true, "Host IP for optionslets"));
        return options;
    }

    @Override
    public void execute(CommandLine cli) {

        if (cli.hasOption(CMD_SERVLET_PORT)) {
            final String optionValue = cli.getOptionValue(CMD_SERVLET_PORT).trim();
            System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, optionValue);
        }
        if (cli.hasOption(CMD_SERVLET_HOST)) {
            final String optionValue = cli.getOptionValue(CMD_SERVLET_HOST).trim();
            System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY, optionValue);
        }
        if (cli.hasOption(CMD_REST_PORT)) {
            final String optionValue = cli.getOptionValue(CMD_REST_PORT).trim();
            System.setProperty(MainUndertow.REST_PORT_PROPERTY, optionValue);
        }
        if (cli.hasOption(CMD_REST_HOST)) {
            final String optionValue = cli.getOptionValue(CMD_REST_HOST).trim();
            System.setProperty(MainUndertow.REST_HOST_PROPERTY, optionValue);
        }
    }
}
