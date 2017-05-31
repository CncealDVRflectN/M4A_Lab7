import java.util.Arrays;

public class Solution {
    private static double calcF(double x) {
        return 1.5 * Math.pow(Math.E, x) - 0.5 * Math.cos(x);
    }

    private static void printArr(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static double calcFactotial(double num) {
        return (num > 0) ? num * calcFactotial(num - 1) : 1;
    }

    private static double calcLagrange(double[][] table, double x, int n) {
        double result = 0;
        double tmp;
        for (int i = 0; i < n + 1; i++) {
            tmp = table[1][i];
            for (int j = 0; j < n + 1; j++) {
                tmp *= (i != j) ? (x - table[0][j]) / (table[0][i] - table[0][j]) : 1;
            }
            result += tmp;
        }
        return result;
    }

    private static double calcNewtone(double[][] table, double x, int n) {
        double[][] divDif = new double[n + 2][n + 1];
        double result = 0;
        double tmp;
        for (int i = 0; i < n + 1; i++) {
            divDif[0][i] = table[0][i];
            divDif[1][i] = table[1][i];
        }
        for (int i = 2; i < n + 2; i++) {
            for (int j = 0; j < n - i + 2; j++) {
                divDif[i][j] = (divDif[i - 1][j + 1] - divDif[i - 1][j]) / (divDif[0][j + i - 1] - divDif[0][j]);
            }
        }
        for (int i = 0; i < n + 1; i++) {
            tmp = divDif[i + 1][0];
            for (int j = 0; j < i; j++) {
                tmp *= x - divDif[0][j];
            }
            result += tmp;
        }
        return result;
    }

    private static void calc(double[][] table, double x) {
        double[][] divDif = new double[13][12];
        double tmp;
        for (int i = 0; i < 11; i++) {
            divDif[0][i] = table[0][i];
            divDif[1][i] = table[1][i];
        }
        divDif[0][11] = x;
        divDif[1][11] = calcF(x);
        Arrays.sort(divDif[0]);
        Arrays.sort(divDif[1]);
        printArr(divDif[0]);
        for (int i = 2; i < 13; i++) {
            for (int j = 0; j < 13 - i; j++) {
                divDif[i][j] = (divDif[i - 1][j + 1] - divDif[i - 1][j]) / (divDif[0][j + i - 1] - divDif[0][j]);
            }
        }
        System.out.println(divDif[12][0]);
        tmp = 1;
        for (int i = 0; i < 11; i++) {
            tmp *= x - table[0][i];
        }
        System.out.println(tmp * 3.65669 / calcFactotial(11));
        System.out.println(tmp * divDif[12][0]);
    }

    public static void main(String[] args) {
        double[][] table = new double[2][11];
        int N = 10;
        for (int i = 0; i <= N; i++) {
            table[0][i] = (double)i / N;
            table[1][i] = calcF(table[0][i]);
        }
        System.out.print("X: ");
        printArr(table[0]);
        System.out.print("Y: ");
        printArr(table[1]);
        System.out.println("Интерполяционный многочлен Лагранжа:");
        System.out.println("r*: " + Math.abs(calcLagrange(table, 0.1 / 3, 10) - calcF(0.1 / 3)));
        System.out.println("r**: " + Math.abs(calcLagrange(table, 0.5 + 0.1 / 3, 10) - calcF(0.5 + 0.1 / 3)));
        System.out.println("r***: " + Math.abs(calcLagrange(table, 1 - 0.1 / 3, 10) - calcF(1 - 0.1 / 3)));
        System.out.println("Интерполяционный многочлен Ньютона:");
        System.out.println("r*: " + Math.abs(calcNewtone(table, 0.1 / 3, 10) - calcF(0.1 / 3)));
        System.out.println("r**: " + Math.abs(calcNewtone(table, 0.5 + 0.1 / 3, 10) - calcF(0.5 + 0.1 / 3)));
        System.out.println("r***: " + Math.abs(calcNewtone(table, 1 - 0.1 / 3, 10) - calcF(1 - 0.1 / 3)));
        calc(table, 0.1 / 3);
        calc(table, 0.5 + 0.1 / 3);
        calc(table, 1 - 0.1 / 3);
    }
}
