package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class Template extends AuthoredArchetype {

    private List<TemplateOverlay> templateOverlays = new ArrayList<>();

    public List<TemplateOverlay> getTemplateOverlays() {
        return templateOverlays;
    }

    public void setTemplateOverlays(List<TemplateOverlay> templateOverlays) {
        this.templateOverlays = templateOverlays;
    }

    public void addTemplateOverlay(TemplateOverlay overlay) {
        templateOverlays.add(overlay);
    }

    public TemplateOverlay getTemplateOverlay(String id) {
        for(TemplateOverlay overlay:templateOverlays) {
            if(overlay.getArchetypeId().getFullId().equals(id)) {
                return overlay;
            }
        }
        return null;
    }
}
