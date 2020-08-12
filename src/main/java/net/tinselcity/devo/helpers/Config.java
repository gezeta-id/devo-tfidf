package net.tinselcity.devo.helpers;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Config {

  @Parameter(names = { "-n", "--show" }, description = "Top n results to be shown")
  public Integer resultsToShow = 5;

  @Parameter(names = { "-i", "--refreshInterval" }, description = "Refresh interval, in seconds")
  public Integer period = 300;

  @Parameter(names = { "-d", "--dir" }, required = true, description = "Directory containing the documents to be processed")
  public String directory;

  @Parameter(names = { "-t", "--terms" }, required = true, description = "Set TT of terms to be analyzed, space separated")
  public String terms;

  @Parameter(description = "Rest of input - will be ignored but avoids throwing errors")
  private List<String> rest = new ArrayList<>();
}

