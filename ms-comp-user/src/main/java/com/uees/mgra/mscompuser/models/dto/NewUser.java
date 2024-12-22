package com.uees.mgra.mscompuser.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public record NewUser(String name, String username, String email, String password, Set<String> roles) implements Serializable { }