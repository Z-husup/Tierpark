package com.prog.tierpark;

import com.prog.tierpark.model.Enclosure;

/**
 * Shared context class for holding scene-level data during view transitions.
 * Used to pass selected objects between scenes without tight coupling.
 */
public class SceneContext {

    /** The enclosure currently selected for detail view or editing. */
    public static Enclosure selectedEnclosure;
}
