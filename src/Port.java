/*
* Port with port number and constraints.
* */

import exceptions.PortOutOfBoundsException;

public class Port {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 65535;

    private int number;

    public Port(int number) {
        try {
            if (number < MIN_VALUE || number > MAX_VALUE)
                throw new PortOutOfBoundsException("Invalid port range. Valid range: [1,65535]");
            this.number = number;
        } catch (PortOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getNumber() {
        return number;
    }
}
