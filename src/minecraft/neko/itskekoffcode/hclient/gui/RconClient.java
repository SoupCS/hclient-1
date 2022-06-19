package neko.itskekoffcode.hclient.gui;

import neko.itskekoffcode.hclient.rcon.AuthFailureException;
import neko.itskekoffcode.hclient.rcon.RconClientException;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RconClient implements Closeable {
    private static final int AUTHENTICATION_FAILURE_ID = -1;
    private static final Charset PAYLOAD_CHARSET;
    private static final int TYPE_COMMAND = 2;
    private static final int TYPE_AUTH = 3;
    private final SocketChannel socketChannel;
    private final AtomicInteger currentRequestId;

    static {
        PAYLOAD_CHARSET = StandardCharsets.US_ASCII;
    }

    private RconClient(SocketChannel socketChannel) {
        this.socketChannel = (SocketChannel)Objects.requireNonNull(socketChannel, "socketChannel");
        this.currentRequestId = new AtomicInteger(1);
    }

    public static RconClient open(String host, int port, String password) {
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        } catch (IOException var9) {
            throw new RconClientException("Failed to open socket to " + host + ":" + port, var9);
        }

        RconClient rconClient = new RconClient(socketChannel);

        try {
            rconClient.authenticate(password);
            return rconClient;
        } catch (Exception var8) {
            try {
                rconClient.close();
            } catch (Exception var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }
    }

    public String sendCommand(String command) {
        return this.send(2, command);
    }

    public void close() {
        try {
            this.socketChannel.close();
        } catch (IOException var2) {
            throw new RconClientException("Failed to close socket channel", var2);
        }
    }

    private void authenticate(String password) {
        this.send(3, password);
    }

    private String send(int type, String payload) {
        int requestId = this.currentRequestId.getAndIncrement();
        ByteBuffer buffer = toByteBuffer(requestId, type, payload);

        try {
            this.socketChannel.write(buffer);
        } catch (IOException var9) {
            throw new RconClientException("Failed to write " + buffer.capacity() + " bytes", var9);
        }

        ByteBuffer responseBuffer = this.readResponse();
        int responseId = responseBuffer.getInt();
        if (responseId == -1) {
            throw new AuthFailureException();
        } else if (responseId != requestId) {
            throw new RconClientException("Sent request id " + requestId + " but received " + responseId);
        } else {
            int responseType = responseBuffer.getInt();
            byte[] bodyBytes = new byte[responseBuffer.remaining()];
            responseBuffer.get(bodyBytes);
            return new String(bodyBytes, PAYLOAD_CHARSET);
        }
    }

    private ByteBuffer readResponse() {
        int size = this.readData(4).getInt();
        ByteBuffer dataBuffer = this.readData(size - 2);
        ByteBuffer nullsBuffer = this.readData(2);
        byte null1 = nullsBuffer.get(0);
        byte null2 = nullsBuffer.get(1);
        if (null1 == 0 && null2 == 0) {
            return dataBuffer;
        } else {
            throw new RconClientException("Expected 2 null bytes but received " + null1 + " and " + null2);
        }
    }

    private ByteBuffer readData(int size) {
        ByteBuffer buffer = ByteBuffer.allocate(size);

        int readCount;
        try {
            readCount = this.socketChannel.read(buffer);
        } catch (IOException var5) {
            throw new RconClientException("Failed to read " + size + " bytes", var5);
        }

        if (readCount != size) {
            throw new RconClientException("Expected " + size + " bytes but received " + readCount);
        } else {
            buffer.position(0);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer;
        }
    }

    private static ByteBuffer toByteBuffer(int requestId, int type, String payload) {
        ByteBuffer buffer = ByteBuffer.allocate(12 + payload.length() + 2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(8 + payload.length() + 2);
        buffer.putInt(requestId);
        buffer.putInt(type);
        buffer.put(payload.getBytes(PAYLOAD_CHARSET));
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.position(0);
        return buffer;
    }
}
