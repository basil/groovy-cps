package com.cloudbees.groovy.cps;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * {@link ObjectInputStream} with a custom {@link ClassLoader}.
 *
 * @author Kohsuke Kawaguchi
 */
public class ObjectInputStreamWithLoader extends ObjectInputStream {
    private final ClassLoader cl;

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "annoying warning")
    public ObjectInputStreamWithLoader(InputStream in, ClassLoader cl) throws IOException {
        super(in);
        this.cl = cl;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        try {
            return cl.loadClass(desc.getName());
        } catch (ClassNotFoundException e) {
            return super.resolveClass(desc);
        }
    }
}
