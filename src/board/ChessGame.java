package board;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGame extends Application {

	private static Stage primaryStage;
	private static Scene scene;

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		MakeNewGame();

		SetStage(primaryStage);

	}

	private static void SetStage(Stage primaryStage) {
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
	}

	public static void MakeNewGame() {

		scene = null;

		ChessBoard chess = new ChessBoard();

		scene = new Scene(chess, Square.SIZE * 8, Square.SIZE * 8);

		SetStage(primaryStage);

	}

}
