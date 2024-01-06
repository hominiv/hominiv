package com.hominiv.records;

public record Person(
    Long userId,
    Boolean isHim,
    String firstName,
    String lastName) {}
