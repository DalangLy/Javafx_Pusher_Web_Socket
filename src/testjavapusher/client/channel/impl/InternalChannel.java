package testjavapusher.client.channel.impl;

import testjavapusher.client.channel.Channel;
import testjavapusher.client.channel.ChannelEventListener;
import testjavapusher.client.channel.ChannelState;
import testjavapusher.client.channel.PusherEvent;

public interface InternalChannel extends Channel, Comparable<InternalChannel> {

    String toSubscribeMessage();

    String toUnsubscribeMessage();

    PusherEvent prepareEvent(String event, String message);

    void onMessage(String event, String message);

    void updateState(ChannelState state);

    void setEventListener(ChannelEventListener listener);

    ChannelEventListener getEventListener();
}
