package com.github.bartekdobija.sqlexec.args;

import org.kohsuke.args4j.Option;

public class Arguments {

  @Option(name = "--url",usage = "jdbc url string", aliases = {"-j"}, help = true, required = true)
  public String url;

  @Option(name = "--driver", usage = "driver name", aliases = {"-d"}, help = true, required = true)
  public String driver;

  @Option(name = "--query", usage = "sql query for execution", aliases = {"-q"}, required = true)
  public String query;

  @Option(name = "--username", usage = "database user name", aliases = {"-u"})
  public String username;

  @Option(name = "--password", usage = "database password", aliases = {"-p"})
  public String password;

  @Option(name = "--passwordFile", usage = "password file", aliases = {"-f"})
  public String passwordFile;

}
