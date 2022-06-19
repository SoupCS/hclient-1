package neko.itskekoffcode.clientapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class DataBuffer {
    private final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
    private final DataOutputStream out;

    public DataBuffer() {
        this.out = new DataOutputStream(this.byteBuffer);
    }

    public DataOutputStream out() {
        return this.out;
    }

    public ByteArrayOutputStream byteData() {
        return this.byteBuffer;
    }
}
