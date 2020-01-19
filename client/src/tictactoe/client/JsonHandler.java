package tictactoe.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.DataInputStream;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import tictactoe.client.gui.InvitationScreen;
import tictactoe.client.gui.MainScreen;
import tictactoe.client.gui.MultiOnlinePlayers;
import tictactoe.client.gui.SigninScreen;
import tictactoe.client.gui.SignupScreen;

/**
 *
 * @author Tharwat
 */
public class JsonHandler {

    private App app;
    private DataInputStream dataInputStream;
    SignupScreen signupScreen;
    SigninScreen signinScreen;
    InvitationScreen invitationScreen;
    MultiOnlinePlayers multiOnlinePlayers;
    MainScreen mainScreen;

    JsonHandler(App a) {
        app = a;
        signupScreen = (SignupScreen) app.getScreen("signup");
        signinScreen = (SigninScreen) app.getScreen("signin");
        invitationScreen = (InvitationScreen) app.getScreen("invitation");
        multiOnlinePlayers = (MultiOnlinePlayers) app.getScreen("multiOnlinePlayers");
        mainScreen = (MainScreen) app.getScreen("main");
    }

    public void handle(JsonObject request) {
        System.out.println(request);
        String requestType = request.get("type").getAsString();
        JsonObject requestData = request.getAsJsonObject("data");
        JsonObject myData = requestData.getAsJsonObject("my-data");
        switch (requestType) {
            case "signup-error":
                signupScreen.showSignupFailedPopup();
                break;
            case "signup-success":
                app.showAlert("Welcome :D", "Sign up successful.\nLogin to play :D");
                app.setScreen("signin");
                break;
            case "signin-success":

                app.setCurrentPlayer(new Player(
                        myData.get("id").getAsInt(),
                        myData.get("firstName").getAsString(),
                        myData.get("email").getAsString(),
                        myData.get("points").getAsInt()
                ));
                app.setScreen("main");
                break;
            case "signin-error":
                app.showAlert("Could not login", requestData.get("msg").getAsString());
                signinScreen.showSigninButton();
                break;
            case "update-player-list":
                refreshList(requestData);
                break;
            case "online-player":
//                refreshList(requestData);     /*THROWS NULL POINTER EXCEPTION*/

                break;
            case "invitation":
                int challengerId = requestData.get("inviter_player_id").getAsInt();
                String challengerName = requestData.get("inviter_player_name").getAsString();
                invitationScreen.setInvitation(challengerId, challengerName);
                break;
            case "invitation-accepted":
                /*inviter side*/
                int opposingPlayerId = requestData.get("invited_player_id").getAsInt();
                String opposingPlayerName = requestData.get("invited_player_name").getAsString();
                multiOnlinePlayers.invitationAcceptedSetInviterSide(opposingPlayerName, opposingPlayerId);
                break;
            case "game-move":
                multiOnlinePlayers.setOpponentMoveFromServer(requestData.get("position").getAsString());
                break;
        }
    }

    public void refreshList(JsonObject requestData) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreen.clearPlayersListPane();
                JsonArray onlinePlayerList = requestData.getAsJsonArray("online-players");
                JsonArray offlinePlayerList = requestData.getAsJsonArray("offline-players");
                mainScreen.setPlayersListCounter(0);
                mainScreen.addPlayersToList(onlinePlayerList, Color.GREEN);
                mainScreen.addPlayersToList(offlinePlayerList, Color.RED);
            }
        });
    }
}
