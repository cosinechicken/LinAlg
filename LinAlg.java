package com.company.LinAlg;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Scanner;

/**
 * Created by Brandon on 10/19/2020, 10:06 AM.
 */


public class LinAlg {
    public static boolean showPivots;
    public static void main(String[] args) {
        // Introduce scanner
        Scanner scanner = new Scanner(System.in);
        // Directions
        System.out.println("Welcome to the Linear Algebra program. ");
        System.out.println("Conventions: ");
        System.out.println("1. An M x N matrix contains M rows and N columns.");
        System.out.println("2. All indices are 1-based, meaning they start from 1 and count upwards");
        System.out.println("");
        // End of directions, starts user input
        //
        System.out.println("Enter \"Y\" if pivots of matrices should be displayed, enter anything else otherwise: ");
        System.out.print(">> ");
        String pivotString = scanner.nextLine();
        if (pivotString.equals("Y")) {
            showPivots = true;
        } else {
            showPivots = false;
        }
        // Get dimensions of matrix
        System.out.println("Enter the dimensions of the matrix (in the form \"M N\"): ");
        System.out.print(">> ");
        String dimString = scanner.nextLine();
        String[] dimArr = dimString.split(" ");
        int dimM = Integer.parseInt(dimArr[0]);
        int dimN = Integer.parseInt(dimArr[1]);
        Matrix matrix = new Matrix(dimM, dimN);
        System.out.println("Matrix has dimensions " + dimM + " x " + dimN + ". ");
        // Get matrix
        System.out.println("You will now the entries of the matrix, separated by a single space. ");
        for (int i = 0; i < dimM; i++) {
            System.out.println("Enter the entries of row #" + (i+1) + ": ");
            System.out.print(">> ");
            String input = scanner.nextLine();
            String[] inputArr = input.split(" ");
            for (int j = 0; j < dimN; j++) {
                int val = Integer.parseInt(inputArr[j]);
                matrix.setValue(val, i, j);
            }
//            System.out.println("Row #" + i + " has been entered. ");
        }
        System.out.println("The matrix you entered is below:");
        System.out.println(matrix);
        //
        // Keep taking commands until user wants to quit
        boolean doQuit = false;
        while (!doQuit) {
            System.out.println("Enter next command (enter \"H\" for help): ");
            System.out.print(">> ");
            String input = scanner.nextLine();
            String[] inputArr = input.split(" ");
            if (inputArr[0].equals("H")) {
                System.out.println("Here are the commands: ");
                System.out.println("\"H\": Displays the list of commands.");
                System.out.println("\"S [i] [j]\": Swaps rows [i] and [j].");
                System.out.println("\"X [i] [scale]\": Multiplies the elements in row [i] by the integer [scale].");
                System.out.println("\"A [i] [j]\": Adds row [i] to row [j].");
                System.out.println("\"D [i]\": Divides the row [i] by the greatest common divisor of numbers in the row and makes the first nonzero element in the row positive.");
                System.out.println("\"C [i] [j]\": Adds one of row [i] and row [j] by a scalar multiple of the other row to simplify it. Prints out the operations used. For now should only run after descaling all rows");
                System.out.println("\"REF\": Simplifies to REF form and shows work.");
                System.out.println("\"RREF\": Simplifies to RREF form and shows work.");
                System.out.println("\"Q\": End the program.");
            } else if (inputArr[0].equals("S")) {
                if (inputArr.length < 3) {
                    System.out.println("Error: You need at least 2 arguments for this command");
                }
                int i;
                int j;
                try {
                    i = Integer.parseInt(inputArr[1]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[1] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                try {
                    j = Integer.parseInt(inputArr[2]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[2] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                if (!Math.isInRange(i,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 1 and " + dimM + " inclusive. ");
                    continue;
                }
                if (!Math.isInRange(j,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 0 and " + dimM + " inclusive. ");
                    continue;
                }
                String operation = matrix.swap(i-1,j-1);
                System.out.println(operation);
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("X")) {
                if (inputArr.length < 3) {
                    System.out.println("Error: You need at least 2 arguments for this command");
                }
                int i;
                int scale;
                try {
                    i = Integer.parseInt(inputArr[1]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[1] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                try {
                    scale = Integer.parseInt(inputArr[2]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[2] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                if (!Math.isInRange(i,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 1 and " + dimM + " inclusive. ");
                    continue;
                }
                matrix.scale(i-1,scale);
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("A")) {
                if (inputArr.length < 3) {
                    System.out.println("Error: You need at least 2 arguments for this command");
                }
                int i;
                int j;
                try {
                    i = Integer.parseInt(inputArr[1]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[1] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                try {
                    j = Integer.parseInt(inputArr[2]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[2] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                if (!Math.isInRange(i,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 1 and " + dimM + " inclusive. ");
                    continue;
                }
                if (!Math.isInRange(j,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 0 and " + dimM + " inclusive. ");
                    continue;
                }
                matrix.addRow(i-1,j-1);
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("D")) {
                if (inputArr.length < 2) {
                    System.out.println("Error: You need at least 1 argument for this command");
                }
                int i;
                try {
                    i = Integer.parseInt(inputArr[1]);
                } catch(NumberFormatException e){
                    System.out.println("Error: \"" + inputArr[1] + "\" is not an integer. Please enter an integer.");
                    continue;
                }
                if (!Math.isInRange(i,1,dimM)) {
                    System.out.println("Error: Row numbers must be between 1 and " + dimM + " inclusive. ");
                    continue;
                }
                matrix.descale(i-1);
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("C")) {
                if (inputArr.length < 3) {
                    System.out.println("Error: You need at least 2 arguments for this command");
                }
                int i = Integer.parseInt(inputArr[1]);
                int j = Integer.parseInt(inputArr[2]);
                String operation = matrix.cancel(i-1,j-1);
                System.out.println(operation);
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("REF")) {
                System.out.println(matrix.toREF() + "\n");
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("RREF")) {
                System.out.println(matrix.toRREF() + "\n");
                System.out.println("The resulting matrix is below:");
                System.out.println(matrix);
            } else if (inputArr[0].equals("Q")) {
                doQuit = true;
            } else {
                System.out.println("Error: \"" + input + "\" is not a valid command. Press \"H\" to see the list of valid commands. ");
            }
        }

    }

}
