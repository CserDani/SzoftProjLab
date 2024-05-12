public class PlayableGame {
    public static void main(String[] args) {
        Game g = new Game();
        g.gameLoad("twoPlayerMap.txt");
        GameView gw = new GameView(g);

        new Controller(g, gw);
    }
}
