package com.nynu.goule.service;

import com.nynu.goule.common.Result;

import java.util.Map;

public interface CustomerService {

    Result getCtmAll();

    Result delCtm(Map<String, Object> map);
}
