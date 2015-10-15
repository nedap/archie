package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredResource {
    private String originalLanguage;
    private Boolean controlled;
    private String uid;

    private List<TranslationDetails> translations = new ArrayList<>();
    private ResourceDescription description;


}
