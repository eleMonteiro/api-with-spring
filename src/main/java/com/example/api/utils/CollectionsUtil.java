package com.example.api.utils;

import java.util.List;
import java.util.Objects;

public class CollectionsUtil {

    public static boolean isNullOrEmpty(List<?> list) {
        return Objects.isNull(list) || list.isEmpty();
    }

}
