package org.openjfx.SolverAI;

public abstract class Looper<ReturnType>{
    public abstract void loop();
    public abstract ReturnType getResult();
}
