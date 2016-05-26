package com.github.bartekdobija.sqlexec.core;

import com.github.bartekdobija.sqlexec.args.Arguments;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {

  public static void main(String[] args) throws CmdLineException {
    Arguments arguments = new Arguments();
    CmdLineParser parser = new CmdLineParser(arguments);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.out.println("Options:");
      parser.printUsage(System.out);
      System.exit(1);
    }

    new Application(arguments).run();
  }
}
