package testjavapusher;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import testjavapusher.client.Pusher;
import testjavapusher.client.PusherOptions;
import testjavapusher.client.channel.Channel;
import testjavapusher.client.channel.ChannelEventListener;
import testjavapusher.client.channel.PusherEvent;
import testjavapusher.client.connection.ConnectionEventListener;
import testjavapusher.client.connection.ConnectionStateChange;

public class FXMLDocumentController implements Initializable {
    
    private final String channelsKey = "4feb8857a532b72292fe";
    private final String channelName = "my-channel";
    private final String eventName = "my-event";
    private final String cluster = "ap1";
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        initPusher();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    private void initPusher(){
          
        // configure your Pusher connection with the options you want
        final PusherOptions options = new PusherOptions()
                .setUseTLS(true)
                .setCluster(cluster);
        Pusher pusher = new Pusher(channelsKey, options);
        
        // set up a ConnectionEventListener to listen for connection changes to Pusher
        ConnectionEventListener connectionEventListener = new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println(String.format("Connection state changed from [%s] to [%s]",
                        change.getPreviousState(), change.getCurrentState()));
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println(String.format("An error was received with message [%s], code [%s], exception [%s]",
                        message, code, e));
            }
        };

        // connect to Pusher
        pusher.connect(connectionEventListener);

        // set up a ChannelEventListener to listen for messages to the channel and event we are interested in
        ChannelEventListener channelEventListener = new ChannelEventListener() {
            @Override
            public void onSubscriptionSucceeded(String channelName) {
                System.out.println(String.format(
                        "Subscription to channel [%s] succeeded", channelName));
            }

            @Override
            public void onEvent(PusherEvent event) {
                System.out.println(String.format(
                        "Received event [%s]", event.toString()));
            }
        };

        // subscribe to the channel and with the event listener for the event name
        Channel channel = pusher.subscribe(channelName, channelEventListener, eventName);
    }
}
