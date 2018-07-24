package com.somecompany.example.models;

import org.json.JSONObject;

public class GameState {
    private static int score = 0;
    private static int newGame = 0;
    private static String playerCode = "";
    private static double playPrice = 0;
    private static String user_token = "";
    private static String gameState = "ready";
    private static String token = "";
    private static String playID = "";
    private static boolean connected = false;
    private static String appID = "";
    private static int pollID = 0;
    private static String contestID = "";
    private static int gameFlag = 1;
    private static String username;
    private static double balance;
    private static String backsplash = "";
    private static String attempt_fee = "";
    private static String jackpot = "";
    private static String userimage = "";
    private static JSONObject leaderboard = new JSONObject();
    private static String device_id = "";
    private static int arcade_id = 0;

    public static void setScore(int s) {
        score = s;
    }

    public static int getScore() {
        return score;
    }

    public static void setNewGame(int ng) {
        newGame = ng;
    }

    public static int getNewGame() {
        return newGame;
    }

    public static void setUserImage(String ui) { userimage = ui; }

    public static String getUserImage() { return userimage; }

    public static void setPlayerCode(String pc) {
        playerCode = pc;
    }

    public static String getPlayerCode() {
        return playerCode;
    }

    public static void setPlayPrice(double pp) {
        playPrice = pp;
    }

    public static double getPlayPrice() {
        return playPrice;
    }

    public static void setUserToken(String ut) {
        user_token = ut;
    }

    public static String getUserToken() {
        return user_token;
    }

    public static void setGameState(String gs) {
        gameState = gs;
    }

    public static String getGameState() {
        return gameState;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tk) {
        token = tk;
    }

    public static String getPlayID() {
        return playID;
    }

    public static void setPlayID(String pid) {
        playID = pid;
    }


    public static boolean getConnected() {
        return connected;
    }

    public static void setConnected(boolean c) {
        connected = c;
    }

    public static String getAppID() {
        return appID;
    }

    public static void setAppID(String aid) {
        appID = aid;
    }

    public static int getPollID() {
        return pollID;
    }

    public static void setPollID(int pid) {
        pollID = pid;
    }

    public static String getContestID() {
        return contestID;
    }

    public static void setContestID(String cid) {
        contestID = cid;
    }

    public static void setGameFlag(int gf) {
        gameFlag = gf;
    }

    public static int getGameFlag() {
        return gameFlag;
    }

    public static void setUsername(String user) {
        username = user;
    }

    public static void setBacksplash(String bs) {
        backsplash = bs;
    }

    public static String getUsername() {
        return username.substring(0,1).toUpperCase() + username.substring(1);
    }

    public static String getBacksplash() {
        return backsplash;
    }

    public static void setAttemptFee(String af) {
        attempt_fee = af;
    }

    public static String getAttemptFee() {
        return attempt_fee;
    }

    public static void setBalance(double bal) {
        balance = bal;
    }

    public static double getBalance() {
        return balance;
    }

    public static void setJackpot(String j) {
        jackpot = j;
    }

    public static String getJackpot() {
        return jackpot;
    }

    public static void setLeaderboard(JSONObject json) { leaderboard = json; }

    public static JSONObject getLeaderboard() { return leaderboard; }

    public static void setDeviceId(String d) {
        device_id = d;
    }

    public static String getDeviceId() {
        return device_id;
    }

    public static void setArcadeId(int a) {
        arcade_id = a;
    }

    public static int getArcadeId() {
        return arcade_id;
    }

    public void resetGameState() {
        score = 0;
        newGame = 0;
        playerCode = "";
        playPrice = 0;
        user_token = "";
        gameState = "ready";
        token = "";
        playID = "";
        connected = false;
        appID = "";
        pollID = 0;
        gameFlag = 1;
        backsplash = "";
        attempt_fee = "";
    }

    public void resetSessionState() {

    }
}
