package com.nedap.archie.openehrtestrm;

import java.lang.Integer;
import java.lang.String;
import javax.annotation.Nullable;

public class Rim extends TestRMBase {
  @Nullable
  private Integer nuts;

  @Nullable
  private String hubcap;

  public Integer getNuts() {
    return nuts;}

  public void setNuts(Integer value) {
    this.nuts = value;
  }

  public String getHubcap() {
    return hubcap;}

  public void setHubcap(String value) {
    this.hubcap = value;
  }
}
