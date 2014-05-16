package ch.vincent_genecand.bfh.primethreading;

import ch.vincent_genecand.bfh.primethreading.version1.ControllerV1;

final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                int version = Integer.parseInt(args[0]);

                switch (version) {
                case 1:
                    int threads = Integer.parseInt(args[1]);
                    int limit = Integer.parseInt(args[2]);
                    new ControllerV1(threads, limit);
                    break;
                case 2:
                    System.out.println("Version 2 WIP!");
                    break;
                default:
                    System.out.println("There is no version " + version + " planned!");
                }

            } catch (NumberFormatException e) {
                System.err.println("Only numbers are allowed arguments!");
            }

        } else if (args.length == 0) {
            System.out.println("Demo Mode:");
            new ControllerV1(20, 20);
        } else {
            System.err.println("Wrong arguments! ===|> <version> <threads> <limit>");
        }
    }
}
