package ch.vincent_genecand.bfh.primethreading;

import ch.vincent_genecand.bfh.primethreading.version1.ControllerV1;
import ch.vincent_genecand.bfh.primethreading.version2.ControllerV2;

final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                int version = Integer.parseInt(args[0]);
                int threads = Integer.parseInt(args[1]);
                int size = Integer.parseInt(args[2]);

                switch (version) {
                case 1:
                    new ControllerV1(threads, size, true);
                    break;
                case 2:
                    new ControllerV2(threads, size);
                    break;
                default:
                    System.out.println("There is no version " + version + " planned!");
                }

            } catch (NumberFormatException e) {
                System.err.println("Only numbers are allowed arguments!");
            }

        } else if (args.length == 0) {
            System.out.println("Demo Mode:");
            new ControllerV1(20, 20, true);
        } else {
            System.err.println("Wrong arguments! ===|> <version> <threads> <limit>");
        }
    }
}
