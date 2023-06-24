public enum Directions {
    N, S, E, W, STOPPED;

    public static Directions flip (Directions direction) {
        switch (direction) {
            case N:
                direction = S;
                break;
            case S:
                direction = N;
                break;
            case E:
                direction = W;
                break;
            case W:
                direction = E;
                break;
            case STOPPED:
                direction = STOPPED;
                break;
        }

        return direction;
    }
}
