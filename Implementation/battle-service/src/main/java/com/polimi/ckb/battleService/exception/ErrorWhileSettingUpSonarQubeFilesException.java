package com.polimi.ckb.battleService.exception;

public class ErrorWhileSettingUpSonarQubeFilesException extends RuntimeException {

  public ErrorWhileSettingUpSonarQubeFilesException() {
    super("Error while setting up SonarQube files.");
  }
}
