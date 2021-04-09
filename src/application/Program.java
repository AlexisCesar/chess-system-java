package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		
		List<ChessPiece> capturedPieces = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		
		while(!chessMatch.getCheckMate()) {
			
			try {
			
				UI.clearScreen();
				
				UI.printMatch(chessMatch, capturedPieces);
				
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece captured = chessMatch.performChessMove(source, target);
				
				if(captured != null) {
					capturedPieces.add(captured);
				}
				
				if(chessMatch.getPromoted() != null) {
					System.out.print("Choose a promotion to your Pawn [B/N/R/Q]: ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("\nInvalid piece type for promotion"
								+ "\nChoose a promotion to your Pawn [B/N/R/Q]: ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
			
			} catch(ChessException e) {
				System.out.println("Error: " + e.getMessage());
				sc.nextLine();
			} catch(InputMismatchException e) {
				System.out.println("Error: " + e.getMessage());
				sc.nextLine();
			} catch(IllegalStateException e) {
				System.out.println("Error: " + e.getMessage());
				sc.nextLine();
			}
			
		}
		
		UI.clearScreen();
		UI.printMatch(chessMatch, capturedPieces);
		System.out.println("");
		
	}

}
