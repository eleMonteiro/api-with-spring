package com.example.GoFTecno.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.example.api.utils.CollectionsUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsUtilTest {

    @Test
    public void deveriaVerificarListaNula() {
        assertTrue(CollectionsUtil.isNullOrEmpty(null), "Lista Nula");
        assertTrue(CollectionsUtil.isNullOrEmpty(new ArrayList<>()), "Lista Vazia");
        assertFalse(CollectionsUtil.isNullOrEmpty(Arrays.asList(1L)), "Lista Long Válida");
        assertFalse(CollectionsUtil.isNullOrEmpty(Arrays.asList("Teste")), "Lista String Válida");
    }

}