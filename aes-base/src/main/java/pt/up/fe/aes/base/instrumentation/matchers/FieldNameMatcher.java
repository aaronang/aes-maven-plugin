package pt.up.fe.aes.base.instrumentation.matchers;

import java.security.ProtectionDomain;

import javassist.CtClass;
import javassist.CtMethod;

public class FieldNameMatcher implements Matcher {
    private final String fieldName;

    public FieldNameMatcher (String name) {
        this.fieldName = name;
    }

    @Override
    public boolean matches (CtClass c, ProtectionDomain d) {
        try {
            return c.getDeclaredField(fieldName) != null;
        }
        catch (Exception e) {}
        return false;
    }

    @Override
    public boolean matches (CtClass c,
                            CtMethod m) {
        return false;
    }
}