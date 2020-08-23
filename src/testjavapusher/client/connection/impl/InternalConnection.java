package testjavapusher.client.connection.impl;

import testjavapusher.client.connection.Connection;

public interface InternalConnection extends Connection {

    void sendMessage(String message);

    void disconnect();
}
