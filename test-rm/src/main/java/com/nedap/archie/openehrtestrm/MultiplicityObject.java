package com.nedap.archie.openehrtestrm;

import java.lang.Integer;
import java.lang.String;
import java.util.List;
import javax.annotation.Nullable;

public class MultiplicityObject extends TestRMBase {
  @Nullable
  private List<Integer> integerList;

  @Nullable
  private List<String> stringList;

  public List<Integer> getIntegerList() {
    return integerList;}

  public void setIntegerList(List<Integer> value) {
    this.integerList = value;
  }

  public List<String> getStringList() {
    return stringList;}

  public void setStringList(List<String> value) {
    this.stringList = value;
  }
}
