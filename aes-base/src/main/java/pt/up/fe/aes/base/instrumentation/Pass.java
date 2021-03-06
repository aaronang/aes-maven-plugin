package pt.up.fe.aes.base.instrumentation;

import java.security.ProtectionDomain;

import javassist.CtClass;

public interface Pass {
    public static enum Outcome {CONTINUE, CANCEL, FINISH};

    Outcome transform (CtClass c, ProtectionDomain d) throws Exception;
}